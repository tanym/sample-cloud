package com.bozy.cloud.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Description:
 * Created by tym on 2018/09/26 17:23.
 */
@Controller
public class HelloController {

    @RequestMapping("/")
    public String index(ModelMap map) {
        map.put("welcome", "welcome to spring cloud web ");
        return "index";
    }

}
