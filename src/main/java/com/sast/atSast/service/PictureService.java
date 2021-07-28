package com.sast.atSast.service;

/**
 * @program: ATSAST
 * @description: 提交照片业务层
 * @author: cxy621
 * @create: 2021-07-18 11:04
 **/
public interface PictureService {

    void addPictures(long contestId, String picPath);

    String getUrlsById(Long contestId);

}
