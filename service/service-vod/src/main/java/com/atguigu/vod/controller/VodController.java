package com.atguigu.vod.controller;

import com.atguigu.commonutils.R;
import com.atguigu.vod.service.VodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/eduservice/service-vod")
public class VodController {

    @Autowired
    private VodService vodService;


    //上传本地文件到oss
    @PostMapping("/uploadVod")
    public R uploadVod(MultipartFile file){
        //返回上传视频的id值
       String vodId = vodService.uploadVodAliyun(file);
        return R.ok().data("vodId",vodId);
    }

    //根据视频id删除oss视频
    @DeleteMapping("/deleteVod/{id}")
    public R deleteVodById(@PathVariable String id){

        vodService.deleteVodById(id);

        return R.ok();

    }
    @DeleteMapping("/deleteVod")
    public R deleteMoreVod(@RequestParam List<String> courseIds){
        vodService.deleteMoreVod(courseIds);

        return R.ok();

    }

}
