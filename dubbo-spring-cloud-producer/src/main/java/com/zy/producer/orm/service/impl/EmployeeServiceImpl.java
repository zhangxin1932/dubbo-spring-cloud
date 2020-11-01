package com.zy.producer.orm.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zy.common.entity.TbEmployee;
import com.zy.producer.orm.dto.EmployeeDTO;
import com.zy.producer.orm.dto.QueryEmployeePerformancePageListReq;
import com.zy.producer.orm.mapper.EmployeeMapper;
import com.zy.producer.orm.service.IEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EmployeeServiceImpl implements IEmployeeService {
    @Autowired
    private EmployeeMapper employeeMapper;

    /**
     * 这里加上  @Transactional(rollbackFor = Throwable.class) 注解,
     * 配合启动类上的 @EnableTransactionManagement 注解, 即可实现事务回滚
     * @param tbEmployee
     * @return
     */
    @Override
    @Transactional(rollbackFor = Throwable.class)
    public Integer addEmployee(TbEmployee tbEmployee) {
        // 这里模拟事务失败的情况
        // employeeMapper.addEmployee(tbEmployee);
        // int i = 1 / 0;
        return employeeMapper.addEmployee(tbEmployee);
    }

    /**
     * 分页安全性问题
     * PageHelper 方法使用了静态的 ThreadLocal 参数，分页参数和线程是绑定的。
     * 一般只要保证在 PageHelper 方法调用后紧跟 MyBatis 查询方法，这就是安全的。因为 PageHelper 在 finally 代码段中自动清除了 ThreadLocal 存储的对象。
     * 如果代码在进入 Executor 前发生异常，就会导致线程不可用，导致 ThreadLocal 参数被错误的使用。
     *
     * 1.下面这样的代码是不安全的用法
     * 这种情况下由于 param1 存在 null 的情况，就会导致 PageHelper 生产了一个分页参数，但是没有被消费，这个参数就会一直保留在这个线程上。当这个线程再次被使用时，就可能导致不该分页的方法去消费这个分页参数，这就产生了错误的分页。
     * PageHelper.startPage(1, 10);
     * List<Country> list;
     * if(param1 != null){
     *     list = countryMapper.selectIf(param1);
     * } else {
     *     list = new ArrayList<Country>();
     * }
     *
     * 2.正确的写法
     * List<Country> list;
     * if(param1 != null){
     *     PageHelper.startPage(1, 10);
     *     list = countryMapper.selectIf(param1);
     * } else {
     *     list = new ArrayList<Country>();
     * }
     *
     * 3.或者可以手动清理 ThreadLocal 存储的分页参数，如下所示：
     * List<Country> list;
     * if(param1 != null){
     *     PageHelper.startPage(1, 10);
     *     try{
     *         list = countryMapper.selectAll();
     *     } finally {
     *         PageHelper.clearPage();
     *     }
     * } else {
     *     list = new ArrayList<Country>();
     * }
     *
     *
     * @param req
     * @param page
     * @return
     */
    @Override
    public PageInfo<EmployeeDTO> queryEmployeePerformancePageList(QueryEmployeePerformancePageListReq req, Pageable page) {
        PageHelper.startPage(page.getPageNumber(), page.getPageSize());
        List<EmployeeDTO> employeeDTOS = employeeMapper.queryEmployeePerformancePageList(req);
        PageHelper.clearPage();
        return new PageInfo<>(employeeDTOS);
    }
}
