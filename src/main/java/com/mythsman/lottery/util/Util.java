package com.mythsman.lottery.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by myths on 5/10/17.
 */
public class Util {
    private static SimpleDateFormat yearFormat=new SimpleDateFormat("yy");
    private static SimpleDateFormat monthsFormat=new SimpleDateFormat("MM");
    private static SimpleDateFormat dayFormat=new SimpleDateFormat("dd");
    private static SimpleDateFormat hourFormat=new SimpleDateFormat("HH");
    private static SimpleDateFormat minuteFormat=new SimpleDateFormat("mm");

    public static List<String> getPeriodsOfToday(Date date){
        String year=yearFormat.format(date);
        String months=monthsFormat.format(date);
        String day=dayFormat.format(date);
        List<String> list=new ArrayList<>();
        for(int i=1;i<=156;i++){
            list.add(year+months+day+String.format("%03d",i));
        }
        return list;
    }

    public static String getLastPeriod(Date date){
        String year=yearFormat.format(date);
        String months=monthsFormat.format(date);
        String day=dayFormat.format(date);
        String hour=hourFormat.format(date);
        if(Integer.parseInt(hour)<10||Integer.parseInt(hour)>=23){
            Date before=new Date(date.getTime()-1000L*60L*60L*12L);
            String beforeYear=yearFormat.format(before);
            String beforeMonths=monthsFormat.format(before);
            String beforeDay=dayFormat.format(before);
            return beforeYear+beforeMonths+beforeDay+"156";
        }

        Date before=new Date((date.getTime()/1000/300)*1000*300);
        int beforeHour=Integer.parseInt(hourFormat.format(before));
        int beforeMinute=Integer.parseInt(minuteFormat.format(before));
        int period=(beforeHour-10)*12+beforeMinute/5+1;
        return year+months+day+String.format("%03d",period);
    }
    public static String getNextPeriod(Date date){
        Long nextTime=Util.getNextTime(date)+1000;
        return Util.getLastPeriod(new Date(nextTime));

    }

    public static Long getNextTime(Date date){
        String hour=hourFormat.format(date);
        if(Integer.parseInt(hour)<10||Integer.parseInt(hour)>=23){
            Date next=new Date(date.getTime()+1000L*60L*60L*12L);
            next.setHours(10);
            next.setMinutes(0);
            next.setSeconds(0);
            return next.getTime();
        }else{
            Date next=new Date((date.getTime()/1000/300+1)*1000*300);
            return next.getTime();
        }
    }

    public static String getName(int t){
        switch(t){
            case 0:
                return "鼠";
            case 1:
                return "牛";
            case 2:
                return "虎";
            case 3:
                return "兔";
            case 4:
                return "龙";
            case 5:
                return "蛇";
            case 6:
                return "马";
            case 7:
                return "羊";
            case 8:
                return "猴";
            case 9:
                return "鸡";
            case 10:
                return "狗";
            case 11:
                return "猪";
            default:
                return "错误";
        }
    }


    public static String getYesterDayPeriod(Date date){
        return new SimpleDateFormat("yyMMdd").format(new Date(date.getTime()-1000L*60L*60L*24));
    }

}
