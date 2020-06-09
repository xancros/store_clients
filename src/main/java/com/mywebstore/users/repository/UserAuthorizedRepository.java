package com.mywebstore.users.repository;

import com.mywebstore.users.entity.UserAuthorized;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserAuthorizedRepository extends JpaRepository<UserAuthorized,Integer> {
    Optional<UserAuthorized> findByUsername(String username);
}
