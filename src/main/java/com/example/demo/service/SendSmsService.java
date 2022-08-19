package com.example.demo.service;

public interface SendSmsService {

    /**
    * @param phoneNum 短信发送的手机号
    * @param templateCode 使用的短信模板id
    * @param code 发送的手机验证码
    * @return 返回是否发送成功
    */

    boolean send(String phoneNum,String templateCode,String code);

}
