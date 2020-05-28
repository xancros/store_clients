package com.mywebstore.users.service;

import com.mywebstore.users.entity.User;
import com.mywebstore.users.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UsersRepository userRepository;


    public boolean logIn(String userName, String password){
        Optional<User> user = this.findUserByUsername(userName);
        return user.isPresent() && Integer.valueOf(password).equals(user.get().getPassword());
    }

    public Optional<User> findUserByUsername(String username){
        return userRepository.findByUserName(username);
    }

    public int countAllUsers(){
        return (int) this.userRepository.count();
    }

    /*public User signupUser(User user){

    }

    public User deleteUser(User user){

    }


    public Optional<User> findUserByNameSurname(User user){

    }


    public User modifyUser(User user){

    }*/





}
