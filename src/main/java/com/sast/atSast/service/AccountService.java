package com.sast.atSast.service;

import com.sast.atSast.model.Account;

/**
 * @Date: 2021/4/20 13:43
 * @Description: 登陆相关逻辑接口
 **/
public interface AccountService {
    void login(String username,String password);
    void logout();
    void register(String username,String password, Byte type);
    Account findByEmail(String email);
}
