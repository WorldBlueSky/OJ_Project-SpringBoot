package com.example.demo.config;

import com.example.demo.interceptor.LoginInterceptor;
import com.example.demo.interceptor.ModeLoadInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    /**
     * 将自定义的拦截器加入到 mvc 框架中
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 将登录拦截注册到 配置中
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/index.html",
                        "/problemDetail.html",
                        "/problemInsert.html",
                        "/problemManage.html",
                        "/problemUpdate.html",
                        "/userManage.html",
                        "/userUpdate.html");// 必须加上/

        // 将用户身份权限拦截 注册到 配置中
        registry.addInterceptor(new ModeLoadInterceptor())
                .addPathPatterns("/userManage.html",
                        "/userUpdate.html",
                        "/problemManage.html",
                        "/problemInsert.html",
                        "/problemUpdate.html");// 对后台管理的模块的网页设置身份验证拦截

    }


}
