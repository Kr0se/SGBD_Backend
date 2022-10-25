package com.online.files.online.files.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.online.files.online.files.models.ProvaCustomer;
import com.online.files.online.files.repositories.ProvaCustomerRepository;

@RestController
public class ProvaController {

    @Autowired
    private ProvaCustomerRepository customerRepo;


    @GetMapping("/prova")
    public ResponseEntity<String> getProva() {
        customerRepo.deleteAll();

    // save a couple of customers
    customerRepo.save(new ProvaCustomer("Alice", "Smith"));
    customerRepo.save(new ProvaCustomer("Bob", "Smith"));

    // fetch all customers
    System.out.println("Customers found with findAll():");
    System.out.println("-------------------------------");
    for (ProvaCustomer customer : customerRepo.findAll()) {
      System.out.println(customer);
    }
    System.out.println();

    // fetch an individual customer
    System.out.println("Customer found with findByFirstName('Alice'):");
    System.out.println("--------------------------------");
    System.out.println(customerRepo.findByFirstName("Alice"));

    System.out.println("Customers found with findByLastName('Smith'):");
    System.out.println("--------------------------------");
    for (ProvaCustomer customer : customerRepo.findByLastName("Smith")) {
      System.out.println(customer);
    }

        return new ResponseEntity<>("Funciona!", HttpStatus.OK);
    }
}
    

