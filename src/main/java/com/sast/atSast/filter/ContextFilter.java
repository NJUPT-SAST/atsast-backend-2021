package com.sast.atSast.filter;


import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * @author Zero
 * @date 2020/5/1 0:57
 * @description: 处理跨域问题
 */
@Component
public class ContextFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {

        HttpServletResponse res = (HttpServletResponse) response;
        HttpServletRequest req = (HttpServletRequest) request;
        String origin = req.getHeader("Origin");
        if (origin != null){
            res.setHeader("Access-Control-Allow-Origin", origin);
            res.setHeader("Access-Control-Allow-Methods", "POST, PUT, GET, OPTIONS, DELETE");
            res.setHeader("Access-Control-Allow-Credentials", "true");
            res.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
            res.setHeader("Access-Control-Max-Age", "3600");
            if (Objects.equals(req.getMethod(), "OPTIONS")) {
                return;
            }

        }
        filterChain.doFilter(request,response);
    }

    @Override
    public void destroy() {

    }
}
