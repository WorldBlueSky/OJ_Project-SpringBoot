package com.example.demo.util;

import org.springframework.stereotype.Service;

import java.io.*;

public class FileUtil {

    // 使用字符流读写文件

    // 读文件的内容到内存中的操作
    public static String readFile(String filePath){// 将filePath中的内容读取出来

        StringBuilder sb = new StringBuilder();
        try(FileReader fileReader = new FileReader(filePath)){

            while(true){
                int ch = fileReader.read();
                if(ch==-1){
                    break;
                }
                sb.append((char) ch);
            }
           return sb.toString();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    // 从内存中读信息写入到文件当中的操作
    public static void writeFile(String filePath,String content){// 将content中的内容写入到filePath中的文件中
        try( FileWriter fileWriter = new FileWriter(filePath)) {
           fileWriter.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
