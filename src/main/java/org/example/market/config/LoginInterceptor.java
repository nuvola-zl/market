package org.example.market.config;

import org.example.market.pojo.User;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * 登录拦截器：没登录不许进首页/后台
 */
public class LoginInterceptor implements HandlerInterceptor {

    // 接口放行前拦截
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        // 1. 从Session拿当前登录用户
        User loginUser = (User) request.getSession().getAttribute("loginUser");

        // 2. 判断：没登录 → 强制跳转到登录页
        if (loginUser == null) {
            response.sendRedirect("/login");
            return false; // 拦截，不放行
        }

        // 3. 已登录 → 正常访问
        return true;
    }
}