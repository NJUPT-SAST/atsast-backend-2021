package com.sast.atSast.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface VideoMapper {

    void addVideo(long contestId, String videoPath);

}
