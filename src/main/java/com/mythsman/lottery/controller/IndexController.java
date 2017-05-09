package com.mythsman.lottery.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by myths on 5/9/17.
 */
@Controller
public class IndexController {

    @RequestMapping(path={"index","/"},method={RequestMethod.GET})
    public String index(){

        return "index";
    }
}
