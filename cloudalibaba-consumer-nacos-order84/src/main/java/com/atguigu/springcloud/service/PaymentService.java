package com.atguigu.springcloud.service;

import com.atguigu.springcloud.entities.CommonReuslt;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "nacos-payment-provider",fallback = PaymentFallbackService.class)
public interface PaymentService {

    @GetMapping(value = "/paymentSQL/{id}")
    public CommonReuslt paymentSQL(@PathVariable("id") Long id);
}
