package com.online.files.online.files.repositories;

import com.online.files.online.files.models.FitxerUsuari;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface FitxerUsuariRepository extends MongoRepository<FitxerUsuari,String> {

    Optional<Collection<FitxerUsuari>> findByfitxerId(String fitxerId);

    Optional<Collection<FitxerUsuari>> findByuserId(String userId);
}
