package com.sast.atSast.controller;

import com.sast.atSast.enums.CustomError;
import com.sast.atSast.exception.LocalRuntimeException;
import com.sast.atSast.mapper.ContestMapper;
import com.sast.atSast.service.AccountService;
import com.sast.atSast.service.impl.ContestServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Date: 2021/4/20 12:13
 * @Description: 登陆相关的接口
 **/
@RestController
public class AccountController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private ContestServiceImpl contestService;

    @Autowired
    private ContestMapper contestMapper;

    @GetMapping("/exception")
    public String except(){
        throw new LocalRuntimeException(CustomError.REQUEST_ERROR);
    }

    @PostMapping("/user/login")
    public String login(String email, String password) {
        return accountService.login(email, password);
    }

    @PutMapping("/user/logout")
    public String logout() {
        accountService.logout();
        return "ok";
    }

    @PostMapping("/user/register")
    public String register(String email, String password, String role, String key) {
        accountService.register(email, password, role, key);
        return "ok";
    }

    //发送验证码邮件
    @PutMapping("/user/sendemail")
    public String sendVerificationCodeEmail() {
        accountService.sendVerificationCodeEmail();
        return "ok";
    }

    //判断验证码是否正确
    @PostMapping("/user/judgecode")
    public boolean judgeVerificationCode(String inputVerificationCode) {
        return accountService.judgeVerificationCode(inputVerificationCode);
    }

    //忘记密码（需要先通过验证）
    @PutMapping("/user/forgetpassword")
    public String forgetPassword(String password) {
        accountService.forgetPassword(password);
        return "ok";
    }

    //更新密码（根据旧密码修改）
    @PutMapping("/user/updatepassword")
    public String updatePassword(String oldPassword, String newPassword) {
        accountService.updatePassword(oldPassword, newPassword);
        return "ok";
    }
}
