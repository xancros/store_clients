package com.mywebstore.users.model;



public class UserModel {
    private String name;
    private String lastName;
    private long phone;
    private String idCard;
    private String userName;
    private int password;

    public UserModel (String name, String lastName, String idCard, String userName, int password){
        this(name,lastName,null,idCard,userName,password);
    }
    public UserModel(String name, String lastName, Long phone, String idCard, String userName, int password) {
        this.name = name;
        this.lastName = lastName;
        this.phone = phone;
        this.idCard = idCard;
        this.userName = userName;
        this.password = password;
    }
}
