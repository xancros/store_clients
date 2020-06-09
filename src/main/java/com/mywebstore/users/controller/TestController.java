package com.mywebstore.users.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/")
    public String hello(){
        return "<h1>HELLO Stranger!!!</h1>";
    }

    @GetMapping("/admin")
    public String helloAdmin(){
        return "<h1>HELLO ADMIN!!!!</h1>";
    }

    @GetMapping("/other")
    public String helloOther() {
        return "<h1>HELLO User!!!!</h1>";
    }
}
