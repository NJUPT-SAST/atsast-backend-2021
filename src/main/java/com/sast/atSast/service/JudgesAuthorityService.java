package com.sast.atSast.service;

import com.sast.atSast.model.JudgesAuthority;

import java.util.List;

public interface JudgesAuthorityService {

    void addAuthority(JudgesAuthority judgesAuthority);

    List<Long> getTeamIdsByUid(Long judgeUid);

}
