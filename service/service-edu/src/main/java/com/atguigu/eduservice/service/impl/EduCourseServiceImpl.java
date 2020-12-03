package com.atguigu.eduservice.service.impl;

import com.alibaba.excel.util.StringUtils;
import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.EduCourseDescription;
import com.atguigu.eduservice.entity.vo.CourseInfo;
import com.atguigu.eduservice.entity.vo.CoursePublishVo;
import com.atguigu.eduservice.entity.vo.CourseQuery;
import com.atguigu.eduservice.mapper.EduCourseMapper;
import com.atguigu.eduservice.service.EduChapterService;
import com.atguigu.eduservice.service.EduCourseDescriptionService;
import com.atguigu.eduservice.service.EduCourseService;
import com.atguigu.eduservice.service.EduVideoService;
import com.atguigu.servicebase.handler.GuLiException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-11-24
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {

    @Autowired
    private EduCourseDescriptionService courseDescriptionService;

    @Autowired
    private EduVideoService videoService;

    @Autowired
    private EduChapterService chapterService;


    @Override
    public String addCourseInfo(CourseInfo courseInfoForm) {
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoForm, eduCourse);
        //1,向课程表添加基本信息
        int insert = baseMapper.insert(eduCourse);
        if (insert == 0) {

            throw new GuLiException(20001, "添加课程信息失败");

        }
        //向课程简介表添加信息
        String cid = eduCourse.getId();
        EduCourseDescription description = new EduCourseDescription();
        description.setDescription(courseInfoForm.getDescription());
        description.setId(cid);//两张表根据id关联
        courseDescriptionService.save(description);

        return cid;
    }

    @Override
    public CourseInfo getCourseById(String courseId) {

        QueryWrapper<EduCourse> queryWrapper = new QueryWrapper<>();
        //去课程表查基本信息
        queryWrapper.eq("id", courseId);
        EduCourse eduCourse = baseMapper.selectOne(queryWrapper);
        CourseInfo courseInfo = new CourseInfo();
        BeanUtils.copyProperties(eduCourse, courseInfo);
        //去简介表查基本信息
        QueryWrapper<EduCourseDescription> wrapper = new QueryWrapper<>();
        wrapper.eq("id", courseId);
        EduCourseDescription description = courseDescriptionService.getOne(wrapper);

        courseInfo.setDescription(description.getDescription());


        return courseInfo;
    }

    @Override
    public void updateInfo(CourseInfo courseInfo) {
        //修改课程表
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfo, eduCourse);
        int i = baseMapper.updateById(eduCourse);
        if (i == 0) {
            throw new GuLiException(20001, "修改失败");
        }
        //修改备注表
        EduCourseDescription description = new EduCourseDescription();
        BeanUtils.copyProperties(courseInfo, description);
        boolean b = courseDescriptionService.updateById(description);


    }

    @Override
    public CoursePublishVo getCoursePublishVoById(String id) {

        CoursePublishVo coursePublishVo = baseMapper.getCoursePublishVoById(id);
        return coursePublishVo;
    }

    @Override
    public void pageQuery(Page<EduCourse> page, CourseQuery courseQuery) {
        QueryWrapper<EduCourse> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("gmt_create");

        if (courseQuery == null){
            baseMapper.selectPage(page, queryWrapper);
            return;
        }
        String title = courseQuery.getTitle();
        String teacherId = courseQuery.getTeacherId();
        String subjectParentId = courseQuery.getSubjectParentId();
        String subjectId = courseQuery.getSubjectId();

        if (!StringUtils.isEmpty(title)) {
            queryWrapper.like("title", title);
        }

        if (!StringUtils.isEmpty(teacherId) ) {
            queryWrapper.eq("teacher_id", teacherId);
        }

        if (!StringUtils.isEmpty(subjectParentId)) {
            queryWrapper.ge("subject_parent_id", subjectParentId);
        }

        if (!StringUtils.isEmpty(subjectId)) {
            queryWrapper.ge("subject_id", subjectId);
        }

        baseMapper.selectPage(page,queryWrapper);
    }
//删除课程
    @Override
    public void removeCourse(String courseId) {
        //根据课程id删除小节
        videoService.removeByCourseId(courseId);

        //根据课程id删除章节
        chapterService.removeByCourseId(courseId);

        //根据课程id删除描述
        courseDescriptionService.removeById(courseId);

        //根据课程id删除课程
        int i = baseMapper.deleteById(courseId);
        if (i == 0){
            throw new GuLiException(20001,"删除失败");
        }

    }


}
