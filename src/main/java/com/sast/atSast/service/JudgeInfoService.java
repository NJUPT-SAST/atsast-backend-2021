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
    void insertJudge(JudgeInfo judgeInfo);

    void deleteJudge(Long uid);

    List<JudgeInfo> listJudges();

    List<JudgeInfo> getJudgeInfo(Long contestId);

    void addSingleJudge(JudgeInfo judgeInfo);

    Long getUidByEmail(String email);
}
