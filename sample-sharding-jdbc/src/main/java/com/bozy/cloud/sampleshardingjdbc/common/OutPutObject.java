package com.bozy.cloud.sampleshardingjdbc.common;

import lombok.Data;

/**
 * Description:
 * Created by tym on 2019/03/12 17:16.
 */
@Data
public class OutPutObject {

    private boolean isSuccess;
    private String returnCode;
    private String returnMsg;

    public OutPutObject(){

    }

    public OutPutObject(boolean isSuccess, String returnMsg){
        this.isSuccess = isSuccess;
        this.returnMsg = returnMsg;
    }

}
