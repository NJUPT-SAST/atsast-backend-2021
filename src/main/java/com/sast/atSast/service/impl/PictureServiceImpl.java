package com.sast.atSast.service.impl;

import com.sast.atSast.mapper.PictureMapper;
import com.sast.atSast.service.PictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @program: ATSAST
 * @description: 提交照片业务实现层
 * @author: cxy621
 * @create: 2021-07-18 11:40
 **/
@Service
public class PictureServiceImpl implements PictureService {

    @Autowired
    PictureMapper pictureMapper;

    @Override
    public void addPictures(long contestId, String picPath) {
        pictureMapper.addPictures(contestId, picPath);
    }

    @Override
    public String getUrlsById(Long contestId) {
        return pictureMapper.getUrlsById(contestId);
    }

}
