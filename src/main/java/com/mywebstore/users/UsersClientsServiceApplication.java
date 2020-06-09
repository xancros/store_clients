package com.mywebstore.users;

import com.mywebstore.users.repository.UserAuthorizedRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.mywebstore.users.repository")
public class UsersClientsServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UsersClientsServiceApplication.class, args);
	}



}
