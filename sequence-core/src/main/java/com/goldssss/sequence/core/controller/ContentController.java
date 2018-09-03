package com.goldssss.sequence.core.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/seq")
public class ContentController {

    @RequestMapping("/list")
    public String list(){
        return "content";
    }
}
