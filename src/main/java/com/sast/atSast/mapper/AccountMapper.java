package com.sast.atSast.mapper;

import com.sast.atSast.model.Account;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Date: 2021/4/20 13:48
 * @Description: 账号相关，account表、student_info表
 **/
@Repository
public interface AccountMapper {
    void insertAccount(Account account);

    Account selectAccountByEmail(@Param("email") String email);

    List<String> selectRolesByEmail(@Param("email") String email);

    void updatePasswordByEmail(@Param("email") String email, @Param("password") String password, @Param("salt") String salt);

    String findPasswordByEmail(@Param("email") String email);

    void importAccount(@Param("account") Account account);

    List<String> listEmail();
}
