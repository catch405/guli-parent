package com.atguigu.vod.service;


import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface VodService {
    String uploadVodAliyun(MultipartFile file);

    void deleteVodById(String id);

    void deleteMoreVod(List<String> courseIds);
}
