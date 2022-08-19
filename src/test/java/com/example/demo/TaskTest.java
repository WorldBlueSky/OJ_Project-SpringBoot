package com.example.demo;

import com.example.demo.pojo.Answer;
import com.example.demo.pojo.Question;
import com.example.demo.service.impl.TaskServiceImpl;
import org.junit.Test;

public class TaskTest {

    @Test
    public void test(){
        TaskServiceImpl task = new TaskServiceImpl();
        Question question = new Question();
        question.setCode("public class Solution {\n" +
                "\n" +
                "    public static void main(String[] args) {\n" +
                "        System.out.println(\"hello world!\");\n" +
                "    }\n" +
                "}");
        Answer answer = task.compileAndRun(question);
        System.out.println(answer);

    }
}
