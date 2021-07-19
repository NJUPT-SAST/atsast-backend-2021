package com.sast.atSast.filter;

import com.sast.atSast.util.HttpResponse;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * @author: 風楪fy
 * @create: 2021-07-19 00:54
 * 自定义shiro的authc拦截器
 **/
public class ShiroFormAuthenticationFilter extends FormAuthenticationFilter {
    //为了让shiro拦截器默认的重定向方案改为返回json方案，需要重写其中的onAccessDenied方法
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletResponse resp = (HttpServletResponse) response;
        resp.setContentType("application/json; charset=utf-8");
        PrintWriter out = resp.getWriter();
        HttpResponse httpResponse= HttpResponse.failure("未登录",4011);
        out.write(httpResponse.toString());
        out.flush();
        out.close();
        return false;
    }
}
