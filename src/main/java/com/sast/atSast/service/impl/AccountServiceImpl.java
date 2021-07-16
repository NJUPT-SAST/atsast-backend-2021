package com.sast.atSast.service.impl;

import com.sast.atSast.mapper.AccountMapper;
import com.sast.atSast.model.Account;
import com.sast.atSast.service.AccountService;
import com.sast.atSast.util.SaltUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Date: 2021/4/20 13:44
 * @Description: 登陆相关逻辑的实现
 **/
@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    AccountMapper accountMapper;

    @Override
    public void login(String username, String password) {
        Subject subject = SecurityUtils.getSubject();
        //todo 因为还没看全局异常处理，所以异常处理先放在这，后期再改
        try {
            subject.login(new UsernamePasswordToken(username, password));
        } catch (UnknownAccountException e) {
            e.printStackTrace();
            System.out.println("用户名错误");
        } catch (IncorrectCredentialsException e) {
            e.printStackTrace();
            System.out.println("密码错误");
        }
    }

    @Override
    public void logout() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
    }

    @Override
    public void register(String username, String password, Byte type) {
        String salt = SaltUtil.getSalt(8);
        Md5Hash md5Hash = new Md5Hash(password, "salt", 1024);
        String md5Password = md5Hash.toHex();
        Account account = new Account(username, md5Password, type, salt);
        accountMapper.insertAccount(account);
    }

    @Override
    public Account findByEmail(String email) {
        return accountMapper.selectByEmail(email);
    }
}
