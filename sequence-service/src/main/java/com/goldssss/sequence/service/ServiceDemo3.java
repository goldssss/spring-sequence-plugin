package com.goldssss.sequence.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServiceDemo3 {

    @Autowired
    private ServiceDemo4 serviceDemo4;

    public void serviceDemo3(){
        serviceDemo4.serviceDemo4();
    }
}
