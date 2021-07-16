package com.sast.atSast.service;

import com.sast.atSast.model.Contest;
import com.sast.atSast.model.Stage;

import java.util.List;

public interface ContestService {
    void createContest(Contest contest);
    void createStage(Stage stage);
}
