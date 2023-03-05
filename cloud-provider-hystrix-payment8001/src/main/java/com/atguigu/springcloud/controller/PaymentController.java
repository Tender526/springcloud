package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.entities.CommonReuslt;
import com.atguigu.springcloud.entities.Payment;
import com.atguigu.springcloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@Slf4j
public class PaymentController {

    @Value("${server.port}")
    private String serverPort;

    @Resource
    private PaymentService paymentService;
    
    @Resource
    private DiscoveryClient discoveryClient;

    @PostMapping(value="/payment/create")
    public CommonReuslt create(@RequestBody Payment payment){
        int result = paymentService.create(payment);
        if(result >0 ){
            return new CommonReuslt(200, "插入成功"+",port:"+serverPort, result);
        }else{
            return new CommonReuslt(444, "插入失败"+",port:"+serverPort, null);
        }
    }

    @GetMapping(value="/payment/get/{id}")
    public CommonReuslt<Payment> getPaymentById(@PathVariable("id") Long id){
        Payment payment = paymentService.getPaymentById(id);
        if(payment!=null){
            return new CommonReuslt(200,"查询成功"+",port:"+serverPort, payment);
        }else{
            return new CommonReuslt(444, "没有对应记录啊啊,查询ID: "+id+",port:"+serverPort,null);
        }

    }
    
    @GetMapping(value = "/payment/discovery")
    public Object Discovery(){
        List<ServiceInstance> instances = discoveryClient.getInstances("cloud-payment-service");
        for (ServiceInstance instance : instances){
            log.info(instance.getInstanceId()+"/t"+
                instance.getHost()+"/t"+
                instance.getUri()+"/t"+
                instance.getPort()
            );
        }
        return discoveryClient;
    }


    @GetMapping(value = "/payment/lb")
    public String getPaymentLB()
    {
        return serverPort;
    }


    @GetMapping(value = "/payment/timeout")
    public String openfeignTimeout(){

        System.out.println("*****paymentFeignTimeOut from port: "+serverPort);
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return serverPort;
    }
}
