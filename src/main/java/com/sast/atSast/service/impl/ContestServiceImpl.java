package com.sast.atSast.service.impl;

import com.sast.atSast.mapper.ContestMapper;
import com.sast.atSast.model.Contest;
import com.sast.atSast.model.Stage;
import com.sast.atSast.service.ContestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContestServiceImpl implements ContestService {
    @Autowired
    private ContestMapper contestMapper;

    public void createContest(Contest contest){
        contestMapper.createContest(contest);
    }

    public void createStage(Stage stage){
        contestMapper.createStage(stage);
    }
}


