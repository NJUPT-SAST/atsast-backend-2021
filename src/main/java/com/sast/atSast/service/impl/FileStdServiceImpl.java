package com.sast.atSast.service.impl;

import com.sast.atSast.mapper.ContestMapper;
import com.sast.atSast.model.FileStd;
import com.sast.atSast.service.FileStdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @program: ATSAST
 * @description: 建立文件阶段的业务实现层
 * @author: cxy621
 * @create: 2021-07-19 12:37
 **/
@Service
public class FileStdServiceImpl implements FileStdService {

    @Autowired
    ContestMapper contestMapper;

    @Override
    public void addFile(FileStd fileStd) {
        contestMapper.addFile(fileStd);
    }

    @Override
    public FileStd getFileMessageById(long stageId, long contestId) {
        return contestMapper.getFileMessageById(stageId, contestId);
    }

}
