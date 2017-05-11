package com.mythsman.lottery.controller;

import com.mythsman.lottery.component.SharedComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * Created by myths on 5/9/17.
 */
@Controller
public class BackgroundController {



    @RequestMapping(path = {"background"}, method = {RequestMethod.GET})
    public String background(Model model) {

        return "background";
    }

    @RequestMapping(path = {"background"}, method = {RequestMethod.POST})
    @ResponseBody
    public String change(Model model, HttpServletRequest httpServletRequest) {
        for(int i=0;i<12;i++)
            System.out.println(httpServletRequest.getParameter(String.valueOf(i)));

        System.out.println();
        return "background";
    }
}
