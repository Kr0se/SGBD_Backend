package com.online.files.online.files.services;

import com.online.files.online.files.DTO.FileFolderDTO;
import com.online.files.online.files.DTO.FolderDTO;
import com.online.files.online.files.DTO.UserAuthDTO;
import com.online.files.online.files.models.FitxerUsuari;
import com.online.files.online.files.models.User;
import com.online.files.online.files.models.fitxers.Fitxer;
import com.online.files.online.files.repositories.FitxerRepository;
import com.online.files.online.files.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FitxerRepository fitxerRepository;

    public Collection<User> getUsers(){
        return userRepository.findAll();
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

    public User getUserByUsername(String username){
        User u = userRepository.findByUsername(username);
        if(u == null){
            throw new RuntimeException("No existeix un usuari amb aquest username");
        }

        return u;
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

    public User addFolder(String username, FolderDTO folder){
        //Mirem si l'usuari existeix al sistema
        User u = userRepository.findByUsername(username);
        if(u == null){
            throw new RuntimeException("No existeix un usuari amb aquest username");
        }
        List<String> itemsPath = Arrays.asList(folder.getPath().split("/")); //cada subcarpeta esta separada per un /
        itemsPath = itemsPath.subList(1, itemsPath.size()); //s'elimina la main

        //Intentem afegir la nova carpeta a l'estructura de l'usuari.
        //Retornem l'usuari amb la nova carpeta posada
        try {
            u = u.addFolder(itemsPath, folder.getFolderName());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return u;
        }

        this.userRepository.save(u);
        return u;
    }

    public User removeFolder(String username, FolderDTO folder) {
        //Mirem si l'usuari existeix al sistema
        User u = userRepository.findByUsername(username);
        if(u == null){
            throw new RuntimeException("No existeix un usuari amb aquest username");
        }
        List<String> itemsPath = Arrays.asList(folder.getPath().split("/")); //cada subcarpeta esta separada per un /
        itemsPath = itemsPath.subList(1, itemsPath.size()); //s'elimina la main

        //Borrem la carpeta de l'estructura de l'usuari.
        //Retornem l'usuari amb la nova carpeta borrada
        try {
            u = u.removeFolder(itemsPath, folder.getFolderName());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return u;
        }

        this.userRepository.save(u);
        return u;
    }

    public void deleteFitxerUsuari(FitxerUsuari fu){
        User u = getUser(fu.getUserId());
        u.removeFitxerUsuari(fu);
        u.toString();
        userRepository.save(u);
    }

    public User renameFolder(String username, FolderDTO folder) {
        //Mirem si l'usuari existeix al sistema
        User u = userRepository.findByUsername(username);
        if(u == null){
            throw new RuntimeException("No existeix un usuari amb aquest username");
        }
        List<String> itemsPath = Arrays.asList(folder.getPath().split("/")); //cada subcarpeta esta separada per un /
        itemsPath = itemsPath.subList(1, itemsPath.size()); //s'elimina la main
        //Actualitzem el nom de la carpeta a l'estructura de l'usuari.
        //Retornem l'usuari amb l'estructura actualitzada
        try {
            u = u.renameFolder(itemsPath, folder.getFolderName(), folder.getNewFolderName());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return u;
        }

        this.userRepository.save(u);
        return u;
    }

    public List<Fitxer> getFiles(String username, FolderDTO folder){
        //Mirem si l'usuari existeix al sistema
        User u = userRepository.findByUsername(username);
        if(u == null){
            throw new RuntimeException("No existeix un usuari amb aquest username");
        }
        List<String> itemsPath = Arrays.asList(folder.getPath().split("/")); //cada subcarpeta esta separada per un /
        itemsPath = itemsPath.subList(1, itemsPath.size()); //s'elimina la main

        try {
            return u.getFiles(itemsPath);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public Boolean addFile(String username, FileFolderDTO fileFolder){
        //Mirem si l'usuari existeix al sistema
        User u = userRepository.findByUsername(username);
        if(u == null){
            throw new RuntimeException("No existeix un usuari amb aquest username");
        }
        List<String> itemsPath = Arrays.asList(fileFolder.getPath().split("/")); //cada subcarpeta esta separada per un /
        itemsPath = itemsPath.subList(1, itemsPath.size()); //s'elimina la main

        //Mirem si el fitxer existeix al sistema
        Optional<Fitxer> f = fitxerRepository.findById(fileFolder.getFitxerID());
        if (f.isEmpty())
            return false;
        //Intentem afegir el fitxer a la carpeta a l'estructura de l'usuari
        //Retornem l'usuari amb el fitxer inserit a la carpeta
        try {
            u = u.addFile(itemsPath, f.get());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }

        this.userRepository.save(u);
        return true;
    }

    public Boolean removeFile(String username,  FileFolderDTO fileFolder){
        //Mirem si l'usuari existeix al sistema
        User u = userRepository.findByUsername(username);
        if(u == null){
            throw new RuntimeException("No existeix un usuari amb aquest username");
        }
        List<String> itemsPath = Arrays.asList(fileFolder.getPath().split("/")); //cada subcarpeta esta separada per un /
        itemsPath = itemsPath.subList(1, itemsPath.size()); //s'elimina la main
        
        //Mirem si el fitxer existeix al sistema
        Optional<Fitxer> f = fitxerRepository.findById(fileFolder.getFitxerID());
        if (f.isEmpty())
            return false;
            
        //Intentem borrar el fitxer de la carpeta a l'estructura de l'usuari
        //Retornem l'usuari amb el fitxer borrat de la carpeta
        try {
            u = u.removeFile(itemsPath, f.get());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }

        this.userRepository.save(u);
        return true;
    }
}