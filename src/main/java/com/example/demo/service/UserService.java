package com.example.demo.service;

import com.example.demo.exception.InsertException;
import com.example.demo.exception.PasswordNotMatchException;
import com.example.demo.exception.UsernameDuplicatedException;
import com.example.demo.exception.UsernameNotFoundException;
import com.example.demo.pojo.User;

public interface UserService {

    /**
     * 用户注册方法
     * @param user 用户的数据对象
     */

    void register(User user) throws UsernameDuplicatedException, InsertException; //register注册方法

    /**
     * 用户登陆功能
     * @param username 用户名
     * @param password 用户密码
     * @return 当前匹配的用户数据，如果没有则返回null值
     */

    User login(String username,String password) throws UsernameNotFoundException, PasswordNotMatchException;
}
