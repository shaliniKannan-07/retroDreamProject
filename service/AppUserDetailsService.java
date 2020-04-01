package com.cognizant.loginservice.service;


import com.cognizant.loginservice.controller.AuthenticationController;
import com.cognizant.loginservice.exception.UserAlreadyExistsException;
import com.cognizant.loginservice.exception.UsernameNotFound;
import com.cognizant.loginservice.entity.User;
import com.cognizant.loginservice.repository.LoginRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.cognizant.loginservice.entity.AppUser;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Slf4j
@Service
public class AppUserDetailsService implements UserDetailsService {



    @Autowired
    AuthenticationController authenticationController;
    @Autowired
    RestTemplate restTemplate;

    // @Cacheable(value = "login")
    @Override
    public UserDetails loadUserByUsername(String username){
        log.info(username);
        log.info("Inside loadUserByUsername method in UserDetailService");
        AppUser appUser;
        appUser = new AppUser(userData(username));
        return appUser;
    }


    @Bean
    RestTemplate restTemplate(){
        return new RestTemplate();
    }

    public User userData(String username) throws UsernameNotFound {
        User userData=this.restTemplate.getForObject("http://localhost:1115/data/"+username,User.class);
        return userData;
    }

//    public void  register(User newUser) throws UserAlreadyExistsException {
//        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//        newUser.setPassword(encoder.encode(newUser.getPassword()));
//        Optional<User> result = loginRepository.findByUsername(newUser.getUsername());
//        if(result.isPresent()){
//            throw new UserAlreadyExistsException();
//        }
//        else{
//            saveUser(newUser);
//        }
//    }

    public void saveUser(User user) {
        User userData=this.restTemplate.postForObject("http://localhost:1115/data/register",user,User.class);
    }


}
