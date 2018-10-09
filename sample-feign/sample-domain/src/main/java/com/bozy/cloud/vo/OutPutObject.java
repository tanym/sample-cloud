package com.bozy.cloud.vo;

import com.alibaba.fastjson.JSON;

/**
 * Description:
 * Created by tym on 2018/08/23 16:39.
 */
public class OutPutObject {

    public OutPutObject(){
    }

    /**
     * @param returnMsg	返回消息描述
     */
    public OutPutObject(String returnMsg){
        this.returnMsg = returnMsg;
    }

    /**
     * @param isSuccess	是否处理成功
     * @param returnMsg	返回消息描述
     */
    public OutPutObject(boolean isSuccess, String returnMsg){
        this(returnMsg);
        this.isSuccess = isSuccess;
    }

    /**
     * @param returnCode	返回状态码,自定义
     * @param returnMsg	返回消息描述
     */
    public OutPutObject(String returnCode, String returnMsg){
        this(returnMsg);
        this.returnCode = returnCode;
    }

    /**
     * @param isSuccess	是否处理成功
     * @param returnCode	返回状态码,自定义
     * @param returnMsg	返回消息描述
     */
    public OutPutObject(boolean isSuccess, String returnCode, String returnMsg){
        this(returnCode, returnMsg);
        this.isSuccess = isSuccess;
    }

    /**
     * 是否处理成功
     */
    private boolean isSuccess;
    /**
     * 返回状态码,自定义
     */
    private String returnCode;
    /**
     * 返回消息描述
     */
    private String returnMsg;

    public boolean getIsSuccess() {
        return isSuccess;
    }
    public void setIsSuccess(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }
    public String getReturnCode() {
        return returnCode;
    }
    public void setReturnCode(String returnCode) {
        this.returnCode = returnCode;
    }
    public String getReturnMsg() {
        return returnMsg;
    }
    public void setReturnMsg(String returnMsg) {
        this.returnMsg = returnMsg;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}