package com.zy.two.datasource.mapper.slave;

import com.zy.two.datasource.bean.Stu;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface StuSlaveMapper {

    List<Stu> getAllStu();
}
