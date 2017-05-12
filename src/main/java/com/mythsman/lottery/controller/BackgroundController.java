package com.mythsman.lottery.controller;

import com.mythsman.lottery.component.ScheduledTask;
import com.mythsman.lottery.dao.RecordDao;
import com.mythsman.lottery.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;


/**
 * Created by myths on 5/9/17.
 */
@Controller
public class BackgroundController {

    @Autowired
    RecordDao recordDao;

    @Autowired
    ScheduledTask scheduledTask;

    @RequestMapping(path = {"background"}, method = {RequestMethod.GET})
    public String background(Model model) {

        return "background";
    }

    @RequestMapping(path = {"background"}, method = {RequestMethod.POST})
    @ResponseBody
    public String change(Model model, HttpServletRequest httpServletRequest) {
        List<String> list=new ArrayList<>();
        int count=0;
        for(int i=0;i<12;i++){
            int key=Integer.parseInt(httpServletRequest.getParameter(String.valueOf(i)));
            while(key>0){
                list.add(Util.getName(i));
                key--;
                count++;
            }
        }
        if(count!=6){
            return "failure";
        }
        Collections.shuffle(list);
        StringBuffer sb=new StringBuffer();
        for(String s:list){
            sb.append(s);
        }
        Date date=new Date();
        String nextPeriod=Util.getNextPeriod(date);
        if(recordDao.select(nextPeriod)==null){
            recordDao.insert(nextPeriod,sb.toString());
        }else {
            recordDao.update(nextPeriod, sb.toString());
        }
        return "success";
    }
}
