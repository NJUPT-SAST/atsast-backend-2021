package com.sast.atSast.mapper;

import com.sast.atSast.model.JudgesAuthority;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author cxy621
 * @desription 授权评委能够评价哪些队伍,以数组的形式传递过来
 * @date 2021/7/16 20:15:03
 */
@Repository
@Mapper
public interface JudgesAuthorityMapper {

    void addAuthority(JudgesAuthority judgesAuthority);

}
