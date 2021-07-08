package com.sast.atSast.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author punkginger
 * @Date: 2021/7/7
 * @Description: 消息相关controller
 **/
@RestController
public class MessageController {
    /**
     * 学生端查看接收到的比赛邀请信息
     * 检查是否登录，若未登录重定向至登录界面
     * 若无邀请则弹窗显示，并重定向返回首页
     * 进入一次对应路由查看一次当前接收信息
     */
    @GetMapping("/message")
    public String checkMsg(){
        return "/message";
    }

    /**
     * 学生端查看信息细节
     * 比赛邀请信息包括比赛id、比赛名称和比赛简介（富文本）
     */
    @GetMapping("/message/details")
    public String msgDetails(){
        return "/message/details";
    }

    /**
     * 管理员端发送信息
     * 发送的信息包括比赛邀请信息和学生id
     */
    @PostMapping("/postMsg")
    public String postMsg(){
        return "/postMsg";
    }
}
