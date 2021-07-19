package com.sast.atSast.service;

import com.sast.atSast.model.FileStd;

/**
 * @program: ATSAST
 * @description: 建立文件阶段的业务层
 * @author: cxy621
 * @create: 2021-07-19 12:36
 **/
public interface FileStdService {

    void addFile(FileStd fileStd);

    FileStd getFileMessageById(long stageId, long contestId);

}
