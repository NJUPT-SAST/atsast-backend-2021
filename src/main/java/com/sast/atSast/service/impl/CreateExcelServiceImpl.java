package com.sast.atSast.service.impl;

import com.sast.atSast.enums.CustomError;
import com.sast.atSast.enums.FacultyEnum;
import com.sast.atSast.exception.LocalRuntimeException;
import com.sast.atSast.model.JudgeInfo;
import com.sast.atSast.model.JudgesResult;
import com.sast.atSast.model.StudentInfo;
import com.sast.atSast.model.TeamMember;
import com.sast.atSast.pojo.*;
import com.sast.atSast.server.MinioServer;
import com.sast.atSast.service.*;
import com.sast.atSast.util.RandomUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: Acow337
 * @Date: 2021/09/08/18:10
 * @Description: 生成excel表格
 */
@Service
public class CreateExcelServiceImpl implements CreateExcelService {

    @Autowired
    private ContestService contestService;

    @Autowired
    private JudgeInfoService judgeInfoService;

    @Autowired
    private TeamMemberService teamMemberService;

    @Autowired
    private StudentInfoService studentInfoService;

    @Autowired
    private MinioServer minioServer;

    @Autowired
    private JudgesResultService judgesResultService;

    //方法公用数据
    private String contestName; // 比赛名称 科技节
    private Map<Long, String> nameMap; // key 学生uid value 学生名称
    private Map<Long, String> judgeUidMap; // key 评委uid value 评委工号
    private Map<Long, LeaderInfo> leaderMap; // key 队长uid value 队长信息
    private Map<Long, Map<String, JugdeResultInfo>> judgeResultMap; // key 队伍id value 评委工号对应评价结果
    private Map<Long, String> studentIdMap; // key 学生uid value 学生学号
    private Map<Long, List<Long>> teamMemberUidMap; // key 队伍id value 队员的uid列表
    private List<TeamMember> teams; // 队伍总信息 一个队伍对应一条数据
    private List<TeamMember> allTeams; // 队伍总信息 一个队伍对应多条数据 （多条指队伍的人数）
    private List<JudgesResult> judgesResults; // 评委结果
    private List<JudgeInfo> judgeInfos;    //评委信息
    private List<JugdeSummaryTeam> manualSummary; //制作类
    private List<JugdeSummaryTeam> natureSummary; //自然类
    private List<JugdeSummaryTeam> philosophySummary; //哲社类
    private List<LeaderInfo> leaderInfos; //队长信息

    //公用数据初始化
    private void initialize(Long contestId) {
        nameMap = new HashMap<>();
        judgeUidMap = new HashMap<>();
        manualSummary = new LinkedList<>();
        natureSummary = new LinkedList<>();
        philosophySummary = new LinkedList<>();
        leaderInfos = new LinkedList<>();

        //TODO 给contestId加个索引
        contestName = contestService.getContestById(contestId).getContestName();
        List<StudentInfo> studentInfos = studentInfoService.listStudentInfos();
        List<List<SummaryTeam>> listList = teamMemberService.getSummaryTeams(contestId);
        teams = teamMemberService.getTeamsByLeader(contestId); //每个队伍的信息
        allTeams = teamMemberService.getTeams(contestId); //每个队伍的信息
        judgeInfos = judgeInfoService.getJudgeInfo(contestId);
        judgesResults = judgesResultService.getResults(contestId); //获得比赛审评结果
        leaderInfos = teamMemberService.getLeaderInfo(contestId);   //获得所有队长信息


        // nameMap  studentIdMap
        for (StudentInfo studentInfo : studentInfos) {
            nameMap.put(studentInfo.getUid(), studentInfo.getRealName());
            studentIdMap.put(studentInfo.getUid(), studentInfo.getStuId());
        }

        // leaderMap
        for (LeaderInfo leaderInfo : leaderInfos) {
            leaderMap.put(leaderInfo.getUid(), new LeaderInfo(leaderInfo.getUid(), leaderInfo.getFaculty(), leaderInfo.getEmail()));
        }

        // judgeResultMap
        for (JudgesResult judgesResult : judgesResults) {
            if (!judgeResultMap.containsKey(judgesResult.getTeamId())) {
                Map<String, JugdeResultInfo> tempMap = new HashMap<>(); //tempMap装着评委评价信息
                JugdeResultInfo jugdeResultInfo = new JugdeResultInfo(judgesResult.getComment(), judgesResult.getScores());
                tempMap.put(judgeUidMap.get(judgesResult.getJudgeUid()), jugdeResultInfo);
                judgeResultMap.put(judgesResult.getTeamId(), tempMap);
            } else {
                JugdeResultInfo jugdeResultInfo = new JugdeResultInfo(judgesResult.getComment(), judgesResult.getScores());
                judgeResultMap.get(judgesResult.getTeamId()).put(judgeUidMap.get(judgesResult.getJudgeUid()), jugdeResultInfo);
            }
        }

        //遍历TeamMember 获得JugdeSummaryTeam 并分类
        for (TeamMember item : teams) {
            JugdeSummaryTeam jugdeSummaryTeam = new JugdeSummaryTeam();
            if (item.getLeaderUid() == item.getMemberUid()) {
                jugdeSummaryTeam.setLeaderUid(item.getLeaderUid());
            } else {
                List<Long> list = new LinkedList<>();
                list.add(item.getMemberUid());
                jugdeSummaryTeam.setMemberUids(list);
            }

            jugdeSummaryTeam.setTeamId(item.getTeamId());
            jugdeSummaryTeam.setFaculty(leaderMap.get(item.getLeaderUid()).getFaculty());
            jugdeSummaryTeam.setTeamName(item.getTeamName());
            jugdeSummaryTeam.setScoreMap(judgeResultMap.get(item.getTeamId()));
            jugdeSummaryTeam.setSubjectCategory(item.getSubjectCategory());
            jugdeSummaryTeam.setWorkType(item.getWorkType());
            jugdeSummaryTeam.setInstructorId(item.getInstructorId());
            int mapCount = 0;
            int sum = 0;
            for (Map.Entry<String, JugdeResultInfo> entry : jugdeSummaryTeam.getScoreMap().entrySet()) {
                mapCount++;
                sum = +entry.getValue().getScore();
            }
            jugdeSummaryTeam.setTotalScore(sum);
            jugdeSummaryTeam.setAverage((sum / 1.0) / mapCount);
            jugdeSummaryTeam.setJudgeNumber(mapCount);
            jugdeSummaryTeam.setInstructor(item.getInstructor());

            if (jugdeSummaryTeam.getSubjectCategory().equals("inventionA") || jugdeSummaryTeam.getSubjectCategory().equals("inventionB")) {
                manualSummary.add(jugdeSummaryTeam);
            } else if (jugdeSummaryTeam.getSubjectCategory().equals("nature")) {
                natureSummary.add(jugdeSummaryTeam);
            } else if (jugdeSummaryTeam.getSubjectCategory().equals("philosophy")) {
                philosophySummary.add(jugdeSummaryTeam);
            }
        }

        // 获得TeamMemberUidMap 获得队伍ID所对应的队员信息
        Set<Long> teamIdSet = new HashSet<>();
        for (TeamMember item : allTeams) {
            if (!teamIdSet.contains(item.getTeamId()) && item.getMemberUid() != item.getLeaderUid()) {
                List<Long> list = new LinkedList<>();
                list.add(item.getMemberUid());
                teamMemberUidMap.put(item.getTeamId(), list);
                teamIdSet.add(item.getTeamId());
            } else if (item.getMemberUid() != item.getLeaderUid()) {
                teamMemberUidMap.get(item.getMemberUid()).add(item.getMemberUid());
            }
        }
    }


    @Override
    public String createSummary(Long id, String filePath) {

        int count = 1;

        String path = "";

        //数据准备
        Map<Long, String> nameMap = new HashMap<>();
        String contestName = contestService.getContestById(id).getContestName();
        List<List<SummaryTeam>> listList = teamMemberService.getSummaryTeams(id);

        Workbook wb = new XSSFWorkbook();

        CreationHelper creationHelper = wb.getCreationHelper();

        //创建表单
        Sheet sheet = wb.createSheet(contestName);

        sheet.setDisplayGridlines(true);
        sheet.setHorizontallyCenter(true);
        sheet.setVerticallyCenter(true);

        //设置表头 第一行
        Row headRow = sheet.createRow(0);

        //设置字体
        Font font = wb.createFont();
        font.setFontName("宋体");
        font.setFontHeightInPoints((short) 16);

        //表头取名
        headRow.createCell(0).setCellValue(contestName + "获奖汇总表（用于奖状制作）");
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 4));

        font.setFontHeightInPoints((short) 11);
        sheet.createRow(1).createCell(0).setCellValue("活动名称" + contestName);
        sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 4));

        //属性名称
        sheet.createRow(2).createCell(0).setCellValue("序号");
        sheet.createRow(2).createCell(1).setCellValue("学生姓名");
        sheet.createRow(2).createCell(2).setCellValue("所获奖项");
        sheet.createRow(2).createCell(3).setCellValue("备注（请提供证书文字内容）");
        sheet.createRow(2).createCell(4).setCellValue("指导老师");

        int markRow = 3;
        //遍历数据 开始填充
        for (List<SummaryTeam> list : listList) {
            //填充前获取相应数据
            int length = list.size();
            long[] arr = new long[length];
            String prize = list.get(0).getResult();
            String instructor = list.get(0).getInstructor();
            String teamName = list.get(0).getTeamName();
            int tempCount = 0;
            for (SummaryTeam item : list) {
                arr[tempCount] = item.getMemberUid();
                tempCount++;
            }

            //对当前行进行数据填充
            for (int i = 0; i < length; i++) {
                Row currRow = sheet.createRow(markRow);
                currRow.createCell(0).setCellValue(markRow - 2);
                currRow.createCell(1).setCellValue(nameMap.get(arr[i]));
                currRow.createCell(2).setCellValue(prize);
                int begin = 0;
                if (i == 0) {
                    begin = markRow;
                    currRow.createCell(3).setCellValue(teamName);
                    currRow.createCell(4).setCellValue(instructor);
                }
                if (i == length - 1) {
                    sheet.addMergedRegion(new CellRangeAddress(begin, begin + length - 1, 3, 3));
                    sheet.addMergedRegion(new CellRangeAddress(begin, begin + length - 1, 4, 4));
                }
                markRow++;
            }
        }

        //补完底部
        sheet.createRow(markRow).createCell(0).setCellValue("学院意见");
        sheet.addMergedRegion(new CellRangeAddress(markRow, markRow + 7, 0, 0));
        sheet.createRow(markRow).createCell(1).setCellValue("");
        sheet.addMergedRegion(new CellRangeAddress(markRow, markRow + 7, 1, 4));


        //文件取名
        SimpleDateFormat ft = new SimpleDateFormat("yyyyMMdd");
        String dataString = "【" + ft.format(new Date()) + "】";

        String bucketName = contestName;
        String objectName = dataString + contestName + "_奖状制作汇总表.xlsx";

        //将输出流转化成输入流 并上传文件到minio


        try (OutputStream fileOut = new FileOutputStream(path)) {    //获取文件流
            wb.write(fileOut);   //将workbook写入文件流
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return "false";
        } catch (IOException e) {
            e.printStackTrace();
            return "false";
        }

        return "ok";

    }


    @Override
    public String createJudgeResultSummary(Long contestId, String filePath) {

        //数据准备

        //评委工号对应表格下标
        Map<String, Integer> idMap = new HashMap<>();

        String contestName = contestService.getContestById(contestId).getContestName();
        List<StudentInfo> studentInfos = studentInfoService.listStudentInfos();
        List<List<SummaryTeam>> listList = teamMemberService.getSummaryTeams(contestId);

        Workbook wb = new XSSFWorkbook();

        CreationHelper creationHelper = wb.getCreationHelper();

        //创建表单 除哲社的总表
        Sheet sheet1 = wb.createSheet("除哲社的总表");

        sheet1.setDisplayGridlines(true);
        sheet1.setHorizontallyCenter(true);
        sheet1.setVerticallyCenter(true);

        //设置表头 第一行
        Row headRow = sheet1.createRow(0);

        //设置字体
        Font font = wb.createFont();
        font.setFontName("宋体");
        font.setFontHeightInPoints((short) 12);

        //设置单元格背景颜色
//        CellStyle style = wb.createCellStyle();
//        style.setFillBackgroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());

        //属性名称 循环部分是评委工号
        sheet1.createRow(0).createCell(0).setCellValue("作品编号");
        sheet1.createRow(0).createCell(1).setCellValue("作品名称");
        sheet1.createRow(0).createCell(2).setCellValue("作品类别");
        sheet1.createRow(0).createCell(3).setCellValue("学科类别");
        sheet1.createRow(0).createCell(4).setCellValue("组长账号");
        sheet1.createRow(0).createCell(5).setCellValue("学院");
        int count = 6; //列计数
        for (JudgeInfo judgeInfo : judgeInfos) {
            sheet1.createRow(0).createCell(count).setCellValue(judgeInfo.getJudgeId());
            idMap.put(judgeInfo.getJudgeId(), count);
            sheet1.addMergedRegion(new CellRangeAddress(0, 0, count, count + 1));
            count = count + 2;
        }
        int index1 = count;
        sheet1.createRow(0).createCell(count++).setCellValue("总分");
        int index2 = count;
        sheet1.createRow(0).createCell(count++).setCellValue("平均分");
        int index3 = count;
        sheet1.createRow(0).createCell(count++).setCellValue("评委人数");

        int rowCount = 1; //行计数

        //对制作类和自然类的信息进行遍历 写入Excel表格中
        for (JugdeSummaryTeam item : manualSummary) {
            Row tempRow = sheet1.createRow(rowCount);
            tempRow.createCell(0).setCellValue(item.getTeamId());
            tempRow.createCell(1).setCellValue(item.getTeamName());
            tempRow.createCell(2).setCellValue(item.getWorkType());
            tempRow.createCell(3).setCellValue(item.getSubjectCategory());
            tempRow.createCell(4).setCellValue(leaderMap.get(item.getLeaderUid()).getEmail());
            tempRow.createCell(5).setCellValue(leaderMap.get(item.getLeaderUid()).getFaculty());
            tempRow.createCell(index1).setCellValue(item.getTotalScore());
            tempRow.createCell(index2).setCellValue(item.getAverage());
            tempRow.createCell(index3).setCellValue(item.getJudgeNumber());

            for (Map.Entry<String, JugdeResultInfo> entry : item.getScoreMap().entrySet()) {
                tempRow.createCell(idMap.get(entry.getKey())).setCellValue(entry.getValue().getScore());
                tempRow.createCell(idMap.get(entry.getKey()) + 1).setCellValue(entry.getValue().getComment());
            }
            rowCount++;
        }
        for (JugdeSummaryTeam item : natureSummary) {
            Row tempRow = sheet1.createRow(rowCount);
            tempRow.createCell(0).setCellValue(item.getTeamId());
            tempRow.createCell(1).setCellValue(item.getTeamName());
            tempRow.createCell(2).setCellValue(item.getWorkType());
            tempRow.createCell(3).setCellValue(item.getSubjectCategory());
            tempRow.createCell(4).setCellValue(leaderMap.get(item.getLeaderUid()).getEmail());
            tempRow.createCell(5).setCellValue(leaderMap.get(item.getLeaderUid()).getFaculty());
            tempRow.createCell(index1).setCellValue(item.getTotalScore());
            tempRow.createCell(index2).setCellValue(item.getAverage());
            tempRow.createCell(index3).setCellValue(item.getJudgeNumber());

            for (Map.Entry<String, JugdeResultInfo> entry : item.getScoreMap().entrySet()) {
                tempRow.createCell(idMap.get(entry.getKey())).setCellValue(entry.getValue().getScore());
                tempRow.createCell(idMap.get(entry.getKey()) + 1).setCellValue(entry.getValue().getComment());
            }

            rowCount++;
        }

        //TODO 制作类 自然类 哲社类
        Sheet sheet2 = wb.createSheet("制作类");
        Sheet sheet3 = wb.createSheet("自然类");
        Sheet sheet4 = wb.createSheet("哲社类");
        List<Sheet> sheets = new LinkedList<>();
        sheets.add(sheet2);
        sheets.add(sheet3);
        sheets.add(sheet4);

        for (Sheet curSheet : sheets) {

            curSheet.setDisplayGridlines(true);
            curSheet.setHorizontallyCenter(true);
            curSheet.setVerticallyCenter(true);

            CellStyle style = wb.createCellStyle();
            style.setFillBackgroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());

            headRow = curSheet.createRow(0);

            //头部列取名
            headRow.createCell(0).setCellValue("作品编号");
            headRow.createCell(1).setCellValue("作品名称");
            headRow.createCell(2).setCellValue("作品类别");
            headRow.createCell(3).setCellValue("学科类别");
            headRow.createCell(4).setCellValue("组长账号");
            headRow.createCell(5).setCellValue("学院");
            headRow.createCell(6).setCellValue("总分");
            headRow.createCell(7).setCellValue("平均分");
            headRow.createCell(8).setCellValue("评委人数");
            headRow.createCell(10).setCellValue("小计");

            //颜色填充
            headRow.getCell(0).setCellStyle(style);
            headRow.getCell(1).setCellStyle(style);
            headRow.getCell(2).setCellStyle(style);
            headRow.getCell(3).setCellStyle(style);
            headRow.getCell(4).setCellStyle(style);
            headRow.getCell(5).setCellStyle(style);
            headRow.getCell(6).setCellStyle(style);
            headRow.getCell(7).setCellStyle(style);
            headRow.getCell(8).setCellStyle(style);
            headRow.getCell(10).setCellStyle(style);

            //遍历 填充数据
            rowCount = 1;
            List<JugdeSummaryTeam> list = new LinkedList<>();
            if (curSheet.getSheetName().equals("制作类")) {
                list = manualSummary;
            } else if (curSheet.getSheetName().equals("自然类")) {
                list = natureSummary;
            } else if (curSheet.getSheetName().equals("哲社类")) {
                list = philosophySummary;
            }

            for (JugdeSummaryTeam item : manualSummary) {
                Row tempRow = curSheet.createRow(rowCount);
                tempRow.createCell(0).setCellValue(item.getTeamId());
                tempRow.createCell(1).setCellValue(item.getTeamName());
                tempRow.createCell(2).setCellValue(item.getWorkType());
                tempRow.createCell(3).setCellValue(item.getSubjectCategory());
                tempRow.createCell(4).setCellValue(leaderMap.get(item.getLeaderUid()).getEmail());
                tempRow.createCell(5).setCellValue(leaderMap.get(item.getLeaderUid()).getFaculty());
                tempRow.createCell(6).setCellValue(item.getTotalScore());
                tempRow.createCell(7).setCellValue(item.getAverage());
                tempRow.createCell(8).setCellValue(item.getJudgeNumber());
                rowCount++;
            }
        }


        //TODO 哲社草稿箱
        Sheet sheet5 = wb.createSheet("哲社草稿箱");

        sheet5.setDisplayGridlines(true);
        sheet5.setHorizontallyCenter(true);
        sheet5.setVerticallyCenter(true);

        //设置表头 第一行
        headRow = sheet5.createRow(0);

        //设置字体
        font.setFontName("宋体");
        font.setFontHeightInPoints((short) 12);

        //属性名称 循环部分是评委工号
        sheet5.createRow(0).createCell(0).setCellValue("作品编号");
        sheet5.createRow(0).createCell(1).setCellValue("作品名称");
        sheet5.createRow(0).createCell(2).setCellValue("作品类别");
        sheet5.createRow(0).createCell(3).setCellValue("学科类别");
        sheet5.createRow(0).createCell(4).setCellValue("用户名");
        sheet5.createRow(0).createCell(5).setCellValue("学院");
        count = 6; //列计数
        for (JudgeInfo judgeInfo : judgeInfos) {
            sheet5.createRow(0).createCell(count).setCellValue(judgeInfo.getJudgeId());
            idMap.put(judgeInfo.getJudgeId(), count);
            sheet5.addMergedRegion(new CellRangeAddress(0, 0, count, count + 1));
            count = count + 2;
        }
        index1 = count;
        sheet5.createRow(0).createCell(count++).setCellValue("总分");
        index2 = count;
        sheet5.createRow(0).createCell(count++).setCellValue("平均分");
        index3 = count;
        sheet5.createRow(0).createCell(count++).setCellValue("评委人数");

        rowCount = 1; //行计数

        //遍历 填写每行数据
        for (JugdeSummaryTeam item : philosophySummary) {
            Row tempRow = sheet1.createRow(rowCount);
            tempRow.createCell(0).setCellValue(item.getTeamId());
            tempRow.createCell(1).setCellValue(item.getTeamName());
            tempRow.createCell(2).setCellValue(item.getWorkType());
            tempRow.createCell(3).setCellValue(item.getSubjectCategory());
            tempRow.createCell(4).setCellValue(leaderMap.get(item.getLeaderUid()).getEmail());
            tempRow.createCell(5).setCellValue(leaderMap.get(item.getLeaderUid()).getFaculty());
            tempRow.createCell(index1).setCellValue(item.getTotalScore());
            tempRow.createCell(index2).setCellValue(item.getAverage());
            tempRow.createCell(index3).setCellValue(item.getJudgeNumber());

            for (Map.Entry<String, JugdeResultInfo> entry : item.getScoreMap().entrySet()) {
                tempRow.createCell(idMap.get(entry.getKey())).setCellValue(entry.getValue().getScore());
                tempRow.createCell(idMap.get(entry.getKey()) + 1).setCellValue(entry.getValue().getComment());
            }
            rowCount++;
        }


        //TODO 导出的总表
        Sheet sheet6 = wb.createSheet("导出的总表");

        sheet6.setDisplayGridlines(true);
        sheet6.setHorizontallyCenter(true);
        sheet6.setVerticallyCenter(true);

        //设置表头 第一行
        headRow = sheet6.createRow(0);

        //设置字体
        font.setFontName("宋体");
        font.setFontHeightInPoints((short) 12);

        //属性名称 循环部分是评委工号
        sheet6.createRow(0).createCell(0).setCellValue("作品编号");
        sheet6.createRow(0).createCell(1).setCellValue("作品名称");
        sheet6.createRow(0).createCell(2).setCellValue("作品类别");
        sheet6.createRow(0).createCell(3).setCellValue("学科类别");
        sheet6.createRow(0).createCell(4).setCellValue("用户名");
        sheet6.createRow(0).createCell(5).setCellValue("学院");
        count = 6; //列计数
        for (JudgeInfo judgeInfo : judgeInfos) {
            sheet6.createRow(0).createCell(count).setCellValue(judgeInfo.getJudgeId());
            idMap.put(judgeInfo.getJudgeId(), count);
            sheet6.addMergedRegion(new CellRangeAddress(0, 0, count, count + 1));
            count = count + 2;
        }
        index1 = count;
        sheet6.createRow(0).createCell(count++).setCellValue("总分");
        index2 = count;
        sheet6.createRow(0).createCell(count++).setCellValue("平均分");
        index3 = count;
        sheet6.createRow(0).createCell(count++).setCellValue("评委人数");

        rowCount = 1; //行计数

        //遍历 填写每行数据
        for (JugdeSummaryTeam item : philosophySummary) {
            Row tempRow = sheet1.createRow(rowCount);
            tempRow.createCell(0).setCellValue(item.getTeamId());
            tempRow.createCell(1).setCellValue(item.getTeamName());
            tempRow.createCell(2).setCellValue(item.getWorkType());
            tempRow.createCell(3).setCellValue(item.getSubjectCategory());
            tempRow.createCell(4).setCellValue(leaderMap.get(item.getLeaderUid()).getEmail());
            tempRow.createCell(5).setCellValue(leaderMap.get(item.getLeaderUid()).getFaculty());
            tempRow.createCell(index1).setCellValue(item.getTotalScore());
            tempRow.createCell(index2).setCellValue(item.getAverage());
            tempRow.createCell(index3).setCellValue(item.getJudgeNumber());

            for (Map.Entry<String, JugdeResultInfo> entry : item.getScoreMap().entrySet()) {
                tempRow.createCell(idMap.get(entry.getKey())).setCellValue(entry.getValue().getScore());
                tempRow.createCell(idMap.get(entry.getKey()) + 1).setCellValue(entry.getValue().getComment());
            }
            rowCount++;
        }


        try (OutputStream fileOut = new FileOutputStream(filePath)) {    //获取文件流
            wb.write(fileOut);   //将workbook写入文件流
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return "false";
        } catch (IOException e) {
            e.printStackTrace();
            return "false";
        }

        return "ok";

    }

    //评审汇总表 报名 初赛 复赛结束
    public String createJudgeSummary(Long contestId, String filePath) {
        Workbook wb = new XSSFWorkbook();

        CreationHelper creationHelper = wb.getCreationHelper();

        //创建表单
        List<Sheet> sheets = new LinkedList<>();
        Sheet sheet1 = wb.createSheet("制作类");
        Sheet sheet2 = wb.createSheet("哲社类");
        Sheet sheet3 = wb.createSheet("自然类");
        sheets.add(sheet1);
        sheets.add(sheet2);
        sheets.add(sheet3);

        //字体设置
        Font font = wb.createFont();
        font.setFontName("宋体");
        font.setFontHeightInPoints((short) 18);

        for (Sheet sheet : sheets) {

            //设置显示网格线 水平垂直居中
            sheet.setDisplayGridlines(true);
            sheet.setHorizontallyCenter(true);
            sheet.setVerticallyCenter(true);

            //表头输入
            Row headRow = sheet.createRow(0);
            headRow.createCell(0).setCellValue(contestName + "作品汇总表");
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 13));

            Row secondRow = sheet.createRow(1);
            secondRow.createCell(0).setCellValue("序号");
            secondRow.createCell(1).setCellValue("学院");
            secondRow.createCell(2).setCellValue("作品名称");
            secondRow.createCell(3).setCellValue("作品类别");
            secondRow.createCell(4).setCellValue("学科类别");
            secondRow.createCell(5).setCellValue("组别");
            secondRow.createCell(6).setCellValue("是否sttip");
            secondRow.createCell(7).setCellValue("申报者姓名");
            secondRow.createCell(9).setCellValue("学号");
            secondRow.createCell(19).setCellValue("指导老师");
            secondRow.createCell(11).setCellValue("指导老师工号");
            secondRow.createCell(12).setCellValue("签到");
            secondRow.createCell(13).setCellValue("确认信息");
            sheet.addMergedRegion(new CellRangeAddress(1, 1, 7, 8));


            List<JugdeSummaryTeam> list = new LinkedList<>();
            if (sheet.getSheetName() == "制作类") {
                list = manualSummary;
            } else if (sheet.getSheetName() == "哲社类") {
                list = philosophySummary;
            } else if (sheet.getSheetName() == "自然类") {
                list = natureSummary;
            }

            int count = 2; //行号计数
            int orderCount = 1; //序号

            // 遍历 数据填充
            for (JugdeSummaryTeam item : list) {
                int rowSize = 0;
                if (item.getMemberUids() == null) {
                    rowSize = 1;
                } else {
                    rowSize = item.getMemberUids().size() + 1;
                }
                Row tempRow = sheet.createRow(count);
                tempRow.createCell(0).setCellValue(orderCount);
                tempRow.createCell(1).setCellValue(item.getFaculty());
                tempRow.createCell(2).setCellValue(item.getTeamName());
                tempRow.createCell(3).setCellValue(item.getWorkType());
                tempRow.createCell(4).setCellValue(item.getSubjectCategory());
                tempRow.createCell(5).setCellValue(item.getTeamGroup());
                if (new RandomUtils().getRandomResult20PerTrue()) {
                    tempRow.createCell(6).setCellValue("是");
                } else {
                    tempRow.createCell(6).setCellValue("否");
                }

                //合并前7列和11列的单元格
                for (int i = 0; i < 7; i++) {
                    sheet.addMergedRegion(new CellRangeAddress(count, count + rowSize - 1, i, i));
                }
                sheet.addMergedRegion(new CellRangeAddress(count, count + rowSize - 1, 10, 10));

                tempRow.createCell(7).setCellValue("第一作者");
                tempRow.createCell(8).setCellValue(nameMap.get(item.getLeaderUid()));
                tempRow.createCell(9).setCellValue(studentIdMap.get(item.getLeaderUid()));
                if (rowSize != 1) {
                    Row anoTempRow = sheet.createRow(count + 1);
                    anoTempRow.createCell(7).setCellValue("合作者");

                    int anoCount = count + 1; //填充成员信息时的行号计数
                    for (Long memberUid : item.getMemberUids()) {
                        sheet.createRow(anoCount).createCell(8).setCellValue(nameMap.get(memberUid));
                        sheet.createRow(anoCount).createCell(9).setCellValue(studentIdMap.get(memberUid));
                        anoCount++;
                    }

                    //对 合作者 这一单元格进行合并
                    sheet.addMergedRegion(new CellRangeAddress(count + 1, count + item.getMemberUids().size(), 7, 7));
                }
                count += rowSize;
                orderCount++;
            }
        }
        try (OutputStream fileOut = new FileOutputStream(filePath)) {    //获取文件流
            wb.write(fileOut);   //将workbook写入文件流
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return "false";
        } catch (IOException e) {
            e.printStackTrace();
            return "false";
        }
        return "ok";

    }


    @Override
    public String createScoreSummary(Long contestId, String filePath) {
        return null;
    }


    //TODO 签到表 复赛结束
    @Override
    public String createRegisterSummary(Long contestId, String filePath) {

        Workbook wb = new XSSFWorkbook();

        CreationHelper creationHelper = wb.getCreationHelper();

        //设置字体
        Font font = wb.createFont();

        //创建表单
        List<Sheet> sheets = new LinkedList<>();
        Sheet sheet1 = wb.createSheet("制作类A区签到表");
        Sheet sheet2 = wb.createSheet("制作类B区签到表");
        Sheet sheet3 = wb.createSheet("制作类C区签到表");
        Sheet sheet4 = wb.createSheet("哲社类A区签到表");
        Sheet sheet5 = wb.createSheet("哲社类B区签到表");
        Sheet sheet6 = wb.createSheet("自然类A区签到表");
        sheets.add(sheet1);
        sheets.add(sheet2);
        sheets.add(sheet3);
        sheets.add(sheet4);
        sheets.add(sheet5);
        sheets.add(sheet6);

        // 各个区的数据
        /**
         * list1 list2 list3 制作类 A B C 区
         * list4 list5 哲社类 A B 区
         * list6 自然类 A 区
         */
        List<JugdeSummaryTeam> list1 = new LinkedList<>();
        List<JugdeSummaryTeam> list2 = new LinkedList<>();
        List<JugdeSummaryTeam> list3 = new LinkedList<>();
        List<JugdeSummaryTeam> list4 = new LinkedList<>();
        List<JugdeSummaryTeam> list5 = new LinkedList<>();
        List<JugdeSummaryTeam> list6 = new LinkedList<>();

        // 分别是 制作类 哲社类 每个表单的期望尺寸
        int expectedSize1 = manualSummary.size() / 3;
        int expectedSize2 = philosophySummary.size() / 2;

        // 分别是 本科生 研究生 的栈
        Stack<JugdeSummaryTeam> undergraduateStack = new Stack<>();
        Stack<JugdeSummaryTeam> postgraduateStack = new Stack<>();

        //对 制作类 进行分类 本科生 研究生
        for (JugdeSummaryTeam item : manualSummary) {
            if (item.getTeamGroup().equals("研究生")) {
                postgraduateStack.add(item);
            } else if (item.getTeamGroup().equals("本科生")) {
                undergraduateStack.add(item);
            } else {
                CustomError.UNKNOWN_ERROR.setErrMsg("数据出现问题（出现 本科生 研究生 以外的数据）");
                throw new LocalRuntimeException(CustomError.UNKNOWN_ERROR);
            }
        }

        //对 list1 list2 list3 进行数据填充
        //此时为研究生队数量小于期望队数的情况
        if (postgraduateStack.size() <= expectedSize1) {
            while (!postgraduateStack.isEmpty()) {
                list1.add(postgraduateStack.peek());
            }
            while (!undergraduateStack.isEmpty()) {
                if (list1.size() < expectedSize1) {
                    list1.add(undergraduateStack.peek());
                } else if (list2.size() < expectedSize1) {
                    list2.add(undergraduateStack.peek());
                } else {
                    list3.add(undergraduateStack.peek());
                }
            }
        } else { //此时为研究生队数量大于期望队数的情况 (少数情况)
            expectedSize1 = (manualSummary.size() - postgraduateStack.size()) / 2;
            while (!postgraduateStack.isEmpty()) {
                list1.add(postgraduateStack.peek());
            }
            while (!undergraduateStack.isEmpty()) {
                if (list2.size() < expectedSize1) {
                    list2.add(undergraduateStack.peek());
                } else {
                    list3.add(undergraduateStack.peek());
                }
            }
        }

        //对 哲社类 进行分类 本科生 研究生
        for (JugdeSummaryTeam item : philosophySummary) {
            if (item.getTeamGroup().equals("研究生")) {
                postgraduateStack.add(item);
            } else if (item.getTeamGroup().equals("本科生")) {
                undergraduateStack.add(item);
            } else {
                CustomError.UNKNOWN_ERROR.setErrMsg("数据出现问题（出现 本科生 研究生 以外的数据）");
                throw new LocalRuntimeException(CustomError.UNKNOWN_ERROR);
            }
        }

        //对 list4 list5 进行数据填充
        //此时为研究生队数量小于期望队数的情况
        if (postgraduateStack.size() <= expectedSize2) {
            while (!postgraduateStack.isEmpty()) {
                list4.add(postgraduateStack.peek());
            }
            while (!undergraduateStack.isEmpty()) {
                if (list4.size() < expectedSize2) {
                    list4.add(undergraduateStack.peek());
                } else {
                    list5.add(undergraduateStack.peek());
                }
            }
        } else { //此时为研究生队数量大于期望队数的情况 (少数情况)
            while (!postgraduateStack.isEmpty()) {
                list4.add(postgraduateStack.peek());
            }
            while (!undergraduateStack.isEmpty()) {
                list5.add(undergraduateStack.peek());
            }
        }

        //对 自然类 进行分类 本科生 研究生
        for (JugdeSummaryTeam item : natureSummary) {
            if (item.getTeamGroup().equals("研究生")) {
                postgraduateStack.add(item);
            } else if (item.getTeamGroup().equals("本科生")) {
                undergraduateStack.add(item);
            } else {
                CustomError.UNKNOWN_ERROR.setErrMsg("数据出现问题（出现 本科生 研究生 以外的数据）");
                throw new LocalRuntimeException(CustomError.UNKNOWN_ERROR);
            }
        }

        //对list6 进行数据填充
        while (!postgraduateStack.isEmpty()) {
            list6.add(postgraduateStack.peek());
        }
        while (!undergraduateStack.isEmpty()) {
            list6.add(undergraduateStack.peek());
        }


        // 开始填写表单
        for (Sheet sheet : sheets) {
            String mark = "";
            if (sheet.getSheetName().equals("制作类A区签到表") || sheet.getSheetName().equals("哲社类A区签到表") || sheet.getSheetName().equals("自然类A区签到表")) {
                mark = "A";
            } else if (sheet.getSheetName().equals("制作类B区签到表") || sheet.getSheetName().equals("哲社类B区签到表")) {
                mark = "B";
            } else if (sheet.getSheetName().equals("制作类C区签到表")) {
                mark = "C";
            }
            List<JugdeSummaryTeam> list = new LinkedList<>();
            switch (sheet.getSheetName()) {
                case "制作类A区签到表":
                    list = list1;
                    break;
                case "制作类B区签到表":
                    list = list2;
                    break;
                case "制作类C区签到表":
                    list = list3;
                    break;
                case "哲社类A区签到表":
                    list = list4;
                    break;
                case "哲社类B区签到表":
                    list = list5;
                    break;
                case "自然类A区签到表":
                    list = list6;
                    break;
            }

            // 水平垂直居中 带边框
            sheet.setDisplayGridlines(true);
            sheet.setHorizontallyCenter(true);
            sheet.setVerticallyCenter(true);

            font.setFontName("等线");
            font.setFontHeightInPoints((short) 16);

            sheet.createRow(0).createCell(0).setCellValue(sheet.getSheetName());
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 9));

            font.setFontHeightInPoints((short) 12);

            Row secondRow = sheet.createRow(1);
            secondRow.createCell(0).setCellValue("序号");
            secondRow.createCell(1).setCellValue("作品名称");
            secondRow.createCell(2).setCellValue("单位");
            secondRow.createCell(3).setCellValue("申报者姓名");
            secondRow.createCell(5).setCellValue("学号");
            secondRow.createCell(6).setCellValue("指导老师");
            secondRow.createCell(7).setCellValue("指导老师工号");
            secondRow.createCell(8).setCellValue("签到");
            secondRow.createCell(9).setCellValue("是否确认信息");
            sheet.addMergedRegion(new CellRangeAddress(1, 1, 3, 4));

            int count = 2; //行号计数
            int orderCount = 1; //序号

            // 遍历 数据填充
            for (JugdeSummaryTeam item : list) {
                int rowSize = 0;
                if (item.getMemberUids() == null) {
                    rowSize = 1;
                } else {
                    rowSize = item.getMemberUids().size() + 1;
                }
                Row tempRow = sheet.createRow(count);
                tempRow.createCell(0).setCellValue(mark + orderCount);
                tempRow.createCell(1).setCellValue(item.getTeamName());
                tempRow.createCell(2).setCellValue(item.getFaculty());
                tempRow.createCell(6).setCellValue(item.getInstructor());
                tempRow.createCell(7).setCellValue(item.getInstructorId());

                //合并前3列和7 8 9 10列的单元格
                for (int i = 0; i < 3; i++) {
                    sheet.addMergedRegion(new CellRangeAddress(count, count + rowSize - 1, i, i));
                }
                for (int i = 6; i < 10; i++) {
                    sheet.addMergedRegion(new CellRangeAddress(count, count + rowSize - 1, i, i));
                }

                tempRow.createCell(3).setCellValue("第一作者");
                tempRow.createCell(4).setCellValue(nameMap.get(item.getLeaderUid()));
                tempRow.createCell(5).setCellValue(studentIdMap.get(item.getLeaderUid()));
                if (rowSize != 1) {
                    Row anoTempRow = sheet.createRow(count + 1);
                    anoTempRow.createCell(3).setCellValue("合作者");

                    int anoCount = count + 1; //填充成员信息时的行号计数
                    for (Long memberUid : item.getMemberUids()) {
                        sheet.createRow(anoCount).createCell(4).setCellValue(nameMap.get(memberUid));
                        sheet.createRow(anoCount).createCell(5).setCellValue(studentIdMap.get(memberUid));
                        anoCount++;
                    }

                    //对 合作者 这一单元格进行合并
                    sheet.addMergedRegion(new CellRangeAddress(count + 1, count + item.getMemberUids().size(), 3, 3));
                }
                count += rowSize;
                orderCount++;
            }


        }


        return null;
    }

    @Override
    public String createJudgeScoreSummary(Long contestId, String filePath) {
        return null;
    }

    @Override
    public String createStaticSummary(Long contestId, String filePath) {

        Map<String, FacultyStatistic> facultyMap = new HashMap<>(); //学院名称对应各类型比赛队伍数量

        //初始化facultyMap
        for (FacultyEnum facultyEnum : FacultyEnum.values()) {
            facultyMap.put(facultyEnum.getFacultyName(), new FacultyStatistic());
        }

        //遍历自然类
        for (JugdeSummaryTeam item : natureSummary) {
            facultyMap.get(item.getFaculty()).setNature(facultyMap.get(item.getFaculty()).getNature() + 1);
        }

        //遍历哲学类
        for (JugdeSummaryTeam item : philosophySummary) {
            facultyMap.get(item.getFaculty()).setPhilosophy(facultyMap.get(item.getFaculty()).getPhilosophy() + 1);
        }

        //遍历制作类
        for (JugdeSummaryTeam item : manualSummary) {
            if (item.getWorkType().equals("inventionA")) {
                facultyMap.get(item.getFaculty()).setInventionA(facultyMap.get(item.getFaculty()).getInventionA() + 1);
            } else if (item.getWorkType().equals("inventionB")) {
                facultyMap.get(item.getFaculty()).setInventionB(facultyMap.get(item.getFaculty()).getInventionB() + 1);
            } else {
                CustomError.UNKNOWN_ERROR.setErrMsg("数据库数据（作品类别）出现错误（出现了 inventionA inventionB 以外的数据）");
                throw new LocalRuntimeException(CustomError.UNKNOWN_ERROR);
            }
        }

        Workbook wb = new XSSFWorkbook();

        CreationHelper creationHelper = wb.getCreationHelper();

        //设置字体
        Font font = wb.createFont();

        Sheet sheet = wb.createSheet("统计表");

        // 水平垂直居中 带边框
        sheet.setDisplayGridlines(true);
        sheet.setHorizontallyCenter(true);
        sheet.setVerticallyCenter(true);

        font.setFontName("宋体");
        font.setFontHeightInPoints((short) 14);

        Row headRow=sheet.createRow(0);
        headRow.createCell(1).setCellValue("自然类");
        headRow.createCell(2).setCellValue("哲学类");
        headRow.createCell(3).setCellValue("科技A");
        headRow.createCell(4).setCellValue("科技B");
        headRow.createCell(5).setCellValue("总计");
        headRow.createCell(6).setCellValue("备注");



        return null;
    }

}
