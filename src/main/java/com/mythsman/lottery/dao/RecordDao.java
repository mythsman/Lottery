package com.mythsman.lottery.dao;

import com.mythsman.lottery.model.Record;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by myths on 5/10/17.
 */
@Mapper
@Service
public interface RecordDao {

    @Insert({"insert into `record` (period,result) values(#{period},#{result} )"})
    void insert(@Param("period") String period, @Param("result") String result);

    @Update({"update `record` set result = #{result} where period=#{period} "})
    void update(@Param("period") String period, @Param("result") String result);

    @Select({"select * from `record` where period=#{period}"})
    Record select(@Param("period") String period);

    @Select({"select * from `record` where period like #{like} and period <=#{now} order by period desc"})
    List<Record> selectByLike(@Param("like") String like,@Param("now") String now);

    @Select({"select * from `record` where period <= #{period} order by period desc limit 50"})
    List<Record> selectLast50(@Param("period")String period);

}
