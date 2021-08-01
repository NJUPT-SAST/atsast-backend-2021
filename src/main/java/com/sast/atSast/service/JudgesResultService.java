package com.sast.atSast.service;

import com.sast.atSast.model.JudgesResult;

import java.util.List;

public interface JudgesResultService {

    void addResult(JudgesResult judgesResult);

    JudgesResult getResult(Long teamId, Long contestId, Long judgeUid);

    List<JudgesResult> getResults(Long contestId);

    Integer getScore(JudgesResult judgesResult);

    void updateResult(JudgesResult judgesResult);

}
