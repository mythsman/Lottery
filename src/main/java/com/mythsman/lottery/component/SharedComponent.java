package com.mythsman.lottery.component;

import com.mythsman.lottery.model.Record;
import com.mythsman.lottery.model.StatisticItem;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by myths on 5/11/17.
 */
@Component
public class SharedComponent {
    private Long nextTime;//下一次开奖的时间，精确到秒
    private Record lastRecord;//上一次开奖的记录
    private List<Record> todayRecords;//今天所有的开奖记录
    private List<Record> yesterdayRecords;//昨天所有的开奖记录
    private List<StatisticItem> statistics;//统计结果

    public Long getNextTime() {
        return nextTime;
    }

    public Record getLastRecord() {
        return lastRecord;
    }

    public List<Record> getTodayRecords() {
        return todayRecords;
    }

    public List<Record> getYesterdayRecords() {
        return yesterdayRecords;
    }


    public void setYesterdayRecords(List<Record> yesterdayRecords) {
        this.yesterdayRecords = yesterdayRecords;
    }


    public void setNextTime(Long nextTime) {
        this.nextTime = nextTime / 1000 * 1000;
    }


    public void setLastRecord(Record lastRecord) {
        this.lastRecord = lastRecord;
    }


    public void setTodayRecords(List<Record> todayRecords) {
        this.todayRecords = todayRecords;
    }

    public List<StatisticItem> getStatistics() {
        return statistics;
    }

    public void setStatistics(List<StatisticItem> statistics) {
        this.statistics = statistics;
    }
}
