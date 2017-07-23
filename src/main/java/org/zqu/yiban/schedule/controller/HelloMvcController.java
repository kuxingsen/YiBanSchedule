package org.zqu.yiban.schedule.controller;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by zqh on 2017/5/24.
 */
@Controller
@RequestMapping(value = "/default")
public class HelloMvcController {
    private static final Logger LOGGER = Logger.getLogger(HelloMvcController.class);

    @RequestMapping(value = "/index")
    public String helloMvc() {
        return "index";
    }

    @RequestMapping(value = "/index2")
    public String helloMvc2() {
        return "index2";
    }

}
