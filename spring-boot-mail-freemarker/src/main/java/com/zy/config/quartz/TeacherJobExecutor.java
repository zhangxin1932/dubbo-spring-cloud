package com.zy.config.quartz;

import com.zy.service.impl.TeacherServiceImpl;

import javax.annotation.Resource;

public class TeacherJobExecutor {

    @Resource
    private TeacherServiceImpl teacherService;

    public void execute() {
        teacherService.teach();
    }

}
