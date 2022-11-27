package com.online.files.online.files.services;

import com.online.files.online.files.models.Fitxer;
import com.online.files.online.files.models.FitxerUsuari;
import com.online.files.online.files.models.User;
import com.online.files.online.files.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collection;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public Collection<User> getUsers(){
        return userRepository.findAll();
    }

    public User createUser(String firstName, String lastName){
        User user = new User(firstName,lastName);
        userRepository.save(user);
        return user;
    }

    public void addFitxerUser(User u, FitxerUsuari fu){
        u.addFitxerUsuari(fu);
        userRepository.save(u);
    }

    public User getUser(String userID){
        Optional<User> u = userRepository.findById(userID);
        if (u.isEmpty())
            throw new RuntimeException("No existeix un usuari amb aquesta id");
        return u.get();
    }

    public User getUserByNom(String firstName){
        return userRepository.findByFirstName(firstName);
    }
}
