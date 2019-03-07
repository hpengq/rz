package com.itqf.dtboot.controller;

import com.google.code.kaptcha.Producer;
import com.itqf.dtboot.anno.MyLog;
import com.itqf.dtboot.dto.UserDTO;
import com.itqf.dtboot.entity.SysUser;
import com.itqf.dtboot.service.UserService;
import com.itqf.dtboot.utils.*;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;


@Controller
public class UserController {

    //注入service
    @Resource
    private UserService userService;
    //注入验证码对象
    @Resource
    private Producer producer;

    @RequestMapping("/findById")
    @ResponseBody
    @RequiresPermissions("sys:user:findById")
    public SysUser  find(){
        return userService.findById(1l);
    }






    //登录
    @RequestMapping("/sys/login")
    @ResponseBody
    public R login(@RequestBody UserDTO userDTO){//@RequestBody：json字符串--->对象
        //比较验证码
        //得到生成的验证码
        System.out.println(userDTO.toString());
        String code = ShiroUtils.getKaptcha();
        System.out.println(code);
        if(!code.equalsIgnoreCase(userDTO.getCaptcha())){
            return R.error("验证码错误");
        }

        try {
            //1 得到Subject对象
            Subject subject = SecurityUtils.getSubject();
            userDTO.setPassword(MD5Utils.md5(userDTO.getPassword(), userDTO.getUsername(),1024 ));
            //2 把用户名密码封装成Token对象
            UsernamePasswordToken token = new UsernamePasswordToken(userDTO.getUsername(),userDTO.getPassword());
            //记住我
            if(userDTO.isRememberMe()){
                token.setRememberMe(true);
            }
            subject.login(token);//底层shiro的api调用自定义realm
            //判断权限的方法
            //java
            System.out.println(subject.isPermitted("sys:role:info")?"具有sys:role:info权限":"不具有sys:role:info");
            System.out.println(subject.hasRole("管理员")?"具有管理员角色":"不具有");

            return  R.ok();//code:0
        } catch (AuthenticationException e) {
            e.printStackTrace();
            return  R.error(e.getMessage());
        }


    }

    //分页
    @MyLog(value = "查询用户列表")
    @RequestMapping("/sys/user/list")
    @ResponseBody
    @RequiresPermissions("sys:user:list")
    public Result findUser(Query query){

        return userService.findUserByPage(query);
    }

    @RequestMapping("/sys/user/info")
    @ResponseBody
    @RequiresPermissions("sys:user:info")

    public R userInfo(){
        //shiro内存存有一些用户信息
     SysUser user= ShiroUtils.getUserEntity();
        return R.ok().put("user",user);
    }

    //删除
    @MyLog(value = "删除用户")
    @RequestMapping("/sys/user/del")
    @ResponseBody
    public R deleteUser(@RequestBody List<Long> ids){
        return  userService.deleteUser(ids);
    }
    //新增
    @MyLog(value = "新增用户")
    @RequestMapping("/sys/user/save")
    @ResponseBody
    public R saveUser(@RequestBody SysUser sysUser){
        return userService.saveUser(sysUser);
    }
//    修改 获取修改的user id
    @MyLog(value = "获得要修改用户的id")
    @RequestMapping("/sys/user/info/{userId}")
    @ResponseBody
    public R userInfo(@PathVariable("userId") long userId){
        return  userService.userInfo(userId);
    }
    //修改
    @MyLog(value = "修改用户")
    @RequestMapping("/sys/user/update")
    @ResponseBody
    public R updateUser(@RequestBody SysUser sysUser){
        return userService.updateUser(sysUser);
    }



//    @MyLog("导出用户列表")
      @RequestMapping("/sys/user/export")
      @RequiresPermissions("sys:user:export")
//    @ResponseBody
      public void exportUser (HttpServletResponse response){
          try {
              Workbook workbook = userService.exportUser();
              //设置响应头
              response.setContentType("application/octet-stream");//所有文件都支持
              String fileName = "千锋集团员工信息.xlsx";
              fileName = URLEncoder.encode(fileName, "utf-8");
              response.setHeader("content-disposition", "attachment;filename="+fileName);
              //文件下载
              workbook.write(response.getOutputStream());
              Lg.log("文件下载成功");
          } catch (IOException e) {
              e.printStackTrace();
          }
      }






}
