package com.sast.atSast.service;

import com.sast.atSast.model.JudgesResult;

import java.util.List;

public interface JudgesResultService {

    void addResult(JudgesResult judgesResult);
    List<JudgesResult> getResults(Integer contestId);
}
