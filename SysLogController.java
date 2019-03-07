package com.itqf.dtboot.controller;

import com.itqf.dtboot.anno.MyLog;
import com.itqf.dtboot.service.LogService;
import com.itqf.dtboot.utils.Query;
import com.itqf.dtboot.utils.Result;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
public class SysLogController {
    @Resource
    private LogService logService;
    @MyLog(value = "查询日志列表")
    @RequestMapping("/sys/log/list")
    @RequiresPermissions("sys:log:list")
    @ResponseBody
    public Result logList(Query query){
        return logService.findByPage(query);

    }
}
