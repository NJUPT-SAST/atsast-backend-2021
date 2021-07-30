package com.sast.atSast.service;

import com.sast.atSast.model.JudgesResult;

import java.util.List;

public interface JudgesResultService {

    void addResult(JudgesResult judgesResult);

    JudgesResult getResult(long teamId, long contestId, long judgeUid);

    Integer getScore(JudgesResult judgesResult);

    void updateResult(JudgesResult judgesResult);

    List<JudgesResult> getResults(Long contestId);

}
