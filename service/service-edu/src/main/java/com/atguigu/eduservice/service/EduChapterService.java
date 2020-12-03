package com.atguigu.eduservice.service;

import com.atguigu.eduservice.entity.EduChapter;
import com.atguigu.eduservice.entity.vo.chapter.ChapterVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author testjava
 * @since 2020-11-24
 */
public interface EduChapterService extends IService<EduChapter> {

    List<ChapterVo> getChapterById(String courseId);



    Boolean deleteById(String chapterId);

    void removeByCourseId(String courseId);
}
