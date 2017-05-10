package com.mythsman.lottery.component;

import com.mythsman.lottery.dao.RecordDao;
import com.mythsman.lottery.model.Record;
import com.mythsman.lottery.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * Created by myths on 5/10/17.
 */
@Component
public class ScheduledTask {

    @Autowired
    RecordDao recordDao;

    @Autowired
    SharedComponent sharedComponent;

    //@Scheduled(fixedRate = 5000)
    @Scheduled(cron = "0 0 0 * * ?")
    public void reportCurrentTimeCron() throws InterruptedException {
        List<String> periodList= Util.getPeriodsOfToday(new Date());

        Random random=new Random();
        for(String period:periodList){
            Record record=recordDao.select(period);
            int[] count=new int[12];
            if(record==null){
                StringBuffer result=new StringBuffer();
                for(int i=0;i<6;i++){
                    int value=random.nextInt(12);
                    while(count[value]==3){
                        value=random.nextInt(12);
                    }
                    count[value]++;
                    result.append(String.format("%02d",value));
                }
                recordDao.insert(period,result.toString());
            }
        }
    }
    @Scheduled(cron = "0 0/5 * * * ?")
    public void updateSharedComponent() throws InterruptedException {
        Date date=new Date();
        sharedComponent.setLastRecord(recordDao.select(Util.getLastPeriod(date)));
        sharedComponent.setNextTime(Util.getNextTime(date));
    }

}