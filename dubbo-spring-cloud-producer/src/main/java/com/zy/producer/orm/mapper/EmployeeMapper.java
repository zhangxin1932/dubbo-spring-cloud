package com.zy.producer.orm.mapper;

import com.zy.common.entity.TbEmployee;
import com.zy.producer.orm.dto.EmployeeDTO;
import com.zy.producer.orm.dto.QueryEmployeePerformancePageListReq;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeMapper {
    Integer addEmployee(TbEmployee tbEmployee);

    List<EmployeeDTO> queryEmployeePerformancePageList(@Param("param")QueryEmployeePerformancePageListReq req);
}
