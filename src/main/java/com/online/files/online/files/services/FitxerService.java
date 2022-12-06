package com.online.files.online.files.services;

import com.online.files.online.files.DTO.FitxerDTO;
import com.online.files.online.files.models.fitxers.Fitxer;
import com.online.files.online.files.models.FitxerUsuari;
import com.online.files.online.files.models.fitxers.FitxerBD;
import com.online.files.online.files.repositories.FitxerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.Optional;

@Service
public class FitxerService
{
    @Autowired
    private FitxerRepository fitxerRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private FitxerBDService fitxerBDService;

    public Collection<Fitxer> getFitxers(){ return fitxerRepository.findAll();}

    public Fitxer createFitxer(String nom, String tipus, Date data, String idDB){
        Fitxer f = new Fitxer(nom, tipus, data, idDB);
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

    public FitxerBD getFitxerBD(FitxerDTO fitxer) throws IOException {
        Optional<Fitxer> f = fitxerRepository.findById(fitxer.getId());
        if (f.isEmpty())
            throw new RuntimeException("No existeix un fitxer amb aquesta id");
        return fitxerBDService.getFitxerBD(f.get().getFitxerDBId());
    }

    public FitxerBD getFitxerBD(String fitxerId) throws IOException {
        Optional<Fitxer> f = fitxerRepository.findById(fitxerId);
        if (f.isEmpty())
            throw new RuntimeException("No existeix un fitxer amb aquesta id");
        return fitxerBDService.getFitxerBD(f.get().getFitxerDBId());
    }

    public Fitxer getFitxerByNom(String nom){
        return fitxerRepository.findByNom(nom);
    }

    public void deleteFitxer(String fitxerID){
        Fitxer f = getFitxer(fitxerID);
        fitxerBDService.deleteFitxerBD(f.getFitxerDBId());
        fitxerRepository.delete(f);
    }

    public Collection<Fitxer> getFitxerByTipus(String tipus){
        return fitxerRepository.findByTipus(tipus);
    }

    public Collection<Fitxer> getFitxerByDataPujada(Date data){
        return fitxerRepository.findByDataPujada(data);
    }

    public Collection<Fitxer> getFitxerByDataPujadaBetween(Date data1, Date data2){
        return fitxerRepository.findByDataPujadaBetween(data1, data2);
    }

    public Collection<Fitxer> getFitxerByNomStartsWith(String nom){
        return fitxerRepository.findByNomStartingWith(nom);
    }

    public Collection<Fitxer> getFitxerByNomEndsWith(String nom){
        return fitxerRepository.findByNomEndingWith(nom);
    }
}
