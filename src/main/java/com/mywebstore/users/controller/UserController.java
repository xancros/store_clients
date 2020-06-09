package com.mywebstore.users.controller;

import com.mywebstore.users.model.CustomResponseObject;
import com.mywebstore.users.model.UserLogin;
import com.mywebstore.users.model.UserModel;
import com.mywebstore.users.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path ="/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping(path="/")
    public String hello(){
        return "HELLO!!";
    }
    @GetMapping(path = "/all")
    public int countUsers(){

        return userService.countAllUsers();
    }

   @PostMapping(path ="/login")
    public CustomResponseObject login(@RequestBody UserLogin userLogin){
        if(this.userService.logIn(userLogin.getUsername(),userLogin.getPassword())){
            return new CustomResponseObject("OK",HttpStatus.FOUND);
        }
            return new CustomResponseObject("NOT FOUND",HttpStatus.NO_CONTENT);
    }

    @PostMapping(path="/register")
    public CustomResponseObject register(@RequestBody UserModel userModel){
        if(this.userService.createUser(userModel)){
            return new CustomResponseObject("CREATED",HttpStatus.CREATED);
        }
            return new CustomResponseObject("ERROR",HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/deleteUser")
    public CustomResponseObject removeUser(@RequestBody UserModel userModel){
        if(this.userService.removeUser(userModel)){
            return new CustomResponseObject("CREATED",HttpStatus.I_AM_A_TEAPOT);
        }
        return new CustomResponseObject("ERROR",HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/modify")
    public CustomResponseObject modifyUser(@RequestBody UserModel userModel){
        if(this.userService.modifyUser(userModel)){
            return new CustomResponseObject("CHANGED",HttpStatus.ACCEPTED);
        }
        return new CustomResponseObject("ERROR",HttpStatus.NOT_MODIFIED);
    }


}
