package com.atguigu.eduservice.entity.subject;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

//一级分类列表
@Data
public class LevelOneSubject {


    private String id;

    private String title;

    private List<LevelTwoSubject> children = new ArrayList<>();
}
