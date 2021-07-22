package com.sast.atSast.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
public interface VideoMapper {

    void addVideo(long contestId, String videoPath);

}
