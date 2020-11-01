package com.zy.consumer.orm.repository;

import com.zy.common.entity.TbEmployee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<TbEmployee, Long> {
}
