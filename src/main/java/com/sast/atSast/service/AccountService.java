package com.sast.atSast.service;

import com.sast.atSast.model.Account;

import java.util.List;

/**
 * @Date: 2021/4/20 13:43
 * @Description: 登陆相关逻辑接口
 **/
public interface AccountService {
    void login(String username,String password);
    void logout();
    void register(String username,String password, String role);
    Account findByEmail(String email);
    List<String> findPermsByEmail(String email);
}
