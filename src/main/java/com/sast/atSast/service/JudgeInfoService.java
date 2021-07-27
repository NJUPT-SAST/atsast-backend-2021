package com.sast.atSast.service;

import com.sast.atSast.model.JudgeInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: Acow337
 * @Date: 2021/07/24/20:21
 * @Description:
 */
public interface JudgeInfoService {
    void insertJudge(JudgeInfo judgeInfo);

    void deleteJudge(Long uid);

    List<JudgeInfo> listJudges();
}
