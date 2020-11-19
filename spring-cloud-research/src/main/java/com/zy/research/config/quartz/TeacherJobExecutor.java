package com.zy.research.config.quartz;

import com.zy.research.service.TeacherServiceImpl;

import javax.annotation.Resource;

public class TeacherJobExecutor {

    @Resource
    private TeacherServiceImpl teacherService;

    public void execute() {
        teacherService.teach();
    }

}
