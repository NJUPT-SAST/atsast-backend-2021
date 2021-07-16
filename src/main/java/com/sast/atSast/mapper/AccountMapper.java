package com.sast.atSast.mapper;

import com.sast.atSast.model.Account;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @Date: 2021/4/20 13:48
 * @Description: 账号相关，account表、student_info表
 **/
@Repository
@Mapper
public interface AccountMapper {
    void insertAccount(Account account);
    Account selectByEmail(@Param("email") String email);
}
