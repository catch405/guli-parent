package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.subject.LevelOneSubject;
import com.atguigu.eduservice.service.EduSubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-11-23
 */
@CrossOrigin
@RestController
@RequestMapping("/eduservice/subject")
public class EduSubjectController {
    @Autowired
    private EduSubjectService subjectService;
//添加课程分类
    @PostMapping("/addSubject")
    public R addSubject(MultipartFile file){

        subjectService.importSubjectData(file,subjectService);

        return R.ok();
    }

    //课程分类列表(树形结构)
    @GetMapping("/getAllSubject")
    public R getAllSubject(){
        List<LevelOneSubject> list = subjectService.getAllSubject();

        return R.ok().data("list",list);

    }

}

