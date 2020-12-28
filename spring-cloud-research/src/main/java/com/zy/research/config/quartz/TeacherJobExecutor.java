package com.zy.research.config.quartz;

import com.zy.research.service.TeacherServiceImpl;

import javax.annotation.Resource;
import java.util.concurrent.atomic.LongAdder;

public class TeacherJobExecutor {

    @Resource
    private TeacherServiceImpl teacherService;

    private final LongAdder longAdder = new LongAdder();

    public void execute() {
        longAdder.increment();
        teacherService.teach(longAdder.longValue());
    }

}
