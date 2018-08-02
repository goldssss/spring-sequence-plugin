package com.goldssss.sequence;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class SpringSequencePluginApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringSequencePluginApplication.class, args);
    }
}
