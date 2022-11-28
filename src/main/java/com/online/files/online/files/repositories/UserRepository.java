package com.online.files.online.files.repositories;


import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.online.files.online.files.models.User;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

  public User findByUsername(String username); //el mongorepository ja detecta que username (nom del metode) es el nom de l'atribut a buscar a la db

}