package com.example.demo.service.impl;

import com.example.demo.pojo.Answer;
import com.example.demo.pojo.Question;
import com.example.demo.service.TaskService;
import com.example.demo.util.CommandUtil;
import com.example.demo.util.FileUtil;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

// 每次的编译+运行作为一个Task
@Service
public class TaskServiceImpl implements TaskService {

    // 进程与进程之间是存在独立性的，一个进程很难影响到其他进程
    //临时文件进行进程间通信

    // 表示所有临时目录所在的目录
    private static String WORK_DIR;

    // 约定代码的类名
    private static final String CLASS="Solution";

    //约定要编译的代码文件名
    private static  String CODE;

    //存放编译错误信息的文件名
    private static  String COMPILE_ERROR;

    //存放运行错误信息的文件名
    private static  String STDERR;

    //存在运行输出信息的文件名
    private static  String STDOUT;

    public TaskServiceImpl() {
        WORK_DIR="./tmp/"+UUID.randomUUID().toString()+"/";
        CODE=WORK_DIR+"Solution.java";
        COMPILE_ERROR = WORK_DIR+"compileError.txt";
        STDERR = WORK_DIR+"stderr.txt";
        STDOUT = WORK_DIR+"stdout.txt";
    }

    // 作为核心方法，编译加运行
    //参数：要编译运行的java源代码
    //返回值：编译运行的结果，编译出错、运行出错、运行正确

    public Answer compileAndRun(Question question){



        Answer answer = new Answer();

        File workDir = new File(WORK_DIR);

        if(!workDir.exists()){
            // 如果路径不存在的话，创建多级目录
            workDir.mkdirs();
        }



        //0、将源代码字符串 code 写入到 Solution.java文件，
        // 类名和文件名要求要一致，所以约定类名和文件名都叫做Solution

        FileUtil.writeFile(CODE,question.getCode());// 把code写入到Solution.java文件中


        //1、调用javac进行编译，需要.java文件
        // 如果编译出错，将错误信息写入到stderr中，用一个文件保存compileError.txt,直接返回

        // 此处需要先把编译命令给构造出来

        String compileCmd = String.format("javac -encoding utf8 %s -d %s",CODE,WORK_DIR);
        // System.out.println("编译命令: "+compileCmd);

        // utf8识别中文字符 -d 后面指定放置编译生成的.class文件的位置

        CommandUtil.run(compileCmd,null,COMPILE_ERROR);

        // 如果编译出错了，那么错误信息就被记录到COMILE_ERROR这个文件中了，如果没有编译出错，那么这个文件为空

        String compileError = FileUtil.readFile(COMPILE_ERROR);

        if(compileError==null || !compileError.equals("")){
            // System.out.println("编译出错!");
            // 编译出错，直接返回answer
            answer.setError(1);
            answer.setReason(compileError);// 错误信息读取编译错误文件中读取
            return answer;
        }
        // 编译正确继续向下执行逻辑


        //2、再次创建子进程，调用java命令，执行.class文件，生成 stdout.txt stderr.txt
        // 如果运行出错，直接返回

        String runCmd = String.format("java -classpath %s %s ",WORK_DIR,CLASS); // -classpath 指定.class文件的位置
        //System.out.println("运行命令: "+runCmd);
        CommandUtil.run(runCmd,STDOUT,STDERR);

        // 读取运行时标准错误的文件，如果非空，那么返回answer，如果不为空，那么继续执行后续逻辑

        String runStderr = FileUtil.readFile(STDERR);

        if(runStderr==null || !runStderr.equals("")){
            // System.out.println("运行出错!");
            // 设置运行错误的返回结果
            answer.setError(2);
            answer.setReason(runStderr);
            return answer;
        }

        //3、父进程获取到编译执行的结果，打包成Answer对象返回
        // 编译运行的结果，就通过从之前读内存的文件中进行获取

//        System.out.println("编译运行正常!");
        answer.setError(0);
        answer.setStdout(FileUtil.readFile(STDOUT));

        return answer;
    }


}
