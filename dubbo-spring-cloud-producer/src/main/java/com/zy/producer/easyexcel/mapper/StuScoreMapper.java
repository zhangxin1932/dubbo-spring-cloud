package com.zy.producer.easyexcel.mapper;

import com.zy.producer.easyexcel.entity.StuScoreExcel;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface StuScoreMapper {
    // mybatis,尽量别直接调用多次insert,自己写一个mapper里面新增一个方法batchInsert,所有数据一次性插入
    void saveStuScore(List<StuScoreExcel> list) throws Exception;

    List<StuScoreExcel> getAllScore() throws Exception;
}
