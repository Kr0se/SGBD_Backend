package com.online.files.online.files.controllers;

import com.online.files.online.files.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.online.files.online.files.models.User;
import com.online.files.online.files.repositories.UserRepository;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;

@RequestMapping(path="/users")
@RestController
@CrossOrigin("*")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping()
    public Collection<User> getUsers(){
        return userService.getUsers();
    }

    /*@PostMapping("/add")
    public String addUser(@RequestParam("firstName") String firstName,
                          @RequestParam("lastName") String lastName){
        User u = userService.createUser(firstName,lastName);
        return "redirect:/users/" + u.getFirstName();
    }*/

    @PostMapping("/register")
    public ResponseEntity<Boolean> register(
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            @RequestParam("nom") String nom,
            @RequestParam("cognom") String cognom){

        return new ResponseEntity<>(this.userService.register(username, password, nom, cognom),HttpStatus.OK);
    }

    @GetMapping("/login")
    public ResponseEntity<Boolean> login(
            @RequestParam("username") String username,
            @RequestParam("password") String password){

        return new ResponseEntity<>(this.userService.login(username, password),HttpStatus.OK);
    }

    @GetMapping("/get")
    public User getUser(@RequestParam("firstName") String firstName){
        return userService.getUser(firstName);
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
    public ResponseEntity<Boolean> addFolder(
            @PathVariable("username") String username,
            @RequestParam("path") String path,
            @RequestParam("folderName") String folderName){

        return new ResponseEntity<>(this.userService.addFolder(username, path, folderName),HttpStatus.OK);
    }

    /**
     *
     * @param username usuari de la base de dades (clau primaria de user)
     * @param path path on volem borrar la carpeta. La carpeta principal
     *             dels usuaris es diu 'main'. Exemples: 'main', 'main/pelis'
     * @param folderName nom de la carpeta que volem borrar
     * @return True si s'ha borrat la carpeta al path esperat, false altrament
     */
    @DeleteMapping("/{username}/removeFolder")
    public ResponseEntity<Boolean> removeFolder(
            @PathVariable("username") String username,
            @RequestParam("path") String path,
            @RequestParam("folderName") String folderName){

        return new ResponseEntity<>(this.userService.removeFolder(username, path, folderName),HttpStatus.OK);
    }

}
    

