package com.sast.atSast.service.impl;

import com.sast.atSast.enums.CustomError;
import com.sast.atSast.exception.LocalRuntimeException;
import com.sast.atSast.mapper.AccountMapper;
import com.sast.atSast.model.Account;
import com.sast.atSast.service.AccountService;
import com.sast.atSast.service.EmailService;
import com.sast.atSast.service.RedisService;
import com.sast.atSast.util.SaltUtil;
import com.sast.atSast.util.VerificationCodeGenerator;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.ShiroException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.lang.model.element.VariableElement;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.sast.atSast.enums.CustomError.PERMISSION_DENY;

/**
 * @Date: 2021/4/20 13:44
 * @Description: 登陆相关逻辑的实现
 **/
@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    AccountMapper accountMapper;
    @Autowired
    EmailService emailService;
    @Autowired
    RedisService redisService;

    @Override
    public void login(String email, String password) {
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(new UsernamePasswordToken(email, password));
        } catch (UnknownAccountException e) {
            throw new LocalRuntimeException(CustomError.UNKNOWN_ACCOUNT);
        } catch (IncorrectCredentialsException e) {
            throw new LocalRuntimeException(CustomError.WRONG_PASSWORD);
        } catch(ShiroException e){
            throw new LocalRuntimeException(CustomError.PERMISSION_DENY);
        }
    }

    @Override
    public void logout() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
    }

    @Override
    public void register(String email, String password, String role) {
        String salt = SaltUtil.getSalt(8);
        Md5Hash md5Hash = new Md5Hash(password, salt, 1024);
        String md5Password = md5Hash.toHex();
        Account account = new Account(email, md5Password, role, salt);
        accountMapper.insertAccount(account);
    }

    @Override
    public Account findAccountByEmail(String email) {
        return accountMapper.selectAccountByEmail(email);
    }

    @Override
    public List<String> findRolesByEmail(String email) {
        return accountMapper.selectRolesByEmail(email);
    }

    @Override
    public void sendVerificationCodeEmail() {
        Subject subject = SecurityUtils.getSubject();
        String email = (String)subject.getPrincipal();
        String verificationCode = VerificationCodeGenerator.getVerificationCode(6);
        redisService.setToCache(email, verificationCode, 1, TimeUnit.HOURS);
        emailService.sendSimpleMail(email, verificationCode);
    }

    @Override
    public boolean judgeVerificationCode(String inputVerificationCode) {
        Subject subject = SecurityUtils.getSubject();
        String email = (String)subject.getPrincipal();
        String LocalVerificationCode = null;
        try {
            LocalVerificationCode = redisService.getFromCache(email);
            if (LocalVerificationCode.equals(inputVerificationCode)) {
                //设置值为"true"表示通过验证
                redisService.setToCache(email, "true", 1, TimeUnit.HOURS);
                return true;
            }
        } catch (Exception e) {
            throw new LocalRuntimeException(CustomError.VERIFICATION_CODE_NOT_SENT);
        }
        return false;
    }

    @Override
    public void forgetPassword(String password) {
        Subject subject = SecurityUtils.getSubject();
        String email = (String)subject.getPrincipal();
        //如果redis中对应value不为true或不存在，表示用户没有通过刚才的验证（防止用户直接通过URL修改）
        String fromCache = null;
        try {
            fromCache = redisService.getFromCache(email);
        } catch (Exception e) {
            throw new LocalRuntimeException(CustomError.FAILED_VERIFICATION);
        }
        if (fromCache.equals("true")) {
            String salt = SaltUtil.getSalt(8);
            Md5Hash md5Hash = new Md5Hash(password, salt, 1024);
            String md5Password = md5Hash.toHex();
            accountMapper.updatePasswordByEmail(email, md5Password,salt);
        } else {
            throw new LocalRuntimeException(CustomError.FAILED_VERIFICATION);
        }
    }

    @Override
    public void updatePassword(String oldPassword, String newPassword) {
        Subject subject = SecurityUtils.getSubject();
        String email = (String)subject.getPrincipal();
        try {
            subject.login(new UsernamePasswordToken(email, oldPassword));
            //验证通过则允许修改
            String salt = SaltUtil.getSalt(8);
            Md5Hash md5Hash = new Md5Hash(newPassword, salt, 1024);
            String md5Password = md5Hash.toHex();
            accountMapper.updatePasswordByEmail(email, md5Password,salt);
        } catch (AuthenticationException e) {
            throw new LocalRuntimeException(CustomError.WRONG_PASSWORD);
        }
    }
}
