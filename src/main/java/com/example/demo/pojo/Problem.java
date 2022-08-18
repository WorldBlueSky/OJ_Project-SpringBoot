package com.example.demo.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Problem {
    private int id;
    private String title;
    private String level;
    private String description;
    private String template;
    private String testCode;
}
