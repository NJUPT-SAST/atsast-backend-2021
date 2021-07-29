package com.sast.atSast.mapper;

import com.sast.atSast.model.Richtext;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface RichtextMapper {
    void updateRichtext(@Param("contestId") Long contestId,
                        @Param("stageId") Long stageId,
                        @Param("content") String content);

    Richtext selectRichtext(@Param("contestId") Long contestId,
                            @Param("stageId") Long stageId);

}
