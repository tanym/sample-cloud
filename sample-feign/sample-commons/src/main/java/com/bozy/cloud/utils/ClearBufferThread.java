package com.bozy.cloud.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Description: 清理输入流缓存的线程
 * Created by tym on 2018/11/01 11:07.
 */
public class ClearBufferThread implements Runnable {
    private InputStream inputStream;

    public ClearBufferThread(InputStream inputStream){
        this.inputStream = inputStream;
    }

    public void run() {
        try{
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
            while(br.readLine() != null);
        } catch(Exception e){
            throw new RuntimeException(e);
        }
    }
}
