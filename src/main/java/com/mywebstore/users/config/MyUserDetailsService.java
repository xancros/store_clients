package com.mywebstore.users.config;

import com.mywebstore.users.entity.UserAuthorized;
import com.mywebstore.users.repository.UserAuthorizedRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MyUserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {
    @Autowired
    UserAuthorizedRepository userAuthorizedRepository;
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Optional<UserAuthorized> user = userAuthorizedRepository.findByUsername(s);
        user.orElseThrow(() -> new UsernameNotFoundException("User not found: "+s));
        return user.map(MyUserDetails::new).get();

    }
}
