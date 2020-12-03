package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.vo.CourseInfo;
import com.atguigu.eduservice.entity.vo.CoursePublishVo;
import com.atguigu.eduservice.entity.vo.CourseQuery;
import com.atguigu.eduservice.service.EduCourseService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-11-24
 */
@CrossOrigin
@RestController
@RequestMapping("/eduservice/course")
public class EduCourseController {
    @Autowired
    private EduCourseService eduCourseService;

    @PostMapping("/addCourseInfo")
    public R addCourseInfo(@RequestBody CourseInfo courseInfoForm){

        String id = eduCourseService.addCourseInfo(courseInfoForm);

        return R.ok().data("courseId",id);
    }
    //根据课程id查询基本信息,做数据回显

    @GetMapping("/getCourseById/{courseId}")
    public R getCourseById(@PathVariable String courseId){

        CourseInfo courseInfo = eduCourseService.getCourseById(courseId);

        return R.ok().data("courseInfo",courseInfo);
    }

    @PostMapping("/updateCourseInfo")
    public R updateCourseInfo(@RequestBody CourseInfo courseInfo){

        eduCourseService.updateInfo(courseInfo);

        return R.ok();
    }
    @GetMapping("/getCoursePublishVoById/{id}")
    public R getCoursePublishVoById(@PathVariable String id){

        CoursePublishVo coursePublishVo = eduCourseService.getCoursePublishVoById(id);

        return R.ok().data("coursePublishVo",coursePublishVo);

    }

    //发布课程
    @PostMapping("/publishCourse/{id}")
    public R publishCourse(@PathVariable String id){
        EduCourse eduCourse = new EduCourse();
        eduCourse.setId(id);
        eduCourse.setStatus("Normal");//设置状态为已发布
        eduCourseService.updateById(eduCourse);

        return R.ok();
    }
    //无条件查询课程
    @PostMapping("/coursetList/{current}/limit")
    public R getCourseList(@PathVariable Long current,@PathVariable Long limit){
        //需要参数当前页current,和每页显示的记录数limit
        Page<EduCourse> page = new Page<>(current,limit);//mybatisplus提供的分页
        eduCourseService.page(page,null);
        //返回总记录数(一个集合)和总页数
        Long total = page.getTotal();

        List<EduCourse> records = page.getRecords();

        return R.ok().data("total",total).data("rows",records);


    }
    @PostMapping("/getCourseByConditions/current/limit")
    public R getCourseListByConditions(@PathVariable Long current, @PathVariable Long limit, @RequestBody CourseQuery courseQuery){

        Page<EduCourse> page = new Page<>(current,limit);
        eduCourseService.pageQuery(page,courseQuery);
        //总课程
        List<EduCourse> records = page.getRecords();
        long total = page.getTotal();

        return R.ok().data("total",total).data("rows",records);


    }
    //删除课程
    @DeleteMapping("deleteCourse/{courseId}")
    public R deleteCourseById(@PathVariable String courseId){

        eduCourseService.removeCourse(courseId);

        return R.ok();
    }




}

