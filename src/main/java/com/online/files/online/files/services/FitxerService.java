package com.online.files.online.files.services;

import com.online.files.online.files.models.fitxers.Fitxer;
import com.online.files.online.files.models.FitxerUsuari;
import com.online.files.online.files.repositories.FitxerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public class FitxerService
{
    @Autowired
    private FitxerRepository fitxerRepository;



    public Collection<Fitxer> getFitxers(){ return fitxerRepository.findAll();}

    public Fitxer createFitxer(String nom, String idDB){
        Fitxer f = new Fitxer(nom,idDB);
        fitxerRepository.save(f);
        return f;
    }

    public void addFitxerUser(Fitxer f, FitxerUsuari fu){
        f.addFitxerUsuari(fu);
        fitxerRepository.save(f);
    }

    public Fitxer getFitxer(String fitxerID){
        Optional<Fitxer> f = fitxerRepository.findById(fitxerID);
        if (f.isEmpty())
            throw new RuntimeException("No existeix un fitxer amb aquesta id");
        return f.get();
    }

    public String getFitxerBD(String fitxerID){
        Optional<Fitxer> f = fitxerRepository.findById(fitxerID);
        if (f.isEmpty())
            throw new RuntimeException("No existeix un fitxer amb aquesta id");
        return f.get().getFitxerDBId();
    }

    public Fitxer getFitxerByNom(String nom){
        return fitxerRepository.findByNom(nom);
    }
}
