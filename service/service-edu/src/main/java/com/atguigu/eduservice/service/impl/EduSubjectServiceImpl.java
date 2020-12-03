package com.atguigu.eduservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.atguigu.eduservice.entity.EduSubject;
import com.atguigu.eduservice.entity.excel.ExcelData;
import com.atguigu.eduservice.entity.subject.LevelOneSubject;
import com.atguigu.eduservice.entity.subject.LevelTwoSubject;
import com.atguigu.eduservice.listener.SubjectExcelListener;
import com.atguigu.eduservice.mapper.EduSubjectMapper;
import com.atguigu.eduservice.service.EduSubjectService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-11-23
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

    @Override
    public void importSubjectData(MultipartFile file, EduSubjectService subjectService) {

        try {
            InputStream fileInputStream = file.getInputStream();

            EasyExcel.read(fileInputStream, ExcelData.class, new SubjectExcelListener(subjectService)).sheet().doRead();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<LevelOneSubject> getAllSubject() {
        //获得所有一级分类
        QueryWrapper<EduSubject> wrapperOne = new QueryWrapper<>();
        wrapperOne.eq("parent_id", "0");

        List<EduSubject> oneSubjects = baseMapper.selectList(wrapperOne);
        //获得所有二级分类
        QueryWrapper<EduSubject> wrapperTwo = new QueryWrapper<>();
        //ne 不等于
        wrapperOne.ne("parent_id", "0");

        List<EduSubject> twoSubjects = baseMapper.selectList(wrapperTwo);

        //封装数据
        List<LevelOneSubject> finalSubjectList = new ArrayList<>();


        //封装一级分类
        // List<EduSubject> --> List<LevelOneSubject> :思路,查询出所有的一级分类list集合遍历,得到每一个一级分类对象

        for (int i = 0; i < oneSubjects.size(); i++) {
            EduSubject eduSubject = oneSubjects.get(i);

            LevelOneSubject levelOneSubject = new LevelOneSubject();
           /* levelOneSubject.setId(eduSubject.getId());
            levelOneSubject.setTitle(eduSubject.getTitle());*/
            BeanUtils.copyProperties(eduSubject, levelOneSubject);//利用spring封装好的对象copy
            finalSubjectList.add(levelOneSubject);

            List<LevelTwoSubject> twoSubjectList = new ArrayList<>();
            for (EduSubject twoSubject : twoSubjects) {
                if (twoSubject.getParentId().equals(levelOneSubject.getId())) {
                    LevelTwoSubject levelTwoSubject = new LevelTwoSubject();
                    BeanUtils.copyProperties(twoSubject, levelTwoSubject);
                    twoSubjectList.add(levelTwoSubject);
                }

            }
            levelOneSubject.setChildren(twoSubjectList);

        }


        return finalSubjectList;
    }
}
