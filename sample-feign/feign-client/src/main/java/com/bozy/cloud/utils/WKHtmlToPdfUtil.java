package com.bozy.cloud.utils;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Description:
 * Created by tym on 2018/11/01 14:46.
 */
public class WKHtmlToPdfUtil {

    private static final Logger log = Logger.getLogger(WKHtmlToPdfUtil.class);

    private static String  DEFAULTCHART="UTF-8";

    /**
     * 操作系统类型:windows,linux
     */
    public static String systemOs;

    /**
     * wkhtmlToPdf工具安装的路径
     */
    public static String wkhtmlToPdfPath;

    @Value("${wk.htmlToPdfPathWin}")
    private static String wkhtmlToPdfPathWin;
    @Value("${wk.htmlToPdfPathLin}")
    private static String wkhtmlToPdfPathLin;
    @Value("${wk.ip}")
    private static String wkhtmlToPdfIp;
    @Value("${wk.username}")
    private static String wkhtmlToPdfUsername;
    @Value("${wk.pass}")
    private static String wkhtmlToPdfPass;

    /**
     * 根据服务器系统设置pdf文件路径
     */
    static {
        systemOs = System.getProperty("os.name");
        if (systemOs.toLowerCase().startsWith("win")) {
            wkhtmlToPdfPath = wkhtmlToPdfPathWin;
        } else {
            wkhtmlToPdfPath = wkhtmlToPdfPathLin;
        }
    }

    /**
     *
     * Description: 组装数据的方法
     *
     * @return
     * @Author tfp
     * @Create Date: 2018年10月31日 下午5:44:30
     */
    private static Map<String,Object> dataMap(){

        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("ZJHKZH", "271003********279975");
        paramMap.put("KYYE", "79244.95");
        paramMap.put("LXFS", "配置web.xml中LXFS属性，例如(张小凡，123,4567,8909)");
        paramMap.put("KHWD", "123");
        paramMap.put("CSKSRQ", "2016年10月31日00时00分");
        paramMap.put("KSRQ", "2017-03-14");
        paramMap.put("YE","94444.95");
        paramMap.put("KHZH","271**********07279975");
        paramMap.put("AH", "(2015)****字第0***0号");
        paramMap.put("CKH", "(2017)法YH****9控字第*号");
        paramMap.put("YDJAH", "(2015)***执字第00020号");
        paramMap.put("KZCS", "01");
        paramMap.put("XM", "張三豐");
        paramMap.put("FYMC", "****人民法院");
        paramMap.put("JSRQ", "2017-06-14");
        paramMap.put("KZZT", "1");
        paramMap.put("SE", "100");
        paramMap.put("LCZH", "987234234");
        paramMap.put("DATE", "2017年03月24日09时39分");
        paramMap.put("CKWH", "(2015)*****字第0**20-1**0号裁定书");
        paramMap.put("SKSE", "100");
        paramMap.put("CSJSRQ", "2016年10月31日 00时00分");
        return paramMap;
    }

    /**
     * 填充模板
     * @throws Exception
     */
    public static void fillTemplateAndConvertToPdf(
            String templatePath,
            String templateName,
            String htmlPathAndName,
            String pdfPathAndName){

        Configuration configuration = new Configuration();

        try{

            configuration.setDirectoryForTemplateLoading(new File(templatePath));
            //这个一定要设置，不然在生成的页面中 会乱码
            configuration.setDefaultEncoding(DEFAULTCHART);
            //获取或创建一个模版。
            Template template = configuration.getTemplate(templateName);

            //将通过模板生成的html落地
            File outHtmFile = new File(htmlPathAndName);

            Writer out = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(outHtmFile)));
            template.process(dataMap(), out);

            out.flush();
            out.close();
        }catch(Exception e){
            e.printStackTrace();
        }

        htmlToPdf(htmlPathAndName, pdfPathAndName);

    }

    public static boolean htmlToPdf(String htmlFilePath, String pdfFilePath){

        StringBuilder sb = new StringBuilder();
        sb.append(wkhtmlToPdfPath);
        sb.append(" ");
        sb.append(htmlFilePath);
        sb.append(" ");
        sb.append(pdfFilePath);

        boolean result=true;

        try {
            Process process = Runtime.getRuntime().exec(sb.toString());

            new Thread(new ClearBufferThread(process.getInputStream())).start();
            new Thread(new ClearBufferThread(process.getErrorStream())).start();

            process.waitFor();

        } catch (Exception e) {
            result=false;
            e.printStackTrace();
        }

        return result;
    }

    /***
     * Description: 远程执行命令行转化pdf
     * @Author tym
     * @Create Date: 2018/11/1/0001 下午 6:06
     * @param cmd
     * @return
     */
    public static String remoteHtmlToPdf(String cmd){
        Connection conn = null;
        boolean flag = false;
        String result = "";
        try{
            conn = new Connection(wkhtmlToPdfIp);
            conn.connect();//连接
            flag=conn.authenticateWithPassword(wkhtmlToPdfUsername, wkhtmlToPdfPass);//认证
            if(flag){
                log.info("=========登录成功========="+conn);
                Session session= conn.openSession();//打开一个会话
                session.execCommand(cmd);//执行命令
                result=processStdout(session.getStdout(),DEFAULTCHART);
                //如果为得到标准输出为空，说明脚本执行出错了
                if(StringUtils.isBlank(result)){
                    log.info("得到标准输出为空,链接conn:"+conn+",执行的命令："+cmd);
                    result=processStdout(session.getStderr(),DEFAULTCHART);
                }else{
                    log.info("执行命令成功,链接conn:"+conn+",执行的命令："+cmd);
                }
                conn.close();
                session.close();
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }

        return result;
    }

    /**
     * 解析脚本执行返回的结果集
     * @param in 输入流对象
     * @param charset 编码
     * @return
     *       以纯文本的格式返回
     */
    private static String processStdout(InputStream in, String charset){
        InputStream  stdout = new StreamGobbler(in);
        StringBuffer buffer = new StringBuffer();
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(stdout,charset));
            String line=null;
            while((line=br.readLine()) != null){
                buffer.append(line+"\n");
            }
        } catch (UnsupportedEncodingException e) {
            log.error("解析脚本出错："+e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            log.error("解析脚本出错："+e.getMessage());
            e.printStackTrace();
        }
        return buffer.toString();
    }

    /**
     * 清理输入流缓存的线程
     * @Description
     * @author Administrator
     * @date 2018年10月31日 下午2:44:26
     */
    private static class ClearBufferThread implements Runnable{

        private InputStream inputStream;

        public ClearBufferThread(InputStream inputStream){
            this.inputStream = inputStream;
        }

        @Override
        public void run() {
            try{
                BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
                while(br.readLine() != null);
            } catch(Exception e){
                throw new RuntimeException(e);
            }
        }
    }


    public static void main(String[] args) {

        String templatePath="D:/data/data/freemarker/pdftest/pdf-demo2/wkhtml/";
        String templateName="201contr.ftl";
        String htmlPathAndName="D:/data/data/freemarker/pdftest/pdf-demo2/wkhtml/201contr_2.html";
        String pdfPathAndName="D:/data/data/freemarker/pdftest/pdf-demo2/wkhtml/201contr_2.pdf";
        fillTemplateAndConvertToPdf(templatePath, templateName, htmlPathAndName, pdfPathAndName);

        String cmd = "";
        remoteHtmlToPdf(cmd);

    }
}
