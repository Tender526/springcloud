package com.atgugui.springcloud.service;

import com.atguigu.springcloud.entities.CommonReuslt;
import com.atguigu.springcloud.entities.Payment;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Component
@FeignClient(value = "CLOUD-PAYMENT-SERVICE") // 去找哪个微服务
public interface PaymentFeignService {

    @PostMapping(value="/payment/create")
    CommonReuslt create(@RequestBody Payment payment);

    @GetMapping(value="/payment/get/{id}")
    CommonReuslt<Payment> getPaymentById(@PathVariable("id") Long id);

    @GetMapping(value = "/payment/timeout")
    String openfeignTimeout();
}
