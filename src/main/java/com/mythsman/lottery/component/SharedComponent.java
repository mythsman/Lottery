package com.mythsman.lottery.component;

import com.mythsman.lottery.model.Record;
import com.mythsman.lottery.util.Util;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by myths on 5/11/17.
 */
@Component
public class SharedComponent {
    private Long nextTime;
    private Record lastRecord;
    private List<String> lastResultList=new ArrayList<>();

    public Long getNextTime() {
        return nextTime;
    }

    public void setNextTime(Long nextTime) {
        this.nextTime = nextTime/1000*1000;
    }

    public Record getLastRecord() {
        return lastRecord;
    }

    public void setLastRecord(Record lastRecord) {
        this.lastRecord = lastRecord;
        lastResultList= Util.parseResult(lastRecord.getResult());
    }

    public List<String> getLastResultList() {
        return lastResultList;
    }
}
