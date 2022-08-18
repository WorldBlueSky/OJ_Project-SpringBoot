package com.example.demo.controller;

import com.example.demo.exception.*;
import org.apache.ibatis.annotations.Insert;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileNotFoundException;
import java.util.HashMap;

@RestController
public class BaseController {

    // 用来处理各种异常
    @ExceptionHandler({ServiceException.class})
    public HashMap<String,Object> handlerException(Throwable e){

        HashMap<String ,Object> map = new HashMap<>();

        /**
         * 编译运行项目的异常都是以 2开头的
         * 注册的异常都是以 1开头的
         * 用户名登陆的异常都是以 3开头的
         * 手机验证码登陆的异常都是以 4开头的
         */

        if(e instanceof FileNotFoundException){
            map.put("status",2000);//规定返回2000是题目为找到的异常
            map.put("message","输入的id不合法,未找到题目!");
        }else if(e instanceof UserNotFoundException){
            map.put("status",2001);
            map.put("message","输入的id不合法，找不到该用户!");
        }else if(e instanceof InsertException){
            map.put("status",1002);
            map.put("message","注册时服务器异常，注册失败!");
        }else if(e instanceof PasswordNotMatchException){
            map.put("status",3001);
            map.put("message","密码与用户名不匹配，请重新输入!");
        }else if(e instanceof UsernameDuplicatedException){
            map.put("status",1004);
            map.put("message","用户名已被占用，请重新输入!");
        }else if(e instanceof UsernameNotFoundException){
            map.put("status",3002);
            map.put("message","输入的用户名不存在，请重新输入!");
        }

        return map;
    }

}
