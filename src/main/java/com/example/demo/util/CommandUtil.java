package com.example.demo.util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class CommandUtil {

    // 创建子进程，执行指定命令

   public static int run(String cmd,String stdoutFile,String stderrFile){
       try {
           //1、通过Runtime类，执行exec
           Process process = Runtime.getRuntime().exec(cmd);

           //2、获取到标准输出 ，写入到指定文件中
           // 如果为null的话，忽略标准输出
           if(stdoutFile!=null){
               InputStream stdoutFrom = process.getInputStream();//从内存中读取内容的流
               FileOutputStream stdoutTo = new FileOutputStream(stdoutFile);//往文件中写入内容的流
               while(true){
                   int ch = stdoutFrom.read();
                   if(ch==-1){
                       break;
                   }
                   stdoutTo.write(ch);
               }
               // 关闭资源
               stdoutFrom.close();
               stdoutTo.close();
           }

           //3、获取到标准错误，写入到指定文件中
           // 如果为null，忽略标准错误
           if(stderrFile!=null){
               InputStream stderrFrom = process.getErrorStream();//从内存中读取内容的流
               FileOutputStream stderrTo = new FileOutputStream(stderrFile);//往文件中写入内容的流
               while(true){
                   int ch = stderrFrom.read();
                   if(ch==-1){
                       break;
                   }
                   stderrTo.write(ch);
               }
               // 关闭资源
               stderrFrom.close();
               stderrTo.close();
           }

           //4、等待子进程结束，拿到子进程的状态码，并返回
           int exitCode = process.waitFor();
           return exitCode;

       } catch (IOException | InterruptedException e) {
           e.printStackTrace();
       }
       return 0;
    }
}
