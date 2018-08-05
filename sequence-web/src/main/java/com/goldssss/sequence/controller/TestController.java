package com.goldssss.sequence.controller;

import com.goldssss.sequence.core.aop.SequenceMethodAspect;
import com.goldssss.sequence.service.ServiceDemo1;
import com.goldssss.sequence.service.ServiceDemo2;
import com.goldssss.sequence.service.ServiceDemo3;
import com.goldssss.sequence.core.entity.SequenceMethodDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;


@RestController
public class TestController {

    @Autowired
    private ServiceDemo1 serviceDemo1;
    @Autowired
    private ServiceDemo2 serviceDemo2;
    @Autowired
    private ServiceDemo3 serviceDemo3;

    @RequestMapping("/")
    public String test(){
        SequenceMethodDTO sequenceMethodDTO = new SequenceMethodDTO();
        serviceDemo3.serviceDemo3();
        serviceDemo1.serviceDemo1();
        serviceDemo2.serviceDemo2();
        serviceDemo2.serviceDemo2_1("str");
        serviceDemo2.serviceDemo2_2("str");
        return "test";
    }
    @RequestMapping("/aaa")
    public String aaa(){
        return "aaa";
    }
    @RequestMapping("/bbb")
    public String bbb(String a ,String b ){
        serviceDemo2.serviceDemo2_1(a);
        serviceDemo3.serviceDemo3();
        serviceDemo1.serviceDemo1();
        serviceDemo2.serviceDemo2();
        serviceDemo2.serviceDemo2_1("str");
        serviceDemo2.serviceDemo2_2("str");
        return "aaaa";
    }
}
