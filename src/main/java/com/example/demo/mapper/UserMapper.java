package com.example.demo.mapper;

import com.example.demo.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

@Mapper
public interface UserMapper {

    int insert(User user) ;

    int delete(@Param("id") int id);

    List<User> selectAll();

    User selectById(int id);

    User selectByName(String username);
}
