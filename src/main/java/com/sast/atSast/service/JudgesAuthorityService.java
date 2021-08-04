package com.sast.atSast.service;

import com.sast.atSast.model.JudgesAuthority;
import com.sast.atSast.pojo.JudgeResultTemp;

import java.util.List;

public interface JudgesAuthorityService {

    void addAuthority(JudgesAuthority judgesAuthority);

    JudgeResultTemp getLastResult(Long teamId, Long contestId, Long judgeUid);

    String JudgeAuthority(Long contestId, Long judgeUid);

}
