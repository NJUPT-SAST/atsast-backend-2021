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

    public List<JudgesResult> getResults(Integer contestId){
        return judgesResultMapper.getResults(contestId);
    }

}
