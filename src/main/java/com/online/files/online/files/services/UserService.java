package com.online.files.online.files.services;

import com.online.files.online.files.models.User;
import com.online.files.online.files.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collection;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public Collection<User> getUsers(){
        return userRepository.findAll();
    }

    public String addUser(String firstName, String lastName){
        User user = new User(firstName,lastName);
        user = userRepository.insert(user);
        return user.getId();
    }
}
