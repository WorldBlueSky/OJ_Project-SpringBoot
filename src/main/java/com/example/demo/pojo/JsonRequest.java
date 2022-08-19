package com.example.demo.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JsonRequest {

    // 用来接收前端传递过来的 JS对象转成JSON格式字符串 的数据，接收JSON对象
    private String  id;
    private String code;
}
