package com.example.demo.mapper;

import com.example.demo.pojo.Problem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ProblemMapper {
    // 实现增删改查

    //1、增加题目
     int insert(Problem problem);

    //2、根据id删除题目
     int delete(@Param("id") int id);

    //3、查询所有的题目
    List<Problem>  selectAll();

    //4、查询一个题目
    Problem selectOne(@Param("id") int id);

    //5、查询题目根据名字、模糊查询
    List<Problem> selectByLikeTitle(String likeTitle);

    //6、根据id修改题目信息
    int updateById(@Param("id") int id,
                   @Param("title") String title,
                   @Param("level") String level,
                   @Param("description") String description,
                   @Param("template") String template,
                   @Param("testCode") String testCode);


}
