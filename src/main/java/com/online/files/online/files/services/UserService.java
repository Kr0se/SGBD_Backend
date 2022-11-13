package com.online.files.online.files.services;

import com.online.files.online.files.models.Carpeta;
import com.online.files.online.files.models.User;
import com.online.files.online.files.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public Collection<User> getUsers(){
        return userRepository.findAll();
    }

    public Boolean register(String username, String password, String nom, String cognom){
        if(userRepository.findByUsername(username) != null){
            return false;
        }
        User user = new User(username, password, nom, cognom);
        userRepository.insert(user);
        return true;
    }

    public Boolean login(String username, String password){
        User u = userRepository.findByUsername(username);
        if(u == null){
            return false;
        }
        return u.getPassword().equals(password);   
    }

    public Boolean addFolder(String username, String path, String folderName){
        //Mirem si l'usuari existeix al sistema
        User u = userRepository.findByUsername(username);
        if(u == null){
            return false;
        }
        List<String> itemsPath = Arrays.asList(path.split("/")); //cada subcarpeta esta separada per un /
        itemsPath = itemsPath.subList(1, itemsPath.size());

        //Intentem afegir la nova carpeta a l'estructura de l'usuari.
        //Retornem l'usuari amb la nova carpeta posada
        try {
            u = u.updateCarpeta(itemsPath, folderName);
        } catch (Exception e) {
            return false;
        }

        this.userRepository.save(u);
        return true;
    }
}
