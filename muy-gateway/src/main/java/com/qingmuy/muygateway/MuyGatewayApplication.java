package com.qingmuy.muygateway;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableDubbo
public class MuyGatewayApplication {


    public static void main(String[] args) {
        SpringApplication.run(MuyGatewayApplication.class, args);
    }

    //public static void main(String[] args) {
    //    ConfigurableApplicationContext context = SpringApplication.run(MuyGatewayApplication.class, args);
    //    MuyGatewayApplication application = context.getBean(MuyGatewayApplication.class);
    //    String result = application.doSayHello("dingzhen");
    //    System.out.println("result = " + result);
    //}

}
