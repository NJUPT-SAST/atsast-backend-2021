package com.sast.atSast.controller;

import com.sast.atSast.model.Video;
import com.sast.atSast.service.impl.VideoServiceImpl;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VideoController {

    @Autowired
    VideoServiceImpl videoService;

    @ResponseBody
    @PostMapping("/admin/uploadvideo")
    public void getVideo(@RequestParam("contestId") long contestId,
                         @RequestParam("videoUrl") String videoUrl){
        videoService.getVideo(contestId, videoUrl);
    }
}
