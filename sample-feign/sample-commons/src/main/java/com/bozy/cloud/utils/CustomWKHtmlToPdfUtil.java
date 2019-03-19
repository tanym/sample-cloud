package com.bozy.cloud.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.UUID;

/**
 * Description: 基于webkit渲染引擎将html内容转为html页面,之后再转为pdf.
 * Created by tym on 2018/11/01 11:04.
 */
public class CustomWKHtmlToPdfUtil {
    // 临时目录的路径
    public static final String TEMP_DIR_PATH = CustomWKHtmlToPdfUtil.class.getResource("/").getPath().substring(1) + "temp/";

    static {
        // 生成临时目录
        new File(TEMP_DIR_PATH).mkdirs();
    }

    public static void main(String[] args) throws Exception {
        String htmlStr = "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\"><html><head><meta http-equiv=\"Content-Type\" content=\"text/html;charset=UTF-8\"></meta><title>HTML转PDF</title></head><body><h1>Hello 世界！</h1></body></html>";

        htmlToPdf(strToHtmlFile(htmlStr), TEMP_DIR_PATH + UUID.randomUUID().toString() + ".pdf");
    }

    /**
     * 将HTML文件内容输出为PDF文件
     *
     * @param htmlFilePath HTML文件路径
     * @param pdfFilePath  PDF文件路径
     */
    public static void htmlToPdf(String htmlFilePath, String pdfFilePath) {
        try {
            Process process = Runtime.getRuntime().exec(getCommand(htmlFilePath, pdfFilePath));
            new Thread(new ClearBufferThread(process.getInputStream())).start();
            new Thread(new ClearBufferThread(process.getErrorStream())).start();
            process.waitFor();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * 将HTML字符串转换为HTML文件
     *
     * @param htmlStr HTML字符串
     * @return HTML文件的绝对路径
     */
    public static String strToHtmlFile(String htmlStr) {
        OutputStream outputStream = null;
        try {
            String htmlFilePath = TEMP_DIR_PATH + UUID.randomUUID().toString() + ".html";
            outputStream = new FileOutputStream(htmlFilePath);
            outputStream.write(htmlStr.getBytes("UTF-8"));
            return htmlFilePath;
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (outputStream != null) {
                    outputStream.close();
                    outputStream = null;
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * 获得HTML转PDF的命令语句
     *
     * @param htmlFilePath HTML文件路径
     * @param pdfFilePath  PDF文件路径
     * @return HTML转PDF的命令语句
     */
    private static String getCommand(String htmlFilePath, String pdfFilePath) {
        String osName = System.getProperty("os.name");
        // Windows
        if (osName.startsWith("Windows")) {
            return String.format("D:/wkhtmltopdf/bin/wkhtmltopdf.exe %s %s", htmlFilePath, pdfFilePath);
        }
        // Linux
        else {
            return String.format("/usr/local/wkhtml2pdf/wkhtmltopdf/bin/wkhtmltopdf %s %s", htmlFilePath, pdfFilePath);
        }
    }
}
