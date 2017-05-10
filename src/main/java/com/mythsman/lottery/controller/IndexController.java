package com.mythsman.lottery.controller;

import com.mythsman.lottery.component.SharedComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Date;


/**
 * Created by myths on 5/9/17.
 */
@Controller
public class IndexController {


    @Autowired
    SharedComponent sharedComponent;

    @RequestMapping(path = {"index", "/"}, method = {RequestMethod.GET})
    public String index(Model model) {
        Date date = new Date();
        Long time = date.getTime()/1000*1000;
        model.addAttribute("time", time);
        model.addAttribute("nextTime", sharedComponent.getNextTime());
        model.addAttribute("period", sharedComponent.getLastRecord().getPeriod());
        model.addAttribute("results",sharedComponent.getLastResultList());
        return "index";
    }
}
