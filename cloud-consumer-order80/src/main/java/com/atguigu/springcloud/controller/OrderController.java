package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.entities.CommonReuslt;
import com.atguigu.springcloud.entities.Payment;
import com.atguigu.springcloud.lb.LoadBalancer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.List;

@RestController
@Slf4j
public class OrderController {

    @Resource
    private RestTemplate restTemplate;

    private static final String PAYMENT_SRV = "http://CLOUD-PAYMENT-SERVICE";

    @Resource // 可以获取注册中心上的服务列表
    private DiscoveryClient discoveryClient;

    @Resource // 自定义的负载均衡
    private LoadBalancer loadBalancer;

    private String SERVER_NAME = "CLOUD-PAYMENT-SERVICE";

    @GetMapping("/consumer/payment/create")
    public CommonReuslt<Payment> create(@RequestBody Payment payment){

        return restTemplate.postForObject(PAYMENT_SRV+"/payment/create",
                payment,
                CommonReuslt.class);

    }

    @GetMapping("/consumer/payment/postForEntity")
    public CommonReuslt<Payment> postForEntity(@RequestBody Payment payment){

        return restTemplate.postForEntity(PAYMENT_SRV+"/payment/create",
                payment,
                CommonReuslt.class).getBody();
    }

    @GetMapping("/consumer/payment/get/{id}")
    public CommonReuslt<Payment> get(@PathVariable("id") Long id){
        return restTemplate.getForObject(PAYMENT_SRV+"/payment/get/"+id,
                CommonReuslt.class);
    }

    @GetMapping("/consumer/payment/getForEntity/{id}")
    public CommonReuslt<Payment> getForEntity(@PathVariable("id") Long id){
        ResponseEntity<CommonReuslt> entity = restTemplate.getForEntity(PAYMENT_SRV + "/payment/get/" + id,
                CommonReuslt.class);
        if(entity.getStatusCode().is2xxSuccessful()){
            log.info(entity.getStatusCode()+"/t"+entity.getHeaders());
            return entity.getBody();
        }else{
            log.info("查询失败");
            return new CommonReuslt<>(400,"查询失败");
        }
    }



    @GetMapping(value = "/consumer/payment/lb")
    public String getPaymentLB(){

        log.info("SERVER_NAME="+SERVER_NAME);
        List<ServiceInstance> instances = discoveryClient.getInstances(SERVER_NAME);
        if(instances == null || instances.size() <=0){
            return null;
        }else{
            ServiceInstance instance = loadBalancer.instances(instances);
            return restTemplate.getForObject(instance.getUri()+"/payment/lb",String.class);
        }

    }

}
