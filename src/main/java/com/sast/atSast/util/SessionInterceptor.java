package com.sast.atSast.util;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author punkginger
 * @description session拦截器判断是否登录
 */
public class SessionInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        String isLogin = (String)session.getAttribute("isLogin");   //这里需要负责登录的同学设置一下session
        if(isLogin==null){
            System.out.println("用户没有登录");
            response.sendRedirect("/login");
            return false;
        }
        System.out.println("用户已登录");
        return true;
    }
}
