package com.mythsman.lottery.component;

import com.mythsman.lottery.dao.RecordDao;
import com.mythsman.lottery.model.Record;
import com.mythsman.lottery.util.Util;
import org.springframework.beans.factory.InitializingBean;
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
public class ScheduledTask implements InitializingBean {

    @Autowired
    RecordDao recordDao;

    @Autowired
    SharedComponent sharedComponent;

    //@Scheduled(fixedRate = 5000)
    @Scheduled(cron = "0 0 0 * * ?")
    public void reportCurrentTimeCron() throws InterruptedException {
        List<String> periodList = Util.getPeriodsOfToday(new Date());

        Random random = new Random();
        for (String period : periodList) {
            Record record = recordDao.select(period);
            int[] count = new int[12];
            if (record == null) {
                StringBuffer result = new StringBuffer();
                for (int i = 0; i < 6; i++) {
                    int value = random.nextInt(12);
                    while (count[value] == 3) {
                        value = random.nextInt(12);
                    }
                    count[value]++;
                    result.append(Util.getName(value));
                }
                recordDao.insert(period, result.toString());
            }
        }
    }

    @Scheduled(cron = "0 0/5 * * * ?")
    public void updateSharedComponent() throws InterruptedException {
        Date date = new Date();
        sharedComponent.setLastRecord(recordDao.select(Util.getLastPeriod(date)));
        sharedComponent.setNextTime(Util.getNextTime(date));

        Record record = sharedComponent.getLastRecord();

        List<Record> parsedTodayRecords = recordDao.selectByLike(record.getPeriod().substring(0, 6) + "%", record.getPeriod());

        sharedComponent.setTodayRecords(parsedTodayRecords);

        String yesterday = Util.getYesterDayPeriod(date);
        List<Record> yesterdayRecords = recordDao.selectByLike(yesterday + "%", record.getPeriod());
        sharedComponent.setYesterdayRecords(yesterdayRecords);

//        List<Record> last50 = recordDao.selectLast50(record.getPeriod());
//
//
//        int[] maxContinue = new int[12];
//        int[] maxLoss = new int[12];
//        int[] currentLoss = new int[12];
//        for (int i = 0; i < 12; i++) {
//            boolean found=false;
//            int cn0;
//            for(int j=0;j<last50.size();i++){
//            }
//        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        reportCurrentTimeCron();
        updateSharedComponent();
    }
}