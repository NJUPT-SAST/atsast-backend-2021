package com.sast.atSast.service.impl;

import com.sast.atSast.enums.CustomError;
import com.sast.atSast.exception.LocalRuntimeException;
import com.sast.atSast.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * @author: 風楪fy
 * @create: 2021-07-18 18:15
 **/
@Service
public class EmailServiceImpl implements EmailService {
    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.from}")
    private String from;

    @Override
    public void sendSimpleMail(String to, String verificationCode) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(to);
        message.setSubject("校科协科协ATSAST平台");
        message.setText("尊敬的ATSAST平台用户:" + "\n\n" +
                "您好！" + "\n\n" +
                "您的验证码为:" + verificationCode + "。" + "\n" +
                "有效时间为1小时");
        try {
            mailSender.send(message);
        } catch (Exception e) {
            throw new LocalRuntimeException(CustomError.EMAIL_SENDING_ABNORMAL);
        }

    }
}
