package com.atguigu.springcloud.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommonReuslt<T> {

    private Integer code;
    private String msg;
    private T      data;

    public CommonReuslt(Integer code,String msg){
        this(code, msg, null);
    }

}
