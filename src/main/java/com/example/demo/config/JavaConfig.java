package com.example.demo.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Configuration
@PropertySource("classpath:config.properties") // 加载资源文件
@ComponentScan(basePackages = "com.example.demo.pojo") // 扫描组件，将属性替换
public class JavaConfig {


}
