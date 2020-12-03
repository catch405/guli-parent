package com.atguigu.servicebase.handler;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 自定义异常处理类
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GuLiException extends RuntimeException{

    private Integer code;//状态码

    private String msg;//异常信息

    @Override
    public String toString() {
        return "GuLiException{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                '}';
    }
}
