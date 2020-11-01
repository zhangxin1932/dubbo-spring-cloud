package com.zy.producer.security;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebSecurityController {

    @RequestMapping("reflectXSS")
    public ResponseEntity<String> reflectXSS(@RequestParam("name") String name) {
        return ResponseEntity.ok(String.format("%s is a baby.", name));
    }
}
