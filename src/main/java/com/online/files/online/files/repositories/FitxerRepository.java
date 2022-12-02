package com.online.files.online.files.repositories;

import com.online.files.online.files.models.fitxers.Fitxer;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Date;

@Repository
public interface FitxerRepository extends MongoRepository<Fitxer, String> {
    public Fitxer findByNom(String nom);
    public Collection<Fitxer> findByTipus(String tipus);
    public Collection<Fitxer> findByDataPujada(Date data);
    public Collection<Fitxer> findByDataPujadaBetween(Date data1, Date data2);
    public Collection<Fitxer> findByNomStartingWith(String nom);
    public Collection<Fitxer> findByNomEndingWith(String nom);

}
