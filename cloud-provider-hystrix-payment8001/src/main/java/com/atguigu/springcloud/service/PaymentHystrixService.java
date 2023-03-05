package com.atguigu.springcloud.service;

public interface PaymentHystrixService {

    public String paymentInfo_OK(Integer id);

    public String paymentInfo_TimeOut(Integer id);

    public String paymentCircuitBreaker(Integer id);
}
