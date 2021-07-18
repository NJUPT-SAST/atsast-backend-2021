package com.sast.atSast.controller;

import com.sast.atSast.model.Contest;
import com.sast.atSast.model.Stage;
import com.sast.atSast.service.ContestService;
import com.sast.atSast.service.PictureService;
import com.sast.atSast.service.StageService;
import com.sast.atSast.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class ContestController {

    @Autowired
    private ContestService contestService;

    @Autowired
    private StageService stageService;

    @Autowired
    private VideoService videoService;

    @Autowired
    private PictureService pictureService;

    /**
     * @description 前端传递json字符串，springboot中可以自动打包
     * @param contest 前端传递json字符串自动打包成对象
     */
    @ResponseBody
    @PostMapping("/admin/createcontest")
    public void createContest(@RequestBody Contest contest){
        contest.setStages(Integer.parseInt(String.valueOf(contest.getStageTemps().size())));
        contestService.createContest(contest);
        for (Stage stage : contest.getStageTemps()){
            stageService.createStage(stage);
        }
    }

    /**
     * @description 通过对应的比赛id，更新对应的推送链接
     * @param contestId 比赛id
     * @param pushLink 推送链接url(s)
     */
    @ResponseBody
    @PostMapping("/admin/uploadlink")
    public void addpushLink(long contestId, String pushLink){
        contestService.updatepushLink(contestId, pushLink);
    }

    /**
     * @desription 上传视频url（单个）
     * @param contestId 比赛id
     * @param videoPath 视频url
     */
    @ResponseBody
    @PostMapping("/admin/uploadvideo")
    public void getVideo(long contestId, @RequestParam("videoUrl") String videoPath){
        videoService.addVideo(contestId, videoPath);
    }

    /**
     * @desription 上传照片urls
     * @param contestId 比赛id
     * @param picPath 照片urls
     */
    @ResponseBody
    @PostMapping("/admin/updatepic")
    public void addPictures(long contestId, @RequestParam("picUrls") String picPath) {
        pictureService.addPictures(contestId, picPath);
    }

}
