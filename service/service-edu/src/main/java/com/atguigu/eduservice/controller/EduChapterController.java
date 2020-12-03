package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduChapter;
import com.atguigu.eduservice.entity.vo.chapter.ChapterVo;
import com.atguigu.eduservice.service.EduChapterService;
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
@RequestMapping("/eduservice/edu-chapter")
public class EduChapterController {

    @Autowired
    private EduChapterService eduChapterService;
    //根据课程id查章节
    @GetMapping("/getChapterById/{courseId}")
    public R getChapterById(@PathVariable String courseId){

        List<ChapterVo> chapterVoList = eduChapterService.getChapterById(courseId);

        return R.ok().data("list",chapterVoList);
    }

    //添加章节
    @PostMapping("/AddChapter")
    public R addChapter(@RequestBody EduChapter chapter){
        eduChapterService.save(chapter);

        return R.ok();

    }
    //根据id查询章节
    @GetMapping("/getChapter/{chapterId}")
    public R getChapter(@PathVariable String chapterId){
        EduChapter eduChapter = eduChapterService.getById(chapterId);
        return R.ok().data("chapter",eduChapter);


    }
    //修改章节信息
    @PostMapping("/updateChapter")
    public R updateChapter(@RequestBody EduChapter eduChapter){
        eduChapterService.updateById(eduChapter);

        return R.ok();


    }
    //删除章节信息
    @DeleteMapping("/deleteChapter/{chapterId}")
    public R deleteChapter(@PathVariable String chapterId){

     Boolean result = eduChapterService.deleteById(chapterId);

        if(result){
            return R.ok();
        }else{
            return R.error().message("删除失败");
        }

    }




}

