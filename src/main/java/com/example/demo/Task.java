package com.example.demo;

// 每次的编译+运行作为一个Task
public class Task {
    // 作为核心方法，编译加运行
    //参数：要编译运行的java源代码
    //返回值：编译运行的结果，编译出错、运行出错、运行正确
    public Answer compileAndRun(Question question){

        //0、将源代码字符串 code 写入到 Solution.java文件，
        // 类名和文件名要求要一致，所以约定类名和文件名都叫做Solution



        //1、调用javac进行编译，需要.java文件
        // 如果编译出错，将错误信息写入到stderr中，用一个文件保存compileError.txt,直接返回

        //2、再次创建子进程，调用java命令，执行.class文件
        // 如果运行出错，直接返回


        //3、父进程获取到编译执行的结果，打包成Answer对象返回

        return null;
    }
}
