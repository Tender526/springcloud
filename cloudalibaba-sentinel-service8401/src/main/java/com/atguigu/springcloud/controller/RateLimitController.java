package com.atguigu.springcloud.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.atguigu.springcloud.entities.CommonReuslt;
import com.atguigu.springcloud.entities.Payment;
import com.atguigu.springcloud.myhandler.CustomHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class RateLimitController {

    @GetMapping("/byResource")
    @SentinelResource(value = "byResource",blockHandler = "handleException")
    public CommonReuslt byResource()
    {
        return new CommonReuslt(200,"按资源名称限流测试OK",new Payment(2020L,"serial001"));
    }
    public CommonReuslt handleException(BlockException exception)
    {
        return new CommonReuslt(444,exception.getClass().getCanonicalName()+"\t 服务不可用");
    }

    @GetMapping("/test/byUrl")
    @SentinelResource(value = "byUrl")
    public CommonReuslt byUrl(){
        return new CommonReuslt(200,"按url限流测试OK",new Payment(2020L,"serial002"));
    }


    @GetMapping("/customHandler")
    @SentinelResource(
            value = "customHandler",
            blockHandlerClass = CustomHandler.class,
            blockHandler = "handleException2"
    )
    public CommonReuslt customHandler(){
        return new CommonReuslt(200,"customHandler限流测试OK",new Payment(2020L,"serial002"));
    }
}
