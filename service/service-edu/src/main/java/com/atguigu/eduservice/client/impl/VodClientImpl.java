package com.atguigu.eduservice.client.impl;

import com.atguigu.commonutils.R;
import com.atguigu.eduservice.client.VodClient;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class VodClientImpl implements VodClient {
    @Override
    public R deleteVodById(String id) {

       return R.error().message("请求超时");
    }

    @Override
    public R deleteMoreVod(List<String> courseIds)
    {
        return R.error().message("请求超时");
    }
}
