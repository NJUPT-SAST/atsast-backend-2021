package com.sast.atSast.service;

import com.sast.atSast.model.JudgesResult;

import java.util.List;

public interface JudgesResultService {

    String addResult(JudgesResult judgesResult);

    JudgesResult getResult(Long teamId, Long contestId, Long judgeUid);

}
