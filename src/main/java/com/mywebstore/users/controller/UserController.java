package com.mywebstore.users.controller;

import com.mywebstore.users.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(path ="/user")
public class UserController {
    @Autowired
    private UserService userService;


    @GetMapping(path ="/all")
    public int getUsers(){
        return userService.countAllUsers();
    }

   @PostMapping(path ="/login")
    public int executeLogin(@RequestParam("username") String username, @RequestParam("password") String password ){
        if(this.userService.logIn(username,password)){
//            response.setStatus(200);
            return 200;
        }
//            response.setStatus(500);
            return 500;
    }
}
