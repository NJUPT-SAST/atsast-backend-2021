package com.sast.atSast.service.impl;

import com.sast.atSast.mapper.StageMapper;
import com.sast.atSast.model.Stage;
import com.sast.atSast.service.StageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @program: ATSAST
 * @description: 业务层实现
 * @author: cxy621
 * @create: 2021-07-17 22:17
 **/
@Service
public class StageServiceImpl implements StageService {

    @Autowired
    StageMapper stageMapper;

    @Override
    public void createStage(Stage stage) {
        stageMapper.createStage(stage);
    }

    @Override
    public List<Stage> getStagesById(Long contestId) {
        return stageMapper.getStagesById(contestId);
    }

}
