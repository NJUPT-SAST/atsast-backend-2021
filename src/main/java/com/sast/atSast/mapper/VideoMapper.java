package com.sast.atSast.mapper;

import com.sast.atSast.model.Video;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface VideoMapper {
    void getVideo(long contestId, String videoUrl);
}
