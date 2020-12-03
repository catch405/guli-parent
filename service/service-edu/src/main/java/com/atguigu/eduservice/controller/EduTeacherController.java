package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduTeacher;
import com.atguigu.eduservice.entity.vo.TeacherQuery;
import com.atguigu.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 讲师 前端控制器,使用rest风格
 * </p>
 *
 * @author testjava
 * @since 2019-1-17
 */



@CrossOrigin
@RestController
@RequestMapping("/eduservice/edu-teacher")
public class EduTeacherController {

    @Autowired
    private EduTeacherService teacherService;

    /**
     * @return 返回所有讲师信息
     */
    @GetMapping("/findAll")
    public R findAllTeacher() {

        List<EduTeacher> list = teacherService.list(null);
        return R.ok().data("items", list);
    }

    /**
     * @param id
     * @return 根据id逻辑删除讲师, 成功返回true
     */

    @DeleteMapping("/deleteTeacher/{id}")
    public R deleteTeacherById(@PathVariable String id) {

        boolean flag = teacherService.removeById(id);
        if (flag) {
            return R.ok();
        } else {
            return R.error();
        }

    }

    /**
     * 讲师无条件分页查询
     *
     * @param current 当前页
     * @param limit   每页展现的记录数
     * @return 返回总记录数和需要的讲师对象集合数据
     */
    @GetMapping("/pageTeacher/{current}/{limit}")
    public R pageTeacher(@PathVariable Long current, @PathVariable Long limit) {

        Page<EduTeacher> teacherPage = new Page<EduTeacher>(current, limit);

        teacherService.page(teacherPage, null);

        Long total = teacherPage.getTotal();//总记录数

        List<EduTeacher> eduTeachers = teacherPage.getRecords(); //讲师对象集合

        return R.ok().data("total", total).data("rows", eduTeachers);


    }

    /**
     * 讲师按条件分页查询
     *
     * @param current      当前页
     * @param limit        每页展现记录数
     * @param teacherQuery 查询条件
     * @return
     */
    @PostMapping("/pageCondition/{current}/{limit}")
    public R pageCondition(@PathVariable Long current,
                           @PathVariable Long limit,
                           @RequestBody(required = false) TeacherQuery teacherQuery) {

        Page<EduTeacher> teacherPage = new Page<>(current, limit);
        //构建查询条件
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
        String name = teacherQuery.getName();
        Integer level = teacherQuery.getLevel();
        String begin = teacherQuery.getBegin();
        String end = teacherQuery.getEnd();
        if (!StringUtils.isEmpty(name)) {
            wrapper.like("name", name);//第一个参数是数据表中的字段名称

        }
        if (!StringUtils.isEmpty(level)) {
            wrapper.eq("level", level);

        }
        if (!StringUtils.isEmpty(begin)) {
            wrapper.ge("gmt_create", begin);

        }
        if (!StringUtils.isEmpty(end)) {
            wrapper.le("gmt_create", end);
        }
        //排序
        wrapper.orderByDesc("gmt_create");

        teacherService.page(teacherPage, wrapper);
        Long total = teacherPage.getTotal();
        List<EduTeacher> rows = teacherPage.getRecords();
        return R.ok().data("total", total).data("rows", rows);

    }

    /**
     * 添加讲师信息
     *
     * @param eduTeacher
     * @return
     */
    @PutMapping("/addTeacher")
    public R addTeacher(@RequestBody EduTeacher eduTeacher) {

        boolean save = teacherService.save(eduTeacher);
        if (save) {

            return R.ok();

        } else {

            return R.error();

        }

    }

    /**
     * 根据id查询讲师信息
     *
     * @param id
     * @return
     */
    @GetMapping("/getTeacherById/{id}")
    public R findTeacherById(@PathVariable String id) {

        EduTeacher eduTeacher = teacherService.getById(id);
        /*try {
            int i = 10/0;
        } catch (Exception e) {
           throw new GuLiException(20001,"抛出自定义异常");
        }*/

        return R.ok().data("item", eduTeacher);

    }

    /**
     * 根据id修改讲师信息
     *
     * @param
     * @param eduTeacher
     * @return
     */

    @PutMapping("/updateTeacher")
    public R updateById(@RequestBody EduTeacher eduTeacher) {

        boolean b = teacherService.updateById(eduTeacher);
        if (b) {
            return R.ok();
        } else {
            return R.error();
        }

    }


}

