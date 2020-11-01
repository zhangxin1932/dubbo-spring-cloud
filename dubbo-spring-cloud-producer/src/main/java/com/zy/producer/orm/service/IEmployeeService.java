package com.zy.producer.orm.service;

import com.github.pagehelper.PageInfo;
import com.zy.common.entity.TbEmployee;
import com.zy.producer.orm.dto.EmployeeDTO;
import com.zy.producer.orm.dto.QueryEmployeePerformancePageListReq;
import org.springframework.data.domain.Pageable;

public interface IEmployeeService {
    Integer addEmployee(TbEmployee tbEmployee);

    PageInfo<EmployeeDTO> queryEmployeePerformancePageList(QueryEmployeePerformancePageListReq req, Pageable page);
}
