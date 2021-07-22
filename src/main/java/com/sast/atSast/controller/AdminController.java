package com.sast.atSast.controller;

import com.sast.atSast.model.Contest;
import com.sast.atSast.model.FileStd;
import com.sast.atSast.model.Stage;
import com.sast.atSast.pojo.FileTemp;
import com.sast.atSast.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class AdminController {

    @Autowired
    private ContestService contestService;

    @Autowired
    private StageService stageService;

    @Autowired
    private VideoService videoService;

    @Autowired
    private PictureService pictureService;

    @Autowired
    private FileStdService fileStdService;

    @Autowired
    private ExcelService excelService;

    /**
     * @param contest 前端传递json字符串自动打包成对象
     * @description 前端传递json字符串，springboot中可以自动打包
     */
    @ResponseBody
    @PostMapping("/admin/createcontest")
    public String createContest(@RequestBody Contest contest) {
        contest.setStages(Integer.parseInt(String.valueOf(contest.getStageTemps().size())));
        contestService.createContest(contest);
        for (Stage stage : contest.getStageTemps()) {
            stageService.createStage(stage);
        }
        return "ok";
    }

    /**
     * @param contestId 比赛id
     * @param pushLink  推送链接url(s)
     * @description 通过对应的比赛id，更新对应的推送链接
     */
    @ResponseBody
    @PostMapping("/admin/uploadlink")
    public String addpushLink(long contestId, String pushLink) {
        contestService.updatepushLink(contestId, pushLink);
        return "ok";
    }

    /**
     * @param contestId 比赛id
     * @param videoPath 视频url
     * @desription 上传视频url（单个）
     */
    @ResponseBody
    @PostMapping("/admin/uploadvideo")
    public String getVideo(long contestId, @RequestParam("videoUrl") String videoPath) {
        videoService.addVideo(contestId, videoPath);
        return "ok";
    }

    /**
     * @param contestId 比赛id
     * @param picPath   照片urls
     * @desription 上传照片urls
     */
    @ResponseBody
    @PostMapping("/admin/updatepic")
    public String addPictures(long contestId, @RequestParam("picUrls") String picPath) {
        pictureService.addPictures(contestId, picPath);
        return "ok";
    }

    /**
     * @param fileStd 自动装配为对象
     * @desription 管理员设定提交文件的各种信息
     */
    @ResponseBody
    @PostMapping("/admin/editfilestd")
    public String addFile(@RequestBody FileStd fileStd) {
        fileStdService.addFile(fileStd);
        return "ok";
    }

    /**
     * @param stageId   阶段id
     * @param contestId 比赛id
     * @desription 获取设定文件信息
     */
    @ResponseBody
    @GetMapping("/admin/editfilestd")
    public FileTemp getFileMessageById(long stageId, long contestId) {
        FileStd fileStd = fileStdService.getFileMessageById(stageId, contestId);
        System.out.println(fileStd);
        return new FileTemp(fileStd.getFileDescription(), fileStd.getFileLimit());
    }




}
