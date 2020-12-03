package com.atguigu.servicebase.handler;


import com.atguigu.commonutils.ExceptionUtil;
import com.atguigu.commonutils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


@ControllerAdvice //在controller中会执行的通知注解
@Slf4j
public class GlobalExceptionHandler  {
    /**
     * 定义全局异常处理
     */

    @ResponseBody
    @ExceptionHandler(Exception.class)
    public R error(Exception e){
        e.printStackTrace();

        return R.error().message("服务器错误!");
    }

    //特殊异常处理
    @ResponseBody
    @ExceptionHandler(ArithmeticException.class)
    public R error(ArithmeticException e){
        e.printStackTrace();

        return R.error().message("执行了ArithmeticException异常处理!");
    }

    //自定义异常处理
    @ResponseBody
    @ExceptionHandler(GuLiException.class)
    public R error(GuLiException e){
        log.error(ExceptionUtil.getMessage(e));
        e.printStackTrace();

        return R.error().code(e.getCode()).message(e.getMsg());
    }

}
