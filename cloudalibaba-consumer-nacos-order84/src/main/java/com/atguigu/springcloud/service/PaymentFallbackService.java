package com.atguigu.springcloud.service;

import com.atguigu.springcloud.entities.CommonReuslt;
import com.atguigu.springcloud.entities.Payment;
import org.springframework.stereotype.Component;

@Component
public class PaymentFallbackService implements PaymentService{
    @Override
    public CommonReuslt paymentSQL(Long id) {
        return new CommonReuslt(444,"PaymentService fallback 降级方法",
                new Payment(id,"serrial id"));
    }
}
