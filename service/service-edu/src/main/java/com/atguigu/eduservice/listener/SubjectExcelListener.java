package com.atguigu.eduservice.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.atguigu.eduservice.entity.EduSubject;
import com.atguigu.eduservice.entity.excel.ExcelData;
import com.atguigu.eduservice.service.EduSubjectService;
import com.atguigu.servicebase.handler.GuLiException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

public class SubjectExcelListener extends AnalysisEventListener<ExcelData> {

    public EduSubjectService subjectService;


    public SubjectExcelListener() {
    }

    public SubjectExcelListener(EduSubjectService subjectService) {
        this.subjectService = subjectService;
    }

    //读取excel内容,一行一行读
    @Override
    public void invoke(ExcelData excelData, AnalysisContext analysisContext) {
        if (excelData == null) {
            throw new GuLiException(20001, "文件数据为空");
        }
        //添加一级分类
        EduSubject existOneSubject = this.existOneSubject(subjectService, excelData.getOneSubjectName());
        if (existOneSubject == null) {//没有相同的一级分类.添加
            existOneSubject = new EduSubject();
            existOneSubject.setTitle(excelData.getOneSubjectName());
            existOneSubject.setParentId("0");
            subjectService.save(existOneSubject);
        }


        //添加二级分类
        //获取一级分类id值
        String pid = existOneSubject.getId();

        EduSubject existTwoSubject = this.existTwoSubject(subjectService, excelData.getTwoSubjectName(), pid);
        if (existTwoSubject == null) {
            existTwoSubject = new EduSubject();
            existTwoSubject.setTitle(excelData.getTwoSubjectName());
            existTwoSubject.setParentId(pid);
            subjectService.save(existTwoSubject);
        }


    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }


    //判断2级分类是否重复
    private EduSubject existTwoSubject(EduSubjectService subjectService, String name, String pid) {
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title", name);
        wrapper.eq("parent_id", pid);
        EduSubject twoSubject = subjectService.getOne(wrapper);
        return twoSubject;
    }

    //判断一级分类是否重复
    private EduSubject existOneSubject(EduSubjectService subjectService, String name) {
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title", name);
        wrapper.eq("parent_id", "0");
        EduSubject oneSubject = subjectService.getOne(wrapper);
        return oneSubject;
    }
}
