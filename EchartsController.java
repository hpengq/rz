package com.itqf.dtboot.controller;

import com.itqf.dtboot.service.UserService;
import com.itqf.dtboot.utils.R;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class EchartsController {

    @Resource
    private UserService userService;
    @RequestMapping("/sys/echarts/pie")
    public R pie(){
        return userService.findPieData();
    }
    @RequestMapping("/sys/echarts/bar")
    public  R bar(){
        return userService.findBarData();
    }

}
