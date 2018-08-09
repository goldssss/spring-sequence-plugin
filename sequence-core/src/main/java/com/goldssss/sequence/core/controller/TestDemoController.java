package com.goldssss.sequence.core.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author majinxin1
 * @date 2018/8/6
 */
@Controller
public class TestDemoController {

    @RequestMapping("/test")
    public String test(){
        return "template";
    }

    @RequestMapping("/list")
    public String list(){
        return "sequenceList";
    }
}
