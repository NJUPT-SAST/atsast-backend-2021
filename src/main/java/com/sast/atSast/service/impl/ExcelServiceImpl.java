package com.sast.atSast.service.impl;

import com.sast.atSast.enums.CustomError;
import com.sast.atSast.exception.LocalRuntimeException;
import com.sast.atSast.mapper.ContestMapper;
import com.sast.atSast.mapper.JudgesResultMapper;
import com.sast.atSast.mapper.TeamMemberMapper;
import com.sast.atSast.model.JudgesResult;
import com.sast.atSast.model.StudentInfo;
import com.sast.atSast.model.TeamMember;
import com.sast.atSast.server.MinioServer;
import com.sast.atSast.service.ExcelService;
import com.sast.atSast.service.StudentInfoService;
import com.sast.atSast.util.MultipartFileToFile;
import io.minio.MinioClient;
import io.minio.PutObjectOptions;
import io.minio.errors.MinioException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: Acow337
 * @Date: 2021/07/19/10:10
 * @Description:
 */
@Service
public class ExcelServiceImpl implements ExcelService {

    @Autowired
    private JudgesResultMapper judgesResultService;

    @Autowired
    private TeamMemberMapper teamMemberService;

    @Autowired
    private ContestMapper contestMapper;

    @Autowired
    private MinioServer minioServer;

    @Autowired
    private StudentInfoService studentInfoService;

    @Override
    public String exportresult(Integer contestId) throws IOException {

        //分配好列表数据
        List<JudgesResult> results=new LinkedList<JudgesResult>();
        List<TeamMember> teamMembers=new LinkedList<>();
        results=judgesResultService.getResults(contestId);
        teamMembers=teamMemberService.getTeams(contestId);
        String contestName=contestMapper.getContestById(contestId).getContestName();

        //分配好要用的map
        Map<Long,String> nameMap=new HashMap<>();
        Map<Long,List<Integer>> map = new HashMap<>();
        Map<Long,Integer> finMap=new HashMap<>();

        //创建工作簿 工作表
        Workbook workbook=new XSSFWorkbook();
        Sheet sheet=workbook.createSheet();

        for (TeamMember item:teamMembers){
            if(!nameMap.containsKey(item.getTeamId())){
                nameMap.put(item.getTeamId(),item.getTeamName());
            }
        }

        for(JudgesResult judgesResult:results){
            if(map.containsKey(judgesResult.getTeamId())){
                map.get(judgesResult.getTeamId()).add(judgesResult.getScores());
            }else {
                List<Integer> temp=new LinkedList<Integer>();
                temp.add(judgesResult.getScores());
                map.put(judgesResult.getTeamId(),temp);
            }
        }

        //获取最终成绩

        for (Map.Entry<Long,List<Integer>> entry:map.entrySet()) {
            if(entry.getValue().size()>4){
                Integer max = entry.getValue().stream().max((a, b) -> {
                    if (a > b) {
                        return 1;
                    } else return -1;
                }).get();
                Integer min = entry.getValue().stream().max((a, b) -> {
                    if (a < b) {
                        return 1;
                    } else return -1;
                }).get();
                Integer fin=0;
                for (Integer item:entry.getValue()){
                    fin+=item;
                }
                fin=(fin-max-min)/(entry.getValue().size()-2);
                finMap.put(entry.getKey(),fin);
            }else if(entry.getValue().size()>=2 | entry.getValue().size()<=4){
                Integer fin=0;
                for (Integer item:entry.getValue()){
                    fin+=item;
                }
                fin=fin/entry.getValue().size();
                finMap.put(entry.getKey(),fin);
            }else if(entry.getValue().size()==1){
                finMap.put(entry.getKey(),entry.getValue().get(0));
            }else {
                throw new LocalRuntimeException(CustomError.UNKNOWN_ERROR);
            }
        }

        //写入Excel操作
        Row row1=sheet.createRow(0);
        row1.createCell(0).setCellValue("队伍名称");
        row1.createCell(1).setCellValue("分数");

        int count=1;
        for(Map.Entry<Long,Integer> entry:finMap.entrySet()){
            Row row=sheet.createRow(count);
            row.createCell(0).setCellValue(nameMap.get(entry.getKey()));
            row.createCell(1).setCellValue(entry.getValue());
            count++;
        }

        String fileName="src/main/resources/excel/"+contestName+"_比赛结果.xlsx";
        File file = new File(fileName);
        if(file.exists()) {
            file.delete();
            System.out.println("删除成功");
        }

        FileOutputStream fileOutputStream=new FileOutputStream(fileName);
        workbook.write(fileOutputStream);
        fileOutputStream.close();

        FileInputStream fileInputStream=new FileInputStream(fileName);

        String url="";

        try {
            // 使用MinIO服务的URL，端口，Access key和Secret key创建一个MinioClient对象
            MinioClient minioClient = new MinioClient(minioServer.getEndpoint(),minioServer.getAccessKey(),
                    minioServer.getSecretKey());
            // 检查存储桶是否已经存在
            boolean isExist = minioClient.bucketExists("finalresults");
            if(!isExist){
                // 创建一个名为asiatrip的存储桶，用于存储照片的zip文件。
                minioClient.makeBucket("finalresults");
            }
            // 使用putObject上传一个文件到存储桶中。
            minioClient.putObject("finalresults", contestName+"_比赛结果.xlsx", fileInputStream,
                    new PutObjectOptions(fileInputStream.available(), -1));
            url=minioClient.getObjectUrl("finalresults", contestName+"_比赛结果.xlsx");

        } catch(MinioException e) {
            CustomError.MINIO_ERROR.setErrMsg(e.getMessage());
            throw new LocalRuntimeException(CustomError.MINIO_ERROR);
        } catch (NoSuchAlgorithmException e) {
            CustomError.MINIO_ERROR.setErrMsg(e.getMessage());
            throw new LocalRuntimeException(CustomError.MINIO_ERROR);
        } catch (InvalidKeyException e) {
            CustomError.MINIO_ERROR.setErrMsg(e.getMessage());
            throw new LocalRuntimeException(CustomError.MINIO_ERROR);
        }

        file = new File(fileName);
        if(file.exists()) {
            file.delete();
        }
        fileInputStream.close();

        return url;
    }
    @Override
    public String importresult(MultipartFile file, long contestId) {
        List<TeamMember> teamMembers=new LinkedList<>();
        teamMembers=teamMemberService.getTeams(Integer.valueOf((int) contestId));

        //分配好map
        Map<String,Long> nameMap=new HashMap<>();
        Map<Long,String> prizeMap=new HashMap<>();
        Map<Long,Integer> scoreMap=new HashMap<>();

        for (TeamMember item:teamMembers){
            if(!nameMap.containsKey(item.getTeamId())){
                nameMap.put(item.getTeamName(),item.getTeamId());
            }
        }

        MultipartFileToFile multipartFileToFile=new MultipartFileToFile();
        Workbook workbook;
        try {
            InputStream inputStream=file.getInputStream();
            inputStream = new FileInputStream(multipartFileToFile.convert(file));
            workbook = new XSSFWorkbook(inputStream);
        } catch (Exception e) {
            CustomError.EXCEL_ERROR.setErrMsg("上传文件格式有误（文件后缀应为.xlsx）");
            throw new LocalRuntimeException(CustomError.EXCEL_ERROR);
        }

        Sheet sheet=workbook.getSheetAt(0);
        int rowCount=sheet.getPhysicalNumberOfRows();
        Row rowTitle=sheet.getRow(0);

        for(int rowNum=1;rowNum<rowCount;rowNum++){
            Row rowData=sheet.getRow(rowNum);
            String teamName=" ";
            if(rowData!=null){
                int cellCount=rowTitle.getPhysicalNumberOfCells();
                for(int cellNum=0;cellNum<cellCount;cellNum++){
                    Cell cell= null;
                    try {
                        cell = rowData.getCell(cellNum);
                        CellType cellType=cell.getCellTypeEnum();
                    } catch (NullPointerException e) {
                        CustomError.EXCEL_ERROR.setErrMsg("Excel缺失数据");
                        throw new LocalRuntimeException(CustomError.EXCEL_ERROR);
                    }

                    String cellValue;
                    if(cell.getCellTypeEnum()==CellType.STRING) {
                        cellValue = cell.getStringCellValue();
                    }else if(cell.getCellTypeEnum()==CellType.NUMERIC){
                        cellValue=String.valueOf(Integer.valueOf((int) cell.getNumericCellValue()));
                    }else {
                        CustomError.EXCEL_ERROR.setErrMsg("Excel内存放数据类型有误");
                        throw new LocalRuntimeException(CustomError.EXCEL_ERROR);
                    }

                    switch (cellNum){
                        case 0:
                            if(nameMap.containsKey(cellValue)){
                                teamName=cellValue;
                            }else {
                                CustomError.EXCEL_ERROR.setErrMsg("第"+rowNum+"行队伍名称不存在");
                                throw new LocalRuntimeException(CustomError.EXCEL_ERROR);
                            }
                            break;
                        case 1:
                            if(Long.valueOf(cellValue)>=0 & Long.valueOf(cellValue)<=100){
                                scoreMap.put(nameMap.get(teamName),Integer.valueOf(cellValue));
                            }else {
                                CustomError.EXCEL_ERROR.setErrMsg("第"+rowNum+"行分数内容有误");
                                throw new LocalRuntimeException(CustomError.EXCEL_ERROR);
                            }
                            break;
                        case 2:
                            if(cellValue.equals("特等奖")|cellValue.equals("一等奖")
                                    |cellValue.equals("二等奖")|cellValue.equals("三等奖")){
                                prizeMap.put(nameMap.get(teamName),cellValue);
                            }else {
                                CustomError.EXCEL_ERROR.setErrMsg("第"+rowNum+"行奖项内容有误");
                                throw new LocalRuntimeException(CustomError.EXCEL_ERROR);
                            }
                            break;
                        default:
                            CustomError.EXCEL_ERROR.setErrMsg("Excel内容错误");
                            throw new LocalRuntimeException(CustomError.EXCEL_ERROR);
                    }
                }
            }else {
                CustomError.EXCEL_ERROR.setErrMsg("Excel内容缺失");
                throw new LocalRuntimeException(CustomError.EXCEL_ERROR);
            }
        }

        for (Map.Entry<String,Long> entry : nameMap.entrySet()){
            if(!prizeMap.containsKey(entry.getValue())){
                CustomError.EXCEL_ERROR.setErrMsg("有队伍没有评分");
                throw new LocalRuntimeException(CustomError.EXCEL_ERROR);
            }
        }

        for (Map.Entry<Long,String> entry : prizeMap.entrySet()) {
            teamMemberService.updateTeam(entry.getKey(),scoreMap.get(entry.getKey()),entry.getValue());
        }
        return "success";
    }

    public String generatelist(long contestId) throws IOException {

        //分配好列表数据
        List<JudgesResult> results=new LinkedList<JudgesResult>();
        List<TeamMember> teamMembers=new LinkedList<>();
        List<StudentInfo> studentInfos=new LinkedList<>();
        studentInfos=studentInfoService.listStudentInfos();
        teamMembers=teamMemberService.getTeams((int) contestId);
        String contestName=contestMapper.getContestById((int) contestId).getContestName();

        //分配好要用的map
        Map<String,Long> teamMap=new HashMap<>();
        Map<Long,String> studentMap=new HashMap<>();

        for(StudentInfo studentInfo:studentInfos){
            studentMap.put(studentInfo.getUid(),studentInfo.getRealName());
        }

        for(TeamMember teamMember:teamMembers){
            teamMap.put(teamMember.getTeamName(),teamMember.getMemberUid());
        }

        Map<Long,List<Integer>> map = new HashMap<>();
        Map<Long,Integer> finMap=new HashMap<>();

        //创建工作簿 工作表
        Workbook workbook=new XSSFWorkbook();
        Sheet sheet=workbook.createSheet();


        for(JudgesResult judgesResult:results){
            if(map.containsKey(judgesResult.getTeamId())){
                map.get(judgesResult.getTeamId()).add(judgesResult.getScores());
            }else {
                List<Integer> temp=new LinkedList<Integer>();
                temp.add(judgesResult.getScores());
                map.put(judgesResult.getTeamId(),temp);
            }
        }


        //写入Excel操作
        Row row1=sheet.createRow(0);
        row1.createCell(0).setCellValue("队伍名称");
        row1.createCell(1).setCellValue("队员");
        row1.createCell(2).setCellValue("签到");

        int count=1;
        for(TeamMember teamMember:teamMembers){
            Row row=sheet.createRow(count);
            row.createCell(0).setCellValue(teamMember.getTeamName());
            row.createCell(1).setCellValue(studentMap.get(teamMember.getMemberUid()));
            count++;
        }

        String fileName="src/main/resources/excel/"+contestName+"_签到表格.xlsx";
        File file = new File(fileName);
        if(file.exists()) {
            file.delete();
            System.out.println("删除成功");
        }

        FileOutputStream fileOutputStream=new FileOutputStream(fileName);
        workbook.write(fileOutputStream);
        fileOutputStream.close();

        FileInputStream fileInputStream=new FileInputStream(fileName);

        String url="";

        try {
            // 使用MinIO服务的URL，端口，Access key和Secret key创建一个MinioClient对象
            MinioClient minioClient = new MinioClient(minioServer.getEndpoint(),minioServer.getAccessKey(),
                    minioServer.getSecretKey());
            // 检查存储桶是否已经存在
            boolean isExist = minioClient.bucketExists("attendsheets");
            if(!isExist){
                minioClient.makeBucket("attendsheets");
            }
            // 使用putObject上传一个文件到存储桶中。
            minioClient.putObject("attendsheets", contestName+"_签到表格.xlsx", fileInputStream,
                    new PutObjectOptions(fileInputStream.available(), -1));
            url=minioClient.getObjectUrl("attendsheets", contestName+"_签到表格.xlsx");

        } catch(MinioException e) {
            CustomError.MINIO_ERROR.setErrMsg(e.getMessage());
            throw new LocalRuntimeException(CustomError.MINIO_ERROR);
        } catch (NoSuchAlgorithmException e) {
            CustomError.MINIO_ERROR.setErrMsg(e.getMessage());
            throw new LocalRuntimeException(CustomError.MINIO_ERROR);
        } catch (InvalidKeyException e) {
            CustomError.MINIO_ERROR.setErrMsg(e.getMessage());
            throw new LocalRuntimeException(CustomError.MINIO_ERROR);
        }

        file = new File(fileName);
        if(file.exists()) {
            file.delete();
        }
        fileInputStream.close();

        return url;
    }

    public String uploadlist(long contestId,MultipartFile file){
        List<StudentInfo> studentInfos=new LinkedList<>();
        List<TeamMember> teamMembers=new LinkedList<>();
        studentInfos=studentInfoService.listStudentInfos();
        teamMembers=teamMemberService.getTeams(Integer.valueOf((int) contestId));

        //分配好map
        Map<String,StudentInfo> studentMap=new HashMap<>();
        Map<String,TeamMember> teamMap=new HashMap<>();
        Map<String,String> memberMap=new HashMap<>();
        Map<String,String> finMap=new HashMap<>();
        Map<Long,String> uidMap=new HashMap<>();

        for(StudentInfo item:studentInfos){
            studentMap.put(item.getStuId(),item);
            uidMap.put(item.getUid(),item.getStuId());
        }

        for(TeamMember item:teamMembers){
            teamMap.put(item.getTeamName(),item);
            memberMap.put(uidMap.get(item.getMemberUid())," ");
        }

        MultipartFileToFile multipartFileToFile=new MultipartFileToFile();
        Workbook workbook;
        try {
            InputStream inputStream=file.getInputStream();
            inputStream = new FileInputStream(multipartFileToFile.convert(file));
            workbook = new XSSFWorkbook(inputStream);
        } catch (Exception e) {
            CustomError.EXCEL_ERROR.setErrMsg("上传文件格式有误（文件后缀应为.xlsx）");
            throw new LocalRuntimeException(CustomError.EXCEL_ERROR);
        }

        Sheet sheet=workbook.getSheetAt(0);
        int rowCount=sheet.getPhysicalNumberOfRows();
        Row rowTitle=sheet.getRow(0);

        for(int rowNum=1;rowNum<rowCount;rowNum++){
            Row rowData=sheet.getRow(rowNum);
            String teamName=" ";
            String stuId=" ";
            if(rowData!=null){
                int cellCount=rowTitle.getPhysicalNumberOfCells();
                for(int cellNum=0;cellNum<cellCount;cellNum++){
                    Cell cell= null;
                    try {
                        cell = rowData.getCell(cellNum);
                        CellType cellType=cell.getCellTypeEnum();
                    } catch (NullPointerException e) {
                        CustomError.EXCEL_ERROR.setErrMsg("Excel缺失数据");
                        throw new LocalRuntimeException(CustomError.EXCEL_ERROR);
                    }

                    String cellValue;
                    if(cell.getCellTypeEnum()==CellType.STRING) {
                        cellValue = cell.getStringCellValue();
                    }else if(cell.getCellTypeEnum()==CellType.NUMERIC){
                        cellValue=String.valueOf(Integer.valueOf((int) cell.getNumericCellValue()));
                    }else {
                        CustomError.EXCEL_ERROR.setErrMsg("Excel内存放数据类型有误");
                        throw new LocalRuntimeException(CustomError.EXCEL_ERROR);
                    }

                    switch (cellNum){
                        case 0:
                            if(teamMap.containsKey(cellValue)){
                                teamName=cellValue;
                            }else {
                                CustomError.EXCEL_ERROR.setErrMsg("第"+rowNum+"行队伍名称不存在");
                                throw new LocalRuntimeException(CustomError.EXCEL_ERROR);
                            }
                            break;
                        case 1:
                            if(!studentMap.containsKey(cellValue)){
                                CustomError.EXCEL_ERROR.setErrMsg("第"+rowNum+"行学号不存在(可能是该学号的学生未进行注册)");
                                throw new LocalRuntimeException(CustomError.EXCEL_ERROR);
                            }else if(finMap.containsKey(cellValue)|memberMap.containsKey(cellValue)){
                                CustomError.EXCEL_ERROR.setErrMsg("第"+rowNum+"行学号重复");
                                throw new LocalRuntimeException(CustomError.EXCEL_ERROR);
                            }else {
                                stuId=cellValue;
                                finMap.put(cellValue,teamName);
                            }
                            break;
                        case 2:
                            if(!studentMap.get(stuId).getRealName().equals(cellValue)){
                                CustomError.EXCEL_ERROR.setErrMsg("第"+rowNum+"行姓名和学号不对应");
                                throw new LocalRuntimeException(CustomError.EXCEL_ERROR);
                            }
                            break;
                        default:
                            CustomError.EXCEL_ERROR.setErrMsg("Excel内容错误");
                            throw new LocalRuntimeException(CustomError.EXCEL_ERROR);
                    }
                }
            }else {
                CustomError.EXCEL_ERROR.setErrMsg("Excel内容缺失");
                throw new LocalRuntimeException(CustomError.EXCEL_ERROR);
            }
        }

        for (Map.Entry<String,String> entry : finMap.entrySet()) {
            TeamMember teamMember=teamMap.get(entry.getValue());
            teamMember.setMemberUid(studentMap.get(entry.getKey()).getUid());
            teamMemberService.insertTeam(teamMember);
        }
        return "success";
    }

}