package com.sast.atSast.service;

import com.sast.atSast.model.JudgesAuthority;

public interface JudgesAuthorityService {

    void addAuthority(JudgesAuthority judgesAuthority);

    void updateStageAfterAuthority(Long uid, Integer judgeTotal);

}
