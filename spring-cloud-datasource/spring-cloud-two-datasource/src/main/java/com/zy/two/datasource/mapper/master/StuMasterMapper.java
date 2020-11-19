package com.zy.two.datasource.mapper.master;


import com.zy.two.datasource.bean.Stu;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface StuMasterMapper {

    List<Stu> getAllStu();
}
