package com.sast.atSast.mapper;

import com.sast.atSast.model.JudgesResult;
import com.sast.atSast.pojo.JudgeResultTemp;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author cxy621
 * @desription 评委评分，并且在返回上一个时可以得到对应的数库
 */
@Repository
public interface JudgesResultMapper {

    void addResult(JudgesResult judgesResult);

    JudgesResult getResult(long teamId, long contestId, long judgeUid);

}
