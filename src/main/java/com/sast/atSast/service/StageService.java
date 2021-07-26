package com.sast.atSast.service;

import com.sast.atSast.model.Stage;

/**
 * @program: ATSAST
 * @description: stage业务层
 * @author: cxy621
 * @create: 2021-07-17 22:16
 **/
public interface StageService {

    void createStage(Stage stage);

    Stage findByContestId(long contestId);

}
