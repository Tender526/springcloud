package com.atguigu.springcloud.myhandler;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.atguigu.springcloud.entities.CommonReuslt;
import org.springframework.stereotype.Component;

public class CustomHandler {
    public static CommonReuslt handleException1(BlockException exception){
        return new CommonReuslt(2020,"自定义的限流处理信息......handleException1");
    }


    public static CommonReuslt handleException2(BlockException exception){
        return new CommonReuslt(2020,"自定义的限流处理信息......handleException2");
    }
}
