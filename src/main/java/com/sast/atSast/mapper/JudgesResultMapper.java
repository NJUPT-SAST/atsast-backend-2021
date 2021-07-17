package com.sast.atSast.mapper;

import org.apache.ibatis.annotations.Mapper;

/**
 * @author cxy621
 * @desription 评委评分，并且在返回上一个时可以得到对应的数库
 */
@Mapper
public interface JudgesResultMapper {

    void addResult(String comment, int scores, long teamId, long contestId ,long judgeUid);
}
