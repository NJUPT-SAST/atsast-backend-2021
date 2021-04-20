package com.sast.atSast.controller;

import com.sast.atSast.enums.CustomError;
import com.sast.atSast.exception.LocalRuntimeException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Date: 2021/4/20 12:13
 * @Description: 登陆相关的接口
 **/
@RestController
public class AccountController {
    @GetMapping("/hello")
    public String helloWorld(){
        return "hello, world";
    }

    @GetMapping("/exception")
    public String except(){
        throw new LocalRuntimeException(CustomError.REQUEST_ERROR);
    }
}
