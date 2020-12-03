package com.atguigu.eduservice.client;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.client.impl.VodClientImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Component
@FeignClient(name = "service-vod",fallback = VodClientImpl.class)
public interface VodClient {

    //根据视频id删除oss视频
    @DeleteMapping("/eduservice/service-vod/deleteVod/{id}")
    public R deleteVodById(@PathVariable("id") String id);
    //删除多个视频
    @DeleteMapping("/eduservice/service-vod/deleteVod")
    public R deleteMoreVod(@RequestParam List<String> courseIds);

}
