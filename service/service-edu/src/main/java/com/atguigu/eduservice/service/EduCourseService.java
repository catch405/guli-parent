package com.atguigu.eduservice.service;

import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.vo.CourseInfo;
import com.atguigu.eduservice.entity.vo.CoursePublishVo;
import com.atguigu.eduservice.entity.vo.CourseQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author testjava
 * @since 2020-11-24
 */
public interface EduCourseService extends IService<EduCourse> {

    String addCourseInfo(CourseInfo courseInfoForm);

    CourseInfo getCourseById(String courseId);

    void updateInfo(CourseInfo courseInfo);

    CoursePublishVo getCoursePublishVoById(String id);

    void pageQuery(Page<EduCourse> page, CourseQuery courseQuery);

    void removeCourse(String courseId);
}
