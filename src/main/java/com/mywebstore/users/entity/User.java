package com.mywebstore.users.entity;

import javax.persistence.*;

@Entity
@Table(name="client")
public class User {
    @Id
    @GeneratedValue()
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String lastName;

    @Column
    private long phone;

    @Column
    private String idCard;

    @Column(name="user_name")
    private String userName;

    @Column
    private int password;


    public void setName(String name) {
        this.name = name;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setPhone(long phone) {
        this.phone = phone;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(int password) {
        this.password = password;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    public long getPhone() {
        return phone;
    }

    public String getIdCard() {
        return idCard;
    }

    public String getUserName() {
        return userName;
    }

    public int getPassword() {
        return password;
    }
}
