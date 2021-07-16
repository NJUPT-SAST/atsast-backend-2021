package com.sast.atSast.controller;

import com.sast.atSast.enums.CustomError;
import com.sast.atSast.exception.LocalRuntimeException;
import com.sast.atSast.mapper.ContestMapper;
import com.sast.atSast.model.Contest;
import com.sast.atSast.service.AccountService;
import com.sast.atSast.service.impl.AccountServiceImpl;
import com.sast.atSast.service.impl.ContestServiceImpl;
import com.sast.atSast.shiro.realm.AccountRealm;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Date: 2021/4/20 12:13
 * @Description: 登陆相关的接口
 **/
@RestController
public class AccountController {

    @Autowired
    private AccountService accountService;

    @PostMapping("/user/login")
    public void login(String username, String password){
        accountService.login(username,password);
    }

    @PutMapping("/user/logout")
    public void logout(){
        accountService.logout();
    }

    @PostMapping("/user/register")
    public void register(String username,String password, String type){
        accountService.register(username,password,type);
    }
}
