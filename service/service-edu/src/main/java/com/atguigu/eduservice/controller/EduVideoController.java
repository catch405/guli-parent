package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.client.VodClient;
import com.atguigu.eduservice.entity.EduVideo;
import com.atguigu.eduservice.service.EduVideoService;
import com.atguigu.servicebase.handler.GuLiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-11-24
 */

@CrossOrigin
@RestController
@RequestMapping("/eduservice/edu-video")
public class EduVideoController {

    @Autowired
    private EduVideoService eduVideoService;


    @Autowired  //注入服务接口
    private VodClient vodClient;


    //增加课时
    @PutMapping("/addVideo")
    public R addVideo(@RequestBody EduVideo eduVideo) {

        eduVideoService.save(eduVideo);

        return R.ok();

    }

    //删除课时
    //TODO 课时里面有视频,之后请完善删除
    @DeleteMapping("/deleteVideo/{id}")
    public R deleteVideo(@PathVariable String id) {

        //根据课时id查询出视频id
        EduVideo video = eduVideoService.getById(id);
        String vodId = video.getVideoSourceId();

        if (!StringUtils.isEmpty(vodId)){

            //调用服务
            R r = vodClient.deleteVodById(vodId);
            if (r.getCode()==20001){
                throw new GuLiException(20001,"请求超时");
            }
        }

        eduVideoService.removeById(id);


        return R.ok();
    }

    //根据id查询课时
    @GetMapping("/getVideo/{id}")
    public R getVideo(@PathVariable String id) {

        EduVideo eduVideo = eduVideoService.getById(id);

        return R.ok().data("eduVideo", eduVideo);
    }

    //修改课时
    @PutMapping("/updateVideo")
    public R updateVideo(@RequestBody EduVideo eduVideo) {

        eduVideoService.updateById(eduVideo);

        return R.ok();
    }


}

