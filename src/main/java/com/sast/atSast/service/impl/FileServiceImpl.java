package com.sast.atSast.service.impl;

import com.sast.atSast.mapper.FileMapper;
import com.sast.atSast.model.File;
import com.sast.atSast.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @program: ATSAST
 * @description: 上传比赛文件业务逻辑实现层
 * @author: cxy621
 * @create: 2021-07-22 11:03
 **/
@Service
public class FileServiceImpl implements FileService {

    @Autowired
    FileMapper fileMapper;

    @Override
    public void updateFiles(File file) {
        fileMapper.updateFiles(file);
    }

    @Override
    public String getFileUrls(long teamId) {
        return fileMapper.getFileUrls(teamId);
    }

}
