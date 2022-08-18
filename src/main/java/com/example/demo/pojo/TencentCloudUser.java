package com.example.demo.pojo;

import lombok.EqualsAndHashCode;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class TencentCloudUser implements InitializingBean {
    @Value("${secretId}")
    private String secretId;
    @Value("${secretKey}")
    private String secretKey;

    public static String ID;
    public static String KEY;


    @Override
    public void afterPropertiesSet() throws Exception {
        ID=this.secretId;
        KEY=this.secretKey;
    }
}
