package com.sast.atSast.service;

import com.sast.atSast.model.JudgeInfo;

import java.util.List;

/**
 * @program: ATSAST
 * @description: 评委具体信息的业务层
 * @author: cxy621
 * @create: 2021-07-24 20:49
 **/
public interface JudgeInfoService {

    List<JudgeInfo> getJudgeInfo(long contestId);

    void addSingleJudge(JudgeInfo judgeInfo);

    Long getUidByEmail(String email);

}
