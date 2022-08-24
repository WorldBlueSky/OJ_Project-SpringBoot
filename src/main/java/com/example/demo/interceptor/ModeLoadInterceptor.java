package com.example.demo.interceptor;

import com.example.demo.pojo.User;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 这里的定义的拦截器是为了  拦截访问后台管理模块网站的 用户
 */

public class ModeLoadInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        HttpSession session = request.getSession(false);
//        if(session==null){
//            response.sendRedirect("login.html");
//            return false;
//        }
//
//        User user = (User)session.getAttribute("user");
//        if(user==null || user.getIsAdmin()==0){
//            response.sendRedirect("index.html");
//            return false;
//        }
//
        return true;
    }
}
