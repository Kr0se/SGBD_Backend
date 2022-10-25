package com.online.files.online.files.repositories;


import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.online.files.online.files.models.ProvaCustomer;

@Repository
public interface ProvaCustomerRepository extends MongoRepository<ProvaCustomer, String> {

  public ProvaCustomer findByFirstName(String firstName); //el mongorepository ja detecta que FirstName (nom del metode) es el nom de l'atribut a buscar a la db
  public List<ProvaCustomer> findByLastName(String lastName);

}