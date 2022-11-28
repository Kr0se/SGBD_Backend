package com.online.files.online.files.services;

import com.online.files.online.files.models.FitxerUsuari;
import com.online.files.online.files.models.User;
import com.online.files.online.files.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;
import java.util.Arrays;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public Collection<User> getUsers(){
        return userRepository.findAll();
    }

    /*public User createUser(String firstName, String lastName){
        User user = new User(firstName,lastName);
        userRepository.save(user);
        return user;
    }*/

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

    public User getUserByUsername(String username){
        return userRepository.findByUsername(username);
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
