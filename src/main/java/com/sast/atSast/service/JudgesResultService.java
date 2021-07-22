package com.sast.atSast.service;

import com.sast.atSast.model.JudgesResult;

public interface JudgesResultService {

    void addResult(JudgesResult judgesResult);

    JudgesResult getResult(long teamId, long contestId, long judgeUid);

}
