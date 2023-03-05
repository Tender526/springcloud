package com.atgugui.springcloud.controller;

import com.atgugui.springcloud.service.PaymentFeignService;
import com.atguigu.springcloud.entities.CommonReuslt;
import com.atguigu.springcloud.entities.Payment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@Slf4j
public class OrderFeignController {

    @Resource
    private PaymentFeignService paymentFeignService;

    @GetMapping("/consumer/payment/create")
    public CommonReuslt<Payment> create(@RequestBody Payment payment){

        System.out.println("openfeign...create");
        return paymentFeignService.create(payment);
    }

    @GetMapping("/consumer/payment/get/{id}")
    public CommonReuslt<Payment> get(@PathVariable("id") Long id){

        System.out.println("openfeign...get");
        return paymentFeignService.getPaymentById(id);
    }

    @GetMapping(value = "/consumer/payment/timeout")
    public String openfeignTimeout(){
        System.out.println("openfeign...openfeignTimeout");
        return paymentFeignService.openfeignTimeout();
    }
}
