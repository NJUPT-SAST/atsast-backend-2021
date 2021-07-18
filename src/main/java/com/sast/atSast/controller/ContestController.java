package com.sast.atSast.controller;

import com.sast.atSast.model.Contest;
import com.sast.atSast.model.Stage;
import com.sast.atSast.service.ContestService;
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

    /**
     * @description 前端传递json字符串，通过fastjson实现自动分配
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
     * @description 通过对应的比赛id，上传对应的推送链接
     * @param contestId 比赛id
     * @param pushLink 推送链接url(s)
     */
    @ResponseBody
    @PostMapping("/admin/uploadlink")
    public void addpushLink(@RequestParam("contestId") int contestId, @RequestParam("pushLink") String pushLink){
        contestService.updatepushLink(contestId, pushLink);
    }

    /**
     * @desription 上传视频url（单个）
     * @param contestId 比赛id
     * @param videoUrl 视频url
     */
    @ResponseBody
    @PostMapping("/admin/uploadvideo")
    public void getVideo(@RequestParam("contestId") long contestId,
                         @RequestParam("videoUrl") String videoUrl){
        videoService.getVideo(contestId, videoUrl);
    }
}
