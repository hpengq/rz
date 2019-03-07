package com.itqf.dtboot.controller;

import com.itqf.dtboot.anno.MyLog;
import com.itqf.dtboot.entity.SysMenu;
import com.itqf.dtboot.service.MenuService;
import com.itqf.dtboot.utils.Query;
import com.itqf.dtboot.utils.R;
import com.itqf.dtboot.utils.Result;
import com.itqf.dtboot.utils.ShiroUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * {total:xxx,rows:{}}
 */
@Controller
public class MenuController {
    //注入service
    @Resource
    private MenuService menuservice;
    @MyLog(value = "查询菜单列表")
    @RequestMapping("/sys/menu/list")
    @ResponseBody
    //@RequiresRoles("管理员")
    @RequiresPermissions("sys:menu:list")
    public Result findMenu(Query query) {
//        System.out.println(menuservice.findMenu(offset, limit, search));
        // System.out.println("333" + menuservice.findMenu(offset, limit, search));
        return menuservice.findMenu(query);

    }
    //删除
    @MyLog(value = "删除菜单")
    @RequestMapping("/sys/menu/del")
    @ResponseBody
    @RequiresPermissions("sys:menu:delete")
    public R delteMenu(@RequestBody List<Long> ids) {//json字符串
        return menuservice.deleteMenu(ids);
    }
    @MyLog(value = "查询菜单")
    @RequestMapping("/sys/menu/select")
    @ResponseBody
    @RequiresPermissions("sys:menu:select")
    public R selectMenu(){
        return menuservice.selectMenu();
    }
    //新增
    @MyLog(value = "新增菜单")
    @RequestMapping("/sys/menu/save")
    @ResponseBody
    @RequiresPermissions("sys:menu:save")
    public R saveMenu(@RequestBody SysMenu sysMenu){
        return menuservice.saveMenu(sysMenu);
    }
    //获取修改的用户id
    @MyLog(value = "查询菜单")
    @RequestMapping("/sys/menu/info/{menuId}")
    @ResponseBody
    public R menuInfo(@PathVariable("menuId") long menuId){
        return menuservice.menuInfo(menuId);
    }
    //修改
    @MyLog(value = "修改菜单")
    @RequestMapping("/sys/menu/update")
    @ResponseBody
    @RequiresPermissions("sys:menu:update")
    public R updateMenu(@RequestBody SysMenu sysMenu){
        return menuservice.updateMenu(sysMenu);
    }

    /**
     * 查询当前用户能够访问的菜单
     *
     */
    @MyLog(value = "查询用户能够访问的菜单")
    @RequestMapping("/sys/menu/user")
    @ResponseBody
    public R userMenu(){
        return menuservice.findUserMenu(ShiroUtils.getUserId());
    }



}

