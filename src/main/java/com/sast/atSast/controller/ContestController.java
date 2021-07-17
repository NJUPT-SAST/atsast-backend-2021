package com.sast.atSast.controller;

import com.alibaba.fastjson.JSON;
import com.sast.atSast.model.Contest;
import com.sast.atSast.model.Stage;
import com.sast.atSast.service.impl.ContestServiceImpl;
import com.sast.atSast.service.impl.VideoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ContestController {

    @Autowired
    private ContestServiceImpl contestService;

    @Autowired
    VideoServiceImpl videoService;

    /**
     * @description 前端传递json字符串，通过fastjson实现自动分配
     * @param data 所有信息的长字符串
     */
    @ResponseBody
    @PostMapping("/admin/createcontest")
    public void createContest(@RequestBody String data){
        Contest contest = JSON.parseObject(data, Contest.class);
        List<Stage> stages = JSON.parseArray(contest.getStages(), Stage.class);
		contestService.createContest(contest);
        for (Stage stage : stages) {
            contestService.createStage(stage);
        }
    }

    /**
     * @description 通过对应的比赛id，上传对应的推送链接
     * @param contestId 比赛id
     * @param pushLink 推送链接url(s)
     */
    @ResponseBody
    @PostMapping("/admin/uploadlink")
    public void addpushLink(@RequestParam("contestId") int contestId,@RequestParam("pushLink") String pushLink){
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
