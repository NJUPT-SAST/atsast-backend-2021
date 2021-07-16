package com.sast.atSast.service;

import com.sast.atSast.model.Contest;
import com.sast.atSast.model.Stage;


public interface ContestService {

    void createContest(Contest contest);

    void createStage(Stage stage);

    void updatepushLink(int contestId, String pushLink);
}
