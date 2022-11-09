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

    @Autowired
    private UserRepository userRepo;

    @Autowired
    UserService userService;

    @GetMapping("/prova")
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
    }

    @PostMapping("/add")
    public String addUser(@RequestParam("firstName") String firstName,
                          @RequestParam("lastName") String lastName){
        String id = userService.addUser(firstName,lastName);
        return "redirect:/users/" + id;
    }

}
    

