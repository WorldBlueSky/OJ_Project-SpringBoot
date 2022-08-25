package com.example.demo;

import com.example.demo.service.UserService;
import com.example.demo.service.impl.UserServiceImpl;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class GetPassWord {



    @Test
    public void test(){
        String password = "123456";
        String salt ="abcdefg";
        String finalPassword = UserServiceImpl.getMd5Password(password,salt);
        System.out.println("原密码为："+password);
        System.out.println("盐值为： "+salt);
        System.out.println("数据库保存密码为："+finalPassword);
    }
}
