package com.zy.producer.orm.controller;

import com.github.pagehelper.PageInfo;
import com.zy.common.entity.TbEmployee;
import com.zy.producer.orm.dto.EmployeeDTO;
import com.zy.producer.orm.dto.QueryEmployeePerformancePageListReq;
import com.zy.producer.orm.service.IEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * https://www.jianshu.com/p/8849866f1a10
 * mybatis插入一张表同时插入关联表
 *
 * 1.Mybatis 分页插件整合
 * https://www.cnblogs.com/kingsonfu/p/10389021.html
 *
 * 2.事务整合
 * @Transactional(rollbackFor = Throwable.class)  & @EnableTransactionManagement
 *
 * 3.SQL语句
 * 3.1 动态获取列的复杂 sql
 * https://www.cnblogs.com/huanghuanghui/p/9997041.html
 *
 */
@RestController
@RequestMapping("/employee/")
public class EmployeeController {
    @Autowired
    private IEmployeeService employeeService;

    @RequestMapping("queryEmployeePerformancePageList")
    public PageInfo<EmployeeDTO> queryEmployeePerformancePageList(@RequestBody(required = false) QueryEmployeePerformancePageListReq req, Pageable page) {
        return employeeService.queryEmployeePerformancePageList(req, page);
    }

    @RequestMapping("addEmployee")
    public Integer addEmployee(@RequestBody TbEmployee tbEmployee) {
        tbEmployee.setUpdateTime(new Date());
        return employeeService.addEmployee(tbEmployee);
    }

}
