package com.sast.atSast.filter;

import com.sast.atSast.util.HttpResponse;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.RolesAuthorizationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author: 風楪fy
 * @create: 2021-07-19 02:26
 * 自定义shiro的roles拦截器
 **/
public class ShiroRolesAuthorizationFilter extends RolesAuthorizationFilter {
    //为了让shiro拦截器默认的重定向方案改为返回json方案，需要重写其中的onAccessDenied方法
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws IOException {
        HttpServletResponse resp = (HttpServletResponse) response;
        resp.setContentType("application/json; charset=utf-8");
        PrintWriter out = resp.getWriter();
        Subject subject = getSubject(request, response);
        HttpResponse httpResponse = null;
        // 没有认证,先返回未认证的json
        if (subject.getPrincipal() == null) {
            httpResponse = HttpResponse.failure("未登录", 4011);
        } else {
            // 已认证但没有角色，返回为授权的json
            httpResponse = HttpResponse.failure("权限错误", 4003);
        }
        out.write(httpResponse.toString());
        out.flush();
        out.close();
        return false;
    }
}
