package com.sast.atSast.service.impl;

import com.sast.atSast.mapper.JudgeAuthorityMapper;
import com.sast.atSast.service.JudgesAuthorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JudgesAuthorityServiceImpl implements JudgesAuthorityService {

    @Autowired
    JudgeAuthorityMapper judgeAuthorityMapper;

    @Override
    public void addAuthority(long judgeId, long teamId) {
        judgeAuthorityMapper.addAuthority(judgeId, teamId);
    }
}
