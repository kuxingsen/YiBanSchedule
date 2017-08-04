package org.zqu.yiban.schedule.controller;


import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by zqh on 2017/5/24.
 */
@Controller
//@RequestMapping(value = "/")
public class HelloMvcController {
    private String appKey  = "fd5191068d4d1fb7";
    private String appSecret = "014e53c72121ee463c7a5b3d817cfe74";
    private String callbackUrl = "http://111.230.100.184:8081/";

    private static final Logger LOGGER = Logger.getLogger(HelloMvcController.class);

    private String IP;
    private String information;

    @RequestMapping(value = {"/index.html"})
    public ModelAndView hello(HttpServletRequest request) throws IOException{
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = simpleDateFormat.format(date);
        IP = getIpAdd(request);
        information = "IP地址："+IP+" 时间："+time+";\r\n";
        BufferedWriter bufferedWriter = null;
        try {
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("D://information.txt", true)));
            bufferedWriter.write(information);

        }catch (Exception e){
        }finally {
            try {
                if(bufferedWriter != null){
                    bufferedWriter.close();
                }
            } catch (IOException e) {
            }finally {
                return new ModelAndView("index");
            }

        }

    }

    public  String getIpAdd(HttpServletRequest request) {
        String ip = request.getHeader("X-Real-IP");
        if (ip!= null && !"".equals(ip) && !"unknown".equalsIgnoreCase(ip)) {
            return ip;
        }
        ip = request.getHeader("X-Forwarded-For");
        if (ip!= null && !"".equals(ip)  && !"unknown".equalsIgnoreCase(ip)) {
            // 多次反向代理后会有多个IP值，第一个为真实IP。
            int index = ip.indexOf(',');
            if (index != -1) {
                return ip.substring(0, index);
            } else {
                return ip;
            }
        } else {
            return request.getRemoteAddr();
        }
    }

//    @RequestMapping(value = {"/"})
//    public String toIndex(){
//        return "index";
//    }


}
