package com.sast.atSast.mapper;

import com.sast.atSast.model.JudgeInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

/**
 * @program: ATSAST
 * @description: 对于评委具体信息的mapper层
 * @author: cxy621
 * @create: 2021-07-24 20:38
 **/
@Repository
public interface JudgeInfoMapper {
    List<JudgeInfo> getJudgeInfo(Long contestId);

    void addSingleJudge(JudgeInfo judgeInfo);

    Long getUidByEmail(String email);

    void deleteJudge(Long uid);

    void addJudgeCurr(Long uid);

    JudgeInfo getJudgeInfoById(Long uid);

    void updateJudgeStage(Long uid);

    void insertJudge(@Param("judgeInfo") JudgeInfo judgeInfo);

    void deleteJudge(Long uid);

    List<JudgeInfo> listJudges();

}
