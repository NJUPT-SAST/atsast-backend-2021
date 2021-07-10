package com.sast.atSast.service;

import com.sast.atSast.model.Contest;
import com.sast.atSast.model.ContestVO;

import java.util.List;

/**
 * @author: 風楪fy
 * @create: 2021-07-08 15:53
 **/
public interface ContestService {
    void contestCreate(Contest contest);

    void uploadInfo(int contestId, List<String> picUrls, List<String> videoUrls, String pushLink);

    ContestVO queryNum(int contestId);

    List<String> queryAllFiles(int contestId);
}
