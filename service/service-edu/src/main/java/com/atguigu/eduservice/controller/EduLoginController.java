package com.atguigu.eduservice.controller;

import com.atguigu.commonutils.R;
import org.springframework.web.bind.annotation.*;

@CrossOrigin//解决跨域问题
@RestController
@RequestMapping("/eduservice/user")
public class EduLoginController {


    @PostMapping("/login")
    public R login(){

        return R.ok().data("token","admin");
    }

    @GetMapping("/info")
    public R info(){

        return R.ok().data("roles","[admin]").data("name","admin").data("avatar","https://edu-11-21.oss-cn-shenzhen.aliyuncs.com/02.jpg");
    }
}
