package com.sast.atSast.service.impl;

import com.sast.atSast.mapper.VideoMapper;
import com.sast.atSast.model.Video;
import com.sast.atSast.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VideoServiceImpl implements VideoService {

    @Autowired
    VideoMapper videoMapper;

    @Override
    public void getVideo(long contestId, String videoUrl) {
        videoMapper.getVideo(contestId, videoUrl);
    }
}
