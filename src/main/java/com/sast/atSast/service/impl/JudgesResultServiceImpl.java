package com.sast.atSast.service.impl;

import com.sast.atSast.mapper.JudgesResultMapper;
import com.sast.atSast.model.JudgesResult;
import com.sast.atSast.service.JudgesResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JudgesResultServiceImpl implements JudgesResultService {

    @Autowired
    JudgesResultMapper judgesResultMapper;

    @Override
    public void addResult(JudgesResult judgesResult) {
        judgesResultMapper.addResult(judgesResult);
    }

    @Override
    public JudgesResult getResult(long teamId, long contestId, long judgeUid) {
        return judgesResultMapper.getResult(teamId, contestId, judgeUid);
    }

    @Override
    public List<JudgesResult> getResults(long contestId) {
        return judgesResultMapper.getResults(contestId);
    }

}
