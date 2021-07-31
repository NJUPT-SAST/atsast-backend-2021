package com.sast.atSast.service.impl;

import com.sast.atSast.enums.CustomError;
import com.sast.atSast.exception.LocalRuntimeException;
import com.sast.atSast.mapper.AccountMapper;
import com.sast.atSast.model.Account;
import com.sast.atSast.service.AccountService;
import com.sast.atSast.service.EmailService;
import com.sast.atSast.service.RedisService;
import com.sast.atSast.util.MultipartFileToFile;
import com.sast.atSast.util.SaltUtil;
import com.sast.atSast.util.VerificationCodeGenerator;
import org.apache.ibatis.annotations.Param;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

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

    String check = "^\\s*\\w+(?:\\.{0,1}[\\w-]+)*@[a-zA-Z0-9]+(?:[-.][a-zA-Z0-9]+)*\\.[a-zA-Z]+\\s*$";
    Pattern emailRegex = Pattern.compile(check);

    String passwordCheck = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,16}$";
    Pattern pwdRegex = Pattern.compile(passwordCheck);

    @Override
    public void login(String email, String password) {
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(new UsernamePasswordToken(email, password));
        } catch (UnknownAccountException e) {
            throw new LocalRuntimeException(CustomError.UNKNOWN_ACCOUNT);
        } catch (IncorrectCredentialsException e) {
            throw new LocalRuntimeException(CustomError.WRONG_PASSWORD);
        } catch (ShiroException e) {
            throw new LocalRuntimeException(CustomError.PERMISSION_DENY);
        }
    }

    @Override
    public void logout() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
    }

    @Override
    public void register(String email, String password, String role, String key) {

        String salt = SaltUtil.getSalt(8);
        Md5Hash md5Hash = new Md5Hash(password, salt, 1024);
        String md5Password = md5Hash.toHex();
        Account account = new Account(email, md5Password, role, salt);
        if(role.equals("admin")){
            if (redisService.getFromCache("key").equals(key)) {
                accountMapper.insertAccount(account);
            }else {
                throw new LocalRuntimeException(CustomError.PERMISSION_DENY);
            }
        }else {
            accountMapper.insertAccount(account);
        }
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
        String email = (String) subject.getPrincipal();
        String verificationCode = VerificationCodeGenerator.getVerificationCode(6);
        redisService.setToCache(email, verificationCode, 1, TimeUnit.HOURS);
        emailService.sendSimpleMail(email, verificationCode);
    }

    @Override
    public boolean judgeVerificationCode(String inputVerificationCode) {
        Subject subject = SecurityUtils.getSubject();
        String email = (String) subject.getPrincipal();
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
        String email = (String) subject.getPrincipal();
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
            accountMapper.updatePasswordByEmail(email, md5Password, salt);
        } else {
            throw new LocalRuntimeException(CustomError.FAILED_VERIFICATION);
        }
    }

    @Override
    public void updatePassword(String oldPassword, String newPassword) {
        Subject subject = SecurityUtils.getSubject();
        String email = (String) subject.getPrincipal();
        try {
            subject.login(new UsernamePasswordToken(email, oldPassword));
            //验证通过则允许修改
            String salt = SaltUtil.getSalt(8);
            Md5Hash md5Hash = new Md5Hash(newPassword, salt, 1024);
            String md5Password = md5Hash.toHex();
            accountMapper.updatePasswordByEmail(email, md5Password, salt);
        } catch (AuthenticationException e) {
            throw new LocalRuntimeException(CustomError.WRONG_PASSWORD);
        }
    }

    public void importAccount(Account account) {
        accountMapper.insertAccount(account);
    }

    public void readAccountExcel(MultipartFile file) throws IOException {
        List<String> emails = new LinkedList<String>();
        List<String> tempEmails = new LinkedList<String>();
        emails = accountMapper.listEmail();

        MultipartFileToFile multipartFileToFile = new MultipartFileToFile();
        List<Account> accounts = new LinkedList<Account>();

        Workbook workbook = null;
        try {
            InputStream inputStream = file.getInputStream();
            inputStream = new FileInputStream(multipartFileToFile.convert(file));
            workbook = new XSSFWorkbook(inputStream);
        } catch (Exception e) {
            CustomError.EXCEL_ERROR.setErrMsg("上传文件格式有误（文件后缀应为.xlsx）");
            throw new LocalRuntimeException(CustomError.EXCEL_ERROR);
        }

        Sheet sheet = workbook.getSheetAt(0);

        Row rowTitle = sheet.getRow(0);

        int rowCount = sheet.getPhysicalNumberOfRows();
        for (int rowNum = 1; rowNum < rowCount; rowNum++) {
            Row rowData = sheet.getRow(rowNum);
            if (rowData != null) {
                int cellCount = rowTitle.getPhysicalNumberOfCells();
                Account account = new Account();
                for (int cellNum = 0; cellNum < cellCount; cellNum++) {
                    account.setUid(Long.parseLong("0"));
                    account.setEnable((byte) 1);

                    Cell cell = null;
                    try {
                        cell = rowData.getCell(cellNum);
                        CellType cellType = cell.getCellTypeEnum();
                    } catch (NullPointerException e) {
                        CustomError.EXCEL_ERROR.setErrMsg("Excel缺失数据");
                        throw new LocalRuntimeException(CustomError.EXCEL_ERROR);
                    }

                    String cellValue;
                    if (cell.getCellTypeEnum() == CellType.STRING) {
                        cellValue = cell.getStringCellValue();
                    } else if (cell.getCellTypeEnum() == CellType.NUMERIC) {
                        cellValue = String.valueOf(cell.getNumericCellValue());
                    } else {
                        CustomError.EXCEL_ERROR.setErrMsg("Excel内存放数据类型有误");
                        throw new LocalRuntimeException(CustomError.EXCEL_ERROR);
                    }

                    switch (cellNum) {
                        case 0:
                            if (emails.contains(cellValue) | tempEmails.contains(cellValue)) {
                                CustomError.EXCEL_ERROR.setErrMsg("第" + rowNum + "行邮箱已存在");
                                throw new LocalRuntimeException(CustomError.EXCEL_ERROR);
                            } else if (!emailRegex.matcher(cellValue).matches()) {
                                CustomError.EXCEL_ERROR.setErrMsg("第" + rowNum + "行邮箱格式错误");
                                throw new LocalRuntimeException(CustomError.EXCEL_ERROR);
                            } else {
                                account.setEmail(cellValue);
                            }
                            break;
                        case 1:
                            if (!pwdRegex.matcher(cellValue).matches()) {
                                CustomError.EXCEL_ERROR.setErrMsg("第" + rowNum + "行密码格式错误");
                                throw new LocalRuntimeException(CustomError.EXCEL_ERROR);
                            } else {
                                account.setPassword(cellValue);
                            }
                            break;
                        case 2:
                            if (cellValue.equals("学生") | cellValue.equals("评委")
                                    | cellValue.equals("管理员") | cellValue.equals("超级管理员")) {
                                account.setRole(cellValue);
                            } else {
                                CustomError.EXCEL_ERROR.setErrMsg("第" + rowNum + "行账号身份错误");
                                throw new LocalRuntimeException(CustomError.EXCEL_ERROR);
                            }
                            break;
                        default:
                            CustomError.EXCEL_ERROR.setErrMsg("Excel内容错误");
                            throw new LocalRuntimeException(CustomError.EXCEL_ERROR);
                    }
                    tempEmails.add(account.getEmail());
                }
                account.setRole("管理员");
                accounts.add(account);
            }
        }
        tempEmails.clear();
        for (Account item : accounts) {
            String salt = SaltUtil.getSalt(8);
            Md5Hash md5Hash = new Md5Hash(item.getPassword(), salt, 1024);
            String md5Password = md5Hash.toHex();
            item.setSalt(salt);
            item.setPassword(md5Password);
            accountMapper.importAccount(item);
        }
    }

    public List<String> listEmail() {
        return accountMapper.listEmail();
    }

    public void deleteAccountByUid(Long uid){
        accountMapper.deleteAccountByUid(uid);
    }



}
