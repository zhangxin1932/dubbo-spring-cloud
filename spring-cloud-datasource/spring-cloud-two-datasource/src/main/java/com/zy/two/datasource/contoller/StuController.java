package com.zy.two.datasource.contoller;

import com.alibaba.fastjson.JSON;
import com.zy.two.datasource.bean.Stu;
import com.zy.two.datasource.service.StuServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class StuController {

    @Autowired
    private StuServiceImpl stuService;

    @PostMapping("/map")
    public String map(@RequestBody String params){
        Map<String, String> map = JSON.parseObject(params, Map.class);
        Set<String> keys = map.keySet();
        for (String key : keys){
            System.out.println(key + "=============" + map.get(key));
        }
        return params;
    }

    @PostMapping("/twoDataSource")
    public List<Stu> twoDataSource(){
        return stuService.hello();
    }

}
