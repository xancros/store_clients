package com.mywebstore.users.model;

import org.springframework.http.HttpStatus;

public class CustomResponseObject {

    private String message;
    private HttpStatus code;

    public CustomResponseObject(){
        message = "NOT DEFINED";
        code = HttpStatus.FORBIDDEN;
    }

    public CustomResponseObject(String message,HttpStatus code){
        this.message=message;
        this.code=code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public HttpStatus getCode() {
        return code;
    }

    public void setCode(HttpStatus code) {
        this.code = code;
    }
}
