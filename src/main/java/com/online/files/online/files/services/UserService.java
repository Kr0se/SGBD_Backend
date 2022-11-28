package com.online.files.online.files.services;

import com.online.files.online.files.DTO.UserAuthDTO;
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

    public Boolean register(UserAuthDTO user){
        if(userRepository.findByUsername(user.getUsername()) != null){
            return false;
        }
        User u = new User(user.getUsername(), user.getPassword(), user.getName(), user.getSurname());
        userRepository.insert(u);
        return true;
    }

    public Boolean login(UserAuthDTO user){
        User u = userRepository.findByUsername(user.getUsername());
        if(u == null){
            return false;
        }
        return u.getPassword().equals(user.getPassword());   
    }

    public Boolean addFolder(String username, String path, String folderName){
        //Mirem si l'usuari existeix al sistema
        User u = userRepository.findByUsername(username);
        if(u == null){
            return false;
        }
        List<String> itemsPath = Arrays.asList(path.split("/")); //cada subcarpeta esta separada per un /
        itemsPath = itemsPath.subList(1, itemsPath.size()); //s'elimina la main

        //Intentem afegir la nova carpeta a l'estructura de l'usuari.
        //Retornem l'usuari amb la nova carpeta posada
        try {
            u = u.addFolder(itemsPath, folderName);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }

        this.userRepository.save(u);
        return true;
    }

    public Boolean removeFolder(String username, String path, String folderName) {
        //Mirem si l'usuari existeix al sistema
        User u = userRepository.findByUsername(username);
        if(u == null){
            return false;
        }
        List<String> itemsPath = Arrays.asList(path.split("/")); //cada subcarpeta esta separada per un /
        itemsPath = itemsPath.subList(1, itemsPath.size()); //s'elimina la main

        //Borrem la carpeta de l'estructura de l'usuari.
        //Retornem l'usuari amb la nova carpeta borrada
        try {
            u = u.removeFolder(itemsPath, folderName);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }

        this.userRepository.save(u);
        return true;
    }
}
