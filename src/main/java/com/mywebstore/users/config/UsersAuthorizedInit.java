package com.mywebstore.users.config;

import com.mywebstore.users.entity.UserAuthorized;
import com.mywebstore.users.repository.UserAuthorizedRepository;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UsersAuthorizedInit implements InitializingBean {

    @Autowired
    private UserAuthorizedRepository userAuthorizedRepository;

    private void init(){
        UserAuthorized user = new UserAuthorized();
        user.setUsername("Xancros");
        user.setPassword("password");
        user.setRoles("ROLE_USER");
        user.setActive(true);
        userAuthorizedRepository.save(user);
        user.setUsername("Zellex");
        user.setPassword("12345");
        user.setRoles("ROLE_ADMIN");
        userAuthorizedRepository.save(user);
    }


    @Override
    public void afterPropertiesSet() throws Exception {
            init();
    }
}
