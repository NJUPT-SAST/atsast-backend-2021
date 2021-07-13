package com.sast.atSast.service;

import com.sast.atSast.model.JudgeVO;
import org.springframework.stereotype.Service;

/**
 * @author: 風楪fy
 * @create: 2021-07-10 20:13
 **/
public interface JudgeService {
    JudgeVO queryJudgeInfo(int teamId);
}
