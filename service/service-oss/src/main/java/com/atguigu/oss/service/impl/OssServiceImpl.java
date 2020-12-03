package com.atguigu.oss.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.atguigu.oss.service.OssService;
import com.atguigu.oss.util.ConstantPropertiesUtil;
import lombok.val;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Service
public class OssServiceImpl implements OssService {
    @Override
    public String uploadFileAvatar(MultipartFile file) {
        // Endpoint以杭州为例，其它Region请按实际情况填写。
        String endpoint = ConstantPropertiesUtil.END_POINT;
        // 云账号AccessKey有所有API访问权限，建议遵循阿里云安全最佳实践，创建并使用RAM子账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建。
        String accessKeyId = ConstantPropertiesUtil.ACCESS_KEY_ID;
        String accessKeySecret = ConstantPropertiesUtil.ACCESS_KEY_SECRET;
        String bucketName = ConstantPropertiesUtil.BUCKET_NAME;

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);


        // 上传文件流。
        InputStream inputStream = null;
        try {
            inputStream = file.getInputStream();

            String filename = file.getOriginalFilename();
            //保证文件名的唯一性
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            filename = uuid+filename;
            //在oos中,我们规定上传的文件按日期进行分类
            String dataPath = new DateTime().toString("yyyy/MM/dd");//引入日期依赖中的方法

            filename = dataPath+"/"+filename;


            ossClient.putObject(bucketName, filename, inputStream);

            // 关闭OSSClient。
            ossClient.shutdown();

            //把上传之后的文件路径返回(oss里面的文件路径格式)
            //https://edu-11-21.oss-cn-shenzhen.aliyuncs.com/01.jpg
            String url = "https://" + bucketName + "." + endpoint + "/" + filename;

            return url;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}