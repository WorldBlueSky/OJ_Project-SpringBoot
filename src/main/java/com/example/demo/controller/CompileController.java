package com.example.demo.controller;

import com.example.demo.exception.ProblemNotFountException;
import com.example.demo.mapper.ProblemMapper;
import com.example.demo.pojo.Answer;
import com.example.demo.pojo.JsonRequest;
import com.example.demo.pojo.Problem;
import com.example.demo.pojo.Question;
import com.example.demo.service.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/oj")
public class CompileController extends BaseController{

    @Autowired
    public Task task;

    @Autowired
    public ProblemMapper problemMapper;

    @PostMapping(value = "/compile",produces = "application/json")
    public Map<String, Object> getCompile(@RequestBody JsonRequest jsonRequest) throws ProblemNotFountException {

        //0、读取传递的正文和id，json传递
        int id = Integer.parseInt(jsonRequest.getId());
        String code = jsonRequest.getCode();

        //System.out.println(id);
        //System.out.println(code);

        //0.5、检查用户提交的代码的安全性，通过对list的黑名单进行查找
        if(!checkCodeSafe(code)){
            //System.out.println("用户提交了不安全的代码!");
            HashMap<String,Object> map = new HashMap<>();
            map.put("error",3);
            map.put("reason","您提交了不安全的代码，可能危害服务器的安全，编译运行失败!");
            return map;
        }

        //1、从数据库中查找到题目，拿到测试用例代码
        Problem problem = problemMapper.selectOne(id);

        if(problem==null){
            throw new ProblemNotFountException("提交的id非法，找不到题目信息!");
        }

        String testCode = problem.getTestCode();

        //2、把用户提交的代码与测试用例代码拼接成一个完整的代码
        String finalCode = mergeCode(code,testCode);
//        System.out.println(finalCode);

        //3、创建一个task，调用编译运行结果
        Question question = new Question();
        question.setCode(finalCode);

        Answer answer = task.compileAndRun(question);

        //4根据Task运行的结果，包装成一个 HTTP响应
        HashMap<String ,Object> map = new HashMap<>();
        map.put("error",answer.getError());
        map.put("reason",answer.getReason());
        map.put("stdout",answer.getStdout());

        return map;
    }

    private String mergeCode(String code, String testCode) {

        // 查找code中 的最后一个 }
        int pos = code.lastIndexOf('}');
        if(pos==-1){// 如果没有找到的话，那么直接返回null，无法拼接
            return null;
        }
      // code截取之后与testCode进行拼接
        return code.substring(0,pos)+testCode+"\n}";

    }

    // 检查代码的安全性的方法
    private boolean checkCodeSafe(String code) {
        List<String> black  = new ArrayList<>();
        // 黑名单是随时扩充的，防止提交的代码危害服务安全
        black.add("Runtime");// 防止提交的代码运行恶意程序
        black.add("exec");
        black.add("java.io");// 禁止提交的代码读写文件
        black.add("java.net"); // 禁止提交的代码访问网络

        for (String target:black) {
            int pos = code.indexOf(target);
            if(pos>=0){
                // 找到任意的恶意代码特征，返回false表示不安全
                return false;
            }
        }
        return true;
    }

}
