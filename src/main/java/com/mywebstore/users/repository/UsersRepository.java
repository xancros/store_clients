package com.mywebstore.users.repository;

import com.mywebstore.users.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<User,Long> {
     Optional<User> findByUserName(String username);

     Optional<User> findByIdCard(String idCard);

     Optional<User> findByUserNameAndPasswordAndIdCard(String username,int password,String idCard);

}
