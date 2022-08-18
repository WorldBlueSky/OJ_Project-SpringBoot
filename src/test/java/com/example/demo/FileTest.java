package com.example.demo;

import com.example.demo.util.FileUtil;
import org.junit.Test;

public class FileTest {

    @Test
    public void test(){
        FileUtil.writeFile("./hello.txt","你好世界!");
        System.out.println(FileUtil.readFile("./hello.txt"));
    }



}
