package com.sast.atSast.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author: 風楪fy
 * @create: 2021-07-18 18:15
 **/
public interface EmailService {

    void sendSimpleMail(String to, String verificationCode);

}
