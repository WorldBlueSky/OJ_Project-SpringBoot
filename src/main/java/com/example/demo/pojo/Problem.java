package com.example.demo.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Problem implements Serializable {
    private int id;
    private String title;
    private String level;
    private String description;
    private String template;
    private String testCode;
}
