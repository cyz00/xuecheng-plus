package com.xuecheng.base.exception;

import java.io.Serializable;

/**
 * @author zcy
 * @description 错误响应参数包装
 * @date 2023/6/25
 */
public class RestErrorResponse implements Serializable {

    private String errMessage;

    public RestErrorResponse(String errMessage){
        this.errMessage= errMessage;
    }

    public String getErrMessage() {
        return errMessage;
    }

    public void setErrMessage(String errMessage) {
        this.errMessage = errMessage;
    }
}
