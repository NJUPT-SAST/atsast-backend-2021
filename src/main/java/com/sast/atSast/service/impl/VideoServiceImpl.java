package com.sast.atSast.service.impl;

import com.sast.atSast.mapper.VideoMapper;
import com.sast.atSast.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VideoServiceImpl implements VideoService {

    @Autowired
    private VideoMapper videoMapper;

    @Override
    public void addVideo(Long contestId, String videoPath) {
        videoMapper.addVideo(contestId, videoPath);
    }

}
