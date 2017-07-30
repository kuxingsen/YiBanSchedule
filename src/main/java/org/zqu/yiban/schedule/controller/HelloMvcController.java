package org.zqu.yiban.schedule.controller;


import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import java.io.IOException;

/**
 * Created by zqh on 2017/5/24.
 */
@Controller
//@RequestMapping(value = "/default")
public class HelloMvcController {
    private String appKey  = "fd5191068d4d1fb7";
    private String appSecret = "014e53c72121ee463c7a5b3d817cfe74";
    private String callbackUrl = "http://111.230.100.184:8081/YibanSchedule/";

    private static final Logger LOGGER = Logger.getLogger(HelloMvcController.class);

    @RequestMapping(value = {"/"})
    public ModelAndView hello() throws IOException{
//        Authorize authorize = new Authorize(appKey,appSecret);
//        String code = request.getParameter("code");
//        if(code==null||code.isEmpty()){
//            String url = authorize.forwardurl(callbackUrl,"test", Authorize.DISPLAY_TAG_T.WEB);
//            response.sendRedirect(url);
//        }else{
//            String information = authorize.querytoken(code,callbackUrl);
//            System.out.println(information);
//        }
        return new ModelAndView("index");
    }

//    @RequestMapping(value = {"/"})
//    public String toIndex(){
//        return "index";
//    }


}
