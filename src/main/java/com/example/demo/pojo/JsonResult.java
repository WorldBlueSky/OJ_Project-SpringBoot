package com.example.demo.pojo;

import lombok.*;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JsonResult<E> implements Serializable {

    public Integer state;//响应的状态码
    public String message;// 返回信息
    public E data;//返回的数据，因为不确定返回什么类型，所以返回泛型即可，类也是泛型

//    /** 只传递state类型的数据 **/
//    public JsonResult(Integer state) {
//        this.status = state;
//    }
//
//    /* 传递state，以及返回对应的数据 */
//    public JsonResult(Integer state, E data) {
//        this.status = state;
//        this.data = data;
//    }
//
//    /**
//     * 返回出异常的提示信息
//     * @param e
//     */
//    public JsonResult(Throwable e) {
//        this.message = e.getMessage();
//    }

}
