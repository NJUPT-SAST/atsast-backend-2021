package com.sast.atSast.service.impl;

import com.sast.atSast.mapper.JudgesAuthorityMapper;
import com.sast.atSast.model.JudgesAuthority;
import com.sast.atSast.service.JudgesAuthorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JudgesAuthorityServiceImpl implements JudgesAuthorityService {

    @Autowired
    JudgesAuthorityMapper judgesAuthorityMapper;

    @Override
    public void addAuthority(JudgesAuthority judgesAuthority) {
        judgesAuthorityMapper.addAuthority(judgesAuthority);
    }

    @Override
    public List<Long> getTeamIdsByUid(Long judgeUid){
        return judgesAuthorityMapper.getTeamIdsByUid(judgeUid);
    }

    @Override
    public void updateStageAfterAuthority(Long uid, Integer judgeTotal) {
        judgesAuthorityMapper.updateStageAfterAuthority(uid, judgeTotal);
    }

}
