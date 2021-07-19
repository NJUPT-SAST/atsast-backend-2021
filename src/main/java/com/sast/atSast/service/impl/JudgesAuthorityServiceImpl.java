package com.sast.atSast.service.impl;

import com.sast.atSast.mapper.JudgesAuthorityMapper;
import com.sast.atSast.model.JudgesAuthority;
import com.sast.atSast.service.JudgesAuthorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JudgesAuthorityServiceImpl implements JudgesAuthorityService {

    @Autowired
    JudgesAuthorityMapper judgesAuthorityMapper;

    @Override
    public void addAuthority(JudgesAuthority judgesAuthority) {
        judgesAuthorityMapper.addAuthority(judgesAuthority);
    }
}
