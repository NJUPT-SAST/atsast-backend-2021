package com.sast.atSast.service;

import com.sast.atSast.model.Account;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * @Date: 2021/4/20 13:43
 * @Description: 登陆相关逻辑接口
 **/
public interface AccountService {
    void login(String username, String password);

    void logout();

    void deleteAccountByUid(Long uid);

    void register(String username, String password, String role);
    
    void register(String username, String password, String role, String key);

    Account findAccountByEmail(String email);

    List<String> findRolesByEmail(String email);

    void sendVerificationCodeEmail();

    boolean judgeVerificationCode(String inputVerificationCode);

    void forgetPassword(String password);

    void updatePassword(String oldPassword, String newPassword);

    void importAccount(Account account);

    void readAccountExcel(MultipartFile file) throws IOException;

    List<String> listEmail();
}
