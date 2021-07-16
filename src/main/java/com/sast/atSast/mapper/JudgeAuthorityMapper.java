package com.sast.atSast.mapper;

import org.apache.ibatis.annotations.Mapper;

/**
 * @author cxy621
 * @desription 授权评委能够评价哪些队伍、
 * @date 2021/7/16 20:15:03
 */

@Mapper
public interface JudgeAuthorityMapper {

    void addAuthority(long judgeId, long teamId);

}
