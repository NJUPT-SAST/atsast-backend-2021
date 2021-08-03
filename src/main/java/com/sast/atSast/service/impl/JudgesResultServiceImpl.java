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
    public List<JudgesResult> getResults(Long contestId){
        return judgesResultMapper.getResults(contestId);
    }

    @Override
    public JudgesResult getResult(Long teamId, Long contestId, Long judgeUid) {
        return judgesResultMapper.getResult(teamId,contestId,judgeUid);
    }

    @Override
    public Integer getScore(JudgesResult judgesResult) {
        return judgesResultMapper.getScore(judgesResult);
    }

    @Override
    public void updateResult(JudgesResult judgesResult) {
        judgesResultMapper.updateResult(judgesResult);
    }

}
