package com.mywebstore.users.service;

import com.mywebstore.users.entity.User;
import com.mywebstore.users.model.UserModel;
import com.mywebstore.users.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.swing.text.html.Option;
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

    public Optional<User> findUserByIdCard(String idCard){
        return this.userRepository.findByIdCard(idCard);
    }

    public Optional<User> findByUserNameAndPasswordAndIdCard(String username,int password, String idCard){
        return this.userRepository.findByUserNameAndPasswordAndIdCard(username,password,idCard);
    }

    public boolean createUser(UserModel userModel) {
        Optional<User> maybeUser = this.findUserByIdCard(userModel.getName());
        if(maybeUser.isEmpty()){
            User user = new User();
            user.setName(userModel.getName());
            user.setLastName(userModel.getLastName());
            user.setPassword(userModel.getPassword());
            user.setPhone(userModel.getPhone());
            user.setUserName(userModel.getUserName());
            user.setIdCard(userModel.getIdCard());
            return this.userRepository.save(user).getId() != 0;

        }
        return false;
    }

    public boolean removeUser(UserModel userModel) {
        Optional<User> maybeUser = this.findByUserNameAndPasswordAndIdCard(userModel.getUserName(),userModel.getPassword(),userModel.getIdCard());
        if(maybeUser.isPresent()){
            this.userRepository.delete(maybeUser.get());
            return true;
        }
        else
            return false;
    }

    public boolean modifyUser(UserModel userModel){
        Optional<User> maybeUser = this.findUserByUsername(userModel.getUserName());
        if(maybeUser.isPresent()){
            User user = maybeUser.get();
            user.setName(userModel.getName());
            user.setLastName(userModel.getLastName());
            user.setPassword(userModel.getPassword());
            user.setPhone(userModel.getPhone());
            user.setUserName(userModel.getUserName());
            user.setIdCard(userModel.getIdCard());
            try {
                this.userRepository.save(user);
            }catch (Exception ex){
                return false;
            }
            return true;
        }
        return false;
    }

}
