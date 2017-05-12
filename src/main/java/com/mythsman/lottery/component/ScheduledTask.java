package com.mythsman.lottery.component;

import com.mythsman.lottery.dao.RecordDao;
import com.mythsman.lottery.model.Record;
import com.mythsman.lottery.model.StatisticItem;
import com.mythsman.lottery.util.Util;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.*;

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


        List<StatisticItem> statistics=new ArrayList<>();
        List<Record> last50 = recordDao.selectLast50(record.getPeriod());
        Collections.reverse(last50);
        for(int i=0;i<12;i++){
            String name=Util.getName(i);
            StatisticItem statisticItem=new StatisticItem();
            statisticItem.setName(name);
            int total=0;
            for(Record record1 :last50){
                total+=countChar(record1.getResult(),name.charAt(0));
            }
            statisticItem.setTotal(total);

            int maxContinue=0;
            int currentContinue=0;
            for(Record record1 :last50){
                int cnt=countChar(record1.getResult(),name.charAt(0));
                if(cnt>0){
                    currentContinue+=cnt;
                }else{
                    maxContinue=Math.max(maxContinue,currentContinue);
                    currentContinue=0;
                }
            }
            maxContinue=Math.max(maxContinue,currentContinue);
            statisticItem.setMaxContinue(maxContinue);

            int maxLoss=0;
            int currentLoss=0;

            for(Record record1 :last50){
                int cnt=countChar(record1.getResult(),name.charAt(0));
                if(cnt==0){
                    currentLoss++;
                    maxLoss=Math.max(currentLoss,maxLoss);
                }else{
                    currentLoss=0;
                }
            }
            maxLoss=Math.max(currentLoss,maxLoss);
            statisticItem.setMaxLoss(maxLoss);
            statisticItem.setCurrentLoss(currentLoss);
            statistics.add(statisticItem);
        }
        sharedComponent.setStatistics(statistics);
    }

    private int countChar(String s,char c){
        int count=0;
        for(int i=0;i<s.length();i++){
            count+=(s.charAt(i)==c)?1:0;
        }
        return count;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        reportCurrentTimeCron();
        updateSharedComponent();
    }
}