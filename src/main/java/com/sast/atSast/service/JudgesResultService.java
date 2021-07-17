package com.sast.atSast.service;

public interface JudgesResultService {

    void addResult(String comment, int scores, long teamId, long contestId ,long judgeUid);
}
