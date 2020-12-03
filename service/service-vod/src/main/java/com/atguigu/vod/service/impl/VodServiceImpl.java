package com.atguigu.vod.service.impl;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.atguigu.servicebase.handler.GuLiException;
import com.atguigu.vod.service.VodService;
import com.atguigu.vod.utils.InitObject;
import com.atguigu.vod.utils.Upload;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

//本地文件上传到o阿里云ss
@Service
public class VodServiceImpl implements VodService {

    @Override
    public String uploadVodAliyun(MultipartFile file) {

        InputStream inputStream;

        String fileName = file.getOriginalFilename();//上传文件的原始名称
        String title = fileName.substring(0, fileName.lastIndexOf(".")); //上传之后显示名称
        try {
          inputStream= file.getInputStream();//上传文件的输入流

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        UploadStreamRequest request = new UploadStreamRequest(Upload.ACCESS_KEY_ID, Upload.ACCESS_KEY_SECRET, title, fileName, inputStream);

        UploadVideoImpl upload = new UploadVideoImpl();

        UploadStreamResponse response = upload.uploadStream(request);

        String vodId = "";
        if (response.isSuccess()) {
            vodId = response.getVideoId();
            System.out.print("VideoId=" + response.getVideoId() + "\n");
        } else {
            /* 如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因 */

            System.out.print("ErrorCode=" + response.getCode() + "\n");
            System.out.print("ErrorMessage=" + response.getMessage() + "\n");
        }


        return vodId;


    }

    /**
     * 根据id删除阿里云oss视频
     * @param id
     */
    @Override
    public void deleteVodById(String id) {

        //初始化对象
        DefaultAcsClient acsClient = InitObject.initVodClient(Upload.ACCESS_KEY_ID,Upload.ACCESS_KEY_SECRET);
        //创建删除视频request对象
        DeleteVideoRequest request = new DeleteVideoRequest();
        //向request设置id
        request.setVideoIds(id);

        //调用初始化对象的方法删除视频

        try {
            acsClient.getAcsResponse(request);
        } catch (ClientException e) {
            e.printStackTrace();
            throw new GuLiException(20001,"删除视频失败");
        }
    }


    //删除多个视频
    @Override
    public void deleteMoreVod(List<String> courseIds) {

        DefaultAcsClient acsClient = InitObject.initVodClient(Upload.ACCESS_KEY_ID,Upload.ACCESS_KEY_SECRET);
        DeleteVideoRequest request = new DeleteVideoRequest();
        String str = StringUtils.join(courseIds.toArray(),",");
        request.setVideoIds(str);

        try {
            acsClient.getAcsResponse(request);
        } catch (ClientException e) {
            e.printStackTrace();
            throw new GuLiException(20001,"删除失败");
        }


    }
}
