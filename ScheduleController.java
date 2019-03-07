package com.itqf.dtboot.controller;

import com.itqf.dtboot.entity.ScheduleJob;
import com.itqf.dtboot.service.ScheduleService;
import com.itqf.dtboot.utils.Query;
import com.itqf.dtboot.utils.R;
import com.itqf.dtboot.utils.Result;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class ScheduleController {

    @Resource
    private ScheduleService scheduleService;

    @RequestMapping("/schedule/job/list")
    //@ResponseBody
    public Result scheduleList(Query query){
        return  scheduleService.findByPage(query);
    }


    @RequestMapping("/schedule/job/save")
    public R saveSchedule(@RequestBody ScheduleJob scheduleJob){
        return  scheduleService.saveSchedule(scheduleJob);
    }
    @RequestMapping("/schedule/job/info/{jobId}")
    public  R scheduleInfo(@PathVariable("jobId") long jobId){
        return  scheduleService.findSchedule(jobId);
    }
    //修改
    @RequestMapping("/schedule/job/update")
    public R updateSchedule (@RequestBody ScheduleJob scheduleJob){
        return  scheduleService.updateSchedule(scheduleJob);
    }
    @RequestMapping("/schedule/job/del")
    public R deleteSchedule (@RequestBody List<Long> ids){
        return scheduleService.deleteSchedule(ids);
    }
    //运行任务
    @RequestMapping("/schedule/job/run")
    public R runTask(@RequestBody List<Long> ids){
        return  scheduleService.runTask(ids);

    }
    //暂停任务
    @RequestMapping("/schedule/job/pause")
    public R pause(@RequestBody List<Long> ids){
        return  scheduleService.pauseTask(ids);

    }
    //重置任务
    @RequestMapping("/schedule/job/resume")
    public R resume(@RequestBody List<Long> ids){
        return  scheduleService.resumeTask(ids);

    }


}
