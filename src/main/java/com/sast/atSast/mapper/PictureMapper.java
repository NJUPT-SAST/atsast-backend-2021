package com.sast.atSast.mapper;

import org.apache.ibatis.annotations.Mapper;

/**
 * @program: ATSAST
 * @description: 提交照片
 * @author: cxy621
 * @create: 2021-07-18 10:46
 **/
@Mapper
public interface PictureMapper {

    void addPictures(long contestId, String picPath);

}
