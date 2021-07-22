package com.sast.atSast.mapper;

import com.sast.atSast.model.Richtext;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface RichtextMapper {
    void updateRichtext(@Param("contestId") long contestId,
                        @Param("stageId") long stageId,
                        @Param("content") String content);

    Richtext selectRichtext(@Param("contestId") long contestId,
                            @Param("stageId") long stageId);

}
