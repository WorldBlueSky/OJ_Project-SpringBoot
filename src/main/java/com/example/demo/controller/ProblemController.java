package com.example.demo.controller;

import com.example.demo.exception.ProblemNotFountException;
import com.example.demo.mapper.ProblemMapper;
import com.example.demo.pojo.Problem;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/oj")
public class ProblemController extends BaseController {

    @Autowired
    public ProblemMapper problemMapper;

    @Autowired
    public ObjectMapper objectMapper;

    @RequestMapping(value = "/problems",produces = "application/json;charset=utf-8")
     public String getProblems(@RequestParam(value = "id",required = false) String id) throws ProblemNotFountException, JsonProcessingException {

        if(id==null || id.equals("")){// 如果没有参数的话，那么直接查询全部题目
            List<Problem> list = problemMapper.selectAll();
                return objectMapper.writeValueAsString(list);//前端接收的是JSON格式的数据，所以要将list转化成 json格式的字符串
        }

        // 如果有参数的话，那么查询指定id的题目详情
        int idString = Integer.parseInt(id);
        Problem problem = problemMapper.selectOne(idString);

        if(problem==null){
            throw new ProblemNotFountException();
        }

        return objectMapper.writeValueAsString(problem);

     }


}
