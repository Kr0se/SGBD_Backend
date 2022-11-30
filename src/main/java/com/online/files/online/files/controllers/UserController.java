package com.online.files.online.files.controllers;

import com.online.files.online.files.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.online.files.online.files.DTO.FolderDTO;
import com.online.files.online.files.DTO.UserAuthDTO;
import com.online.files.online.files.models.User;
import com.online.files.online.files.repositories.UserRepository;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;

@RequestMapping(path="/users")
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping()
    public Collection<User> getUser(){
        return userService.getUsers();
    }

    @GetMapping("/get")
    public User getUser(@RequestParam("firstName") String firstName){
        return userService.getUser(firstName);
    }

    @GetMapping("/getUserByUsername")
    public User getUserByUsername(@RequestParam("username") String username){
        return userService.getUserByUsername(username);
    }

    @PostMapping("/register")
    public ResponseEntity<Boolean> register(@RequestBody UserAuthDTO user){

        return new ResponseEntity<>(this.userService.register(user),HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<Boolean> login(@RequestBody UserAuthDTO user){

        return new ResponseEntity<>(this.userService.login(user),HttpStatus.OK);
    }

    /**
     * 
     * @param username usuari de la base de dades (clau primaria de user)
     * @param path path on volem inserir la nova carpeta. La carpeta principal
     *             dels usuaris es diu 'main'. Exemples: 'main', 'main/pelis'
     * @param folderName nom de la carpeta nova
     * @return True si s'ha afegit la carpeta al path esperat, false altrament
     */
    @PostMapping("/{username}/addFolder")
    public ResponseEntity<User> addFolder(@PathVariable("username") String username, @RequestBody FolderDTO folder){

        return new ResponseEntity<>(this.userService.addFolder(username, folder),HttpStatus.OK);
    }

    /**
     * 
     * @param username usuari de la base de dades (clau primaria de user)
     * @param path path on volem borrar la carpeta. La carpeta principal
     *             dels usuaris es diu 'main'. Exemples: 'main', 'main/pelis'
     * @param folderName nom de la carpeta que volem borrar
     * @return True si s'ha borrat la carpeta al path esperat, false altrament
     */
    @PostMapping("/{username}/removeFolder")
    public ResponseEntity<User> removeFolder(@PathVariable("username") String username, @RequestBody FolderDTO folder){

        return new ResponseEntity<>(this.userService.removeFolder(username, folder),HttpStatus.OK);
    }

}