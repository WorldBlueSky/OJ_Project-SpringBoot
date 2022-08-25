package com.example.demo.mapper;

import com.example.demo.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Value;

import java.util.HashMap;
import java.util.List;

@Mapper
public interface UserMapper {

    int insert(User user) ;

    int delete(@Param("id") int id);

    List<User> selectAll();

    User selectById(int id);

    User selectByName(String username);

    User selectByNameAndId(String id,String username);

    // 用户名模糊查询
    List<User> selectByLikeName(String likeName);

    User selectByEmail(String email);

    int updateUser(@Param("id") int id,@Param("username") String username,@Param("email") String email);

    int updatePassword(@Param("username") String username, @Param("finalPassword") String finalPassword);
}
