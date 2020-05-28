package com.mywebstore.users.repository;

import com.mywebstore.users.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepository extends CrudRepository<User,Long> {
    public Optional<User> findByUserName(String username);
}
