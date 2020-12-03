package com.atguigu.eduservice.service.impl;

import com.atguigu.eduservice.entity.EduChapter;
import com.atguigu.eduservice.entity.EduVideo;
import com.atguigu.eduservice.entity.vo.chapter.ChapterVo;
import com.atguigu.eduservice.entity.vo.chapter.VideoVo;
import com.atguigu.eduservice.mapper.EduChapterMapper;
import com.atguigu.eduservice.service.EduChapterService;
import com.atguigu.eduservice.service.EduVideoService;
import com.atguigu.servicebase.handler.GuLiException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-11-24
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {

    @Autowired
    private EduVideoService eduVideoService;

    @Override
    public List<ChapterVo> getChapterById(String courseId) {

        //根据条件查询所有的chapter
        QueryWrapper<EduChapter> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id", courseId);
        //courseId的所有章节
        List<EduChapter> eduChapters = baseMapper.selectList(wrapper);


        //最终返回的数据
        List<ChapterVo> chapterVoList = new ArrayList<>();

        QueryWrapper<EduVideo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("course_id", courseId);
        List<EduVideo> videos = eduVideoService.list(queryWrapper);
        for (EduChapter chapter : eduChapters) {
            ChapterVo chapterVo = new ChapterVo();
            BeanUtils.copyProperties(chapter, chapterVo);

            //已完成章节数据封装

            List<VideoVo> videoVoList = new ArrayList<>();

            for (EduVideo eduVideo : videos) {
                if (chapter.getId().equals(eduVideo.getChapterId())) {
                    VideoVo videoVo = new VideoVo();
                    BeanUtils.copyProperties(eduVideo, videoVo);
                    videoVoList.add(videoVo);
                    //已完成二级封装
                }
            }
            chapterVo.setChildren(videoVoList);
            chapterVoList.add(chapterVo);

        }


        return chapterVoList;
    }

    //删除章节
    @Override
    public Boolean deleteById(String chapterId) {
        //根据id查询是否有视频课程
        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("chapter_id", chapterId);
        int count = eduVideoService.count(wrapper);
        if (count > 0) {//查询出小节,不删除
            throw new GuLiException(20001, "该分章节下存在视频课程，请先删除视频课程");
        } else {
            int i = baseMapper.deleteById(chapterId);
            return i > 0;
        }
    }

    @Override
    public void removeByCourseId(String courseId) {
        QueryWrapper<EduChapter> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("course_id",courseId);
        baseMapper.delete(queryWrapper);
    }
}