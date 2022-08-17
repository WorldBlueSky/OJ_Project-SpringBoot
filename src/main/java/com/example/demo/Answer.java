package com.example.demo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// 表示一个task的执行结果
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Answer {
    //约定error 0--编译运行ok  1--编译出错  2--运行出错
    private int error;
    // 如果编译出错，reason放编译出错信息，如果运行错误，放运行异常的信息
    private String reason;
    // 如果编译运行都ok的话，放运行程序获取标准输出的结果
    private String stdout;
    //放标准错误
    private String stderr;
}
