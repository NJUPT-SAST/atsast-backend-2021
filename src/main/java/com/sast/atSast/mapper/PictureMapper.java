package com.sast.atSast.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @program: ATSAST
 * @description: 提交照片
 * @author: cxy621
 * @create: 2021-07-18 10:46
 **/
@Repository
public interface PictureMapper {

    void addPictures(Long contestId, String picPath);

    String getUrlsById(Long contestId);

}
