package com.online.files.online.files.repositories;

import com.online.files.online.files.models.Fitxer;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FitxerRepository extends MongoRepository<Fitxer, String>
{
    public Fitxer findByNom(String nom);
}
