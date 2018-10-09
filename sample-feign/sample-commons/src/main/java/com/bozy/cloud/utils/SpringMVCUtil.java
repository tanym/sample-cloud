package com.bozy.cloud.utils;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * Description:
 * Created by tym on 2018/08/23 16:37.
 */
public class SpringMVCUtil {

    /**
     *
     * Description: 统一页面输出方法
     *
     * @param response 响应对象
     * @param content   输出内容
     * @Author ouyangjin
     * @Create Date: 2014年9月3日 下午5:58:00
     */
    public static void printWriter(HttpServletResponse response, Object content){
        PrintWriter printWriter = null;
        try {
            //设置文本类型、编码
            response.setContentType("text/html;charset=utf-8");
            response.setCharacterEncoding("utf-8");
            printWriter = response.getWriter();
            printWriter.print(content.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            if(null != printWriter){
                printWriter.close();
            }
        }
    }
}
