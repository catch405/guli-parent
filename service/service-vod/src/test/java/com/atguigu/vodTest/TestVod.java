package com.atguigu.vodTest;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadVideoRequest;
import com.aliyun.vod.upload.resp.UploadVideoResponse;
import com.aliyuncs.DefaultAcsClient;

import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoRequest;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoResponse;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;

import java.util.List;

import static com.atguigu.vodTest.InitObject.initVodClient;


/**
 * 阿里云视频上传调用sdk的方法
 */
public class TestVod {

    //1.音视频上传-本地文件上传
    public static void main(String[] argv)  {

        //视频标题(必选)
        String title = "3 - How Does Project Submission Work - upload by sdk";
        //本地文件上传和文件流上传时，文件名称为上传文件绝对路径，如:/User/sample/文件名称.mp4 (必选)
        //文件名必须包含扩展名
        String fileName = "D:\\DPFS\\项目资料\\1-阿里云上传测试视频\\6 - What If I Want to Move Faster.mp4";
        //本地文件上传
        String accessKeyId ="LTAI4GHwWcn5oAAzS4X246Gj" ;
        String accessKeySecret = "Ydsi3lXVd6TQCNCob99IqPKGPkEqEP";
        UploadVideoRequest request = new UploadVideoRequest(accessKeyId, accessKeySecret, title, fileName);
        /* 可指定分片上传时每个分片的大小，默认为1M字节 */
        request.setPartSize(1 * 1024 * 1024L);

        request.setTaskNum(1);
        request.setEnableCheckpoint(false);

        UploadVideoImpl uploader = new UploadVideoImpl();
        UploadVideoResponse response = uploader.uploadVideo(request);
        System.out.print("RequestId=" + response.getRequestId() + "\n");  //请求视频点播服务的请求ID
        if (response.isSuccess()) {
            System.out.print("VideoId=" + response.getVideoId() + "\n");
        } else {
            /* 如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因 */
            System.out.print("VideoId=" + response.getVideoId() + "\n");
            System.out.print("ErrorCode=" + response.getCode() + "\n");
            System.out.print("ErrorMessage=" + response.getMessage() + "\n");
        }
    }


    //根据视频id获得视频播放凭证
    public static void getPlayAuth() throws ClientException{

        //创建初始化对象
        DefaultAcsClient client = initVodClient("LTAI4GHwWcn5oAAzS4X246Gj", "Ydsi3lXVd6TQCNCob99IqPKGPkEqEP");
        //获取视频地址的response和request
        GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
        GetVideoPlayAuthResponse response = new GetVideoPlayAuthResponse();
        //向request设置id
        request.setVideoId("14eb9788224f41eaab82d01a3ef8db3e");

        //调用初始化对象的方法得到凭证
        response = client.getAcsResponse(request);
        System.out.println("playAuth:"+response.getPlayAuth());

    }



    /*根据视频id获得视频播放地址*/
    public static void getPlayUrl() throws Exception{

        //创建初始化对象
        DefaultAcsClient client = initVodClient("LTAI4GHwWcn5oAAzS4X246Gj", "Ydsi3lXVd6TQCNCob99IqPKGPkEqEP");
        //获取视频地址的reponse和request
        GetPlayInfoResponse response = new GetPlayInfoResponse();
        GetPlayInfoRequest request = new GetPlayInfoRequest();

        //向request对像里面设置视频Id'
        request.setVideoId("14eb9788224f41eaab82d01a3ef8db3e");
        //调用初始化对象里面的方法,传递request,获取数据
        response = client.getAcsResponse(request);

        List<GetPlayInfoResponse.PlayInfo> playInfoList = response.getPlayInfoList();
        //获取播放地址
        for (GetPlayInfoResponse.PlayInfo playInfo : playInfoList) {
            System.out.print("PlayInfo.PlayURL = " + playInfo.getPlayURL() + "\n");
        }
        //Base信息
        System.out.print("VideoBase.Title = " + response.getVideoBase().getTitle() + "\n");

    }
}
