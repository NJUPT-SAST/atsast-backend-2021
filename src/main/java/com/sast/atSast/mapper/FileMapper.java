package com.sast.atSast.mapper;

import com.sast.atSast.model.File;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @program: ATSAST
 * @description: 学生上传文件mapper
 * @author: cxy621
 * @create: 2021-07-22 10:57
 **/
@Mapper
@Repository
public interface FileMapper {

    void updateFiles(File file);

    String getFileUrls(Long teamId);

}
