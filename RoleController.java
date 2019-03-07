package com.itqf.dtboot.controller;

import com.itqf.dtboot.anno.MyLog;
import com.itqf.dtboot.entity.SysRole;
import com.itqf.dtboot.service.RoleService;
import com.itqf.dtboot.utils.Query;
import com.itqf.dtboot.utils.R;
import com.itqf.dtboot.utils.Result;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

@Controller
public class RoleController {

    //注入
    @Resource
    private RoleService roleService;
    @MyLog(value = "查询角色列表")
    @RequestMapping("/sys/role/list")
    @ResponseBody
    public Result findRole(Query query){
        return roleService.findRoleByPage(query);
    }

    //删除
    @MyLog(value = "删除角色")
    @RequestMapping("/sys/role/del")
    @ResponseBody
    public R deleteUser(@RequestBody List<Long> ids){
        return  roleService.deleteRole(ids);
    }
    //新增
    @MyLog(value = "新增角色")
    @RequestMapping("/sys/role/save")
    @ResponseBody
    public R savaUser(@RequestBody SysRole sysRole){
        return roleService.saveRole(sysRole);
    }
    //修改 获取修改的user id
    @RequestMapping("/sys/role/info/{roleId}")
    @ResponseBody
    public R userInfo(@PathVariable("roleId") long roleId){
        return  roleService.roleInfo(roleId);
    }
    //修改
    @MyLog(value = "修改角色")
    @RequestMapping("/sys/role/update")
    @ResponseBody
    public R updateUser(@RequestBody SysRole sysRole){
        return roleService.updateRole(sysRole);
    }



}
