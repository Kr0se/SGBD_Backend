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
public class UserController {

    /*@Autowired
    private UserRepository userRepo;*/

    @Autowired
    private UserService userService;

    /*@GetMapping("/prova")
    public ResponseEntity<String> getProva() {
        userRepo.deleteAll();

    // save a couple of customers
        userRepo.save(new User("Alice", "Smith"));
        userRepo.save(new User("Bob", "Smith"));

    // fetch all customers
    System.out.println("Customers found with findAll():");
    System.out.println("-------------------------------");
    for (User customer : userRepo.findAll()) {
      System.out.println(customer);
    }
    System.out.println();

    // fetch an individual customer
    System.out.println("Customer found with findByFirstName('Alice'):");
    System.out.println("--------------------------------");
    System.out.println(userRepo.findByFirstName("Alice"));

    System.out.println("Customers found with findByLastName('Smith'):");
    System.out.println("--------------------------------");
    for (User customer : userRepo.findByLastName("Smith")) {
      System.out.println(customer);
    }

        return new ResponseEntity<>("Funciona!", HttpStatus.OK);
    }

    @GetMapping()
    public Collection<User> getUser(){
        return userService.getUsers();
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
    

