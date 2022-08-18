package com.example.demo.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginInterceptor implements HandlerInterceptor {

    /**
     *
     * @param request 请求
     * @param response 响应
     * @param handler 处理器
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // 获取session对象
        HttpSession session = request.getSession(false);
        //System.out.println("session:"+session);

        // session在用户名登陆的时候设置了 "user"->user
        //        在手机号验证码登陆的时候设置了 "phone"->phone
        if(session==null || (session.getAttribute("user")==null && session.getAttribute("phone")==null)){
            // 上面的逻辑一定要搞清楚，user 和 phone 的信息，session中有一个即可
            //System.out.println("进入拦截器，重定向到登陆界面!");
            response.sendRedirect("login.html");
            return false;// 禁止通行
        }
        // 放行
        return true;
    }


}
