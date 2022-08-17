package com.example.demo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// 这个类作为一个Task的参数，包含要编译的代码
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Question {
    private String code;
}
