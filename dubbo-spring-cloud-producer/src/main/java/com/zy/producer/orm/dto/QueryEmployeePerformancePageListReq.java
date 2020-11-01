package com.zy.producer.orm.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QueryEmployeePerformancePageListReq implements Serializable {
    private static final long serialVersionUID = 4464478453480330414L;
    private Long employeeId;
    private Long departmentId;
    private Long jobId;
    /**
     * 年度
     */
    private String performanceYear;
    /**
     * 季度
     */
    private String quarter;
}
