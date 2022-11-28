package com.online.files.online.files.services;

import com.online.files.online.files.models.FitxerUsuari;
import com.online.files.online.files.repositories.FitxerUsuariRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public class FitxerUsuariService {

    @Autowired
    private FitxerUsuariRepository fitxerUsuariRepository;

    public Collection<FitxerUsuari> getFitxersUsuaris(){ return fitxerUsuariRepository.findAll();}

    public FitxerUsuari createFitxerUsuari(String f, String u){

        FitxerUsuari fu = new FitxerUsuari(f, u);
        fitxerUsuariRepository.save(fu);

        return fu;
    }

    public Collection<FitxerUsuari> getListFitxerUsuariByUsuari(String usuariId){
        Optional<Collection<FitxerUsuari>> listFU = fitxerUsuariRepository.findByuserId(usuariId);
        if (listFU.isEmpty())
            throw new RuntimeException("Aquest usuari no te cap fitxer");
        return listFU.get();
    }

    public Collection<FitxerUsuari> getListFitxerUsuariByFitxer(String fitxerId){
        Optional<Collection<FitxerUsuari>> listFU = fitxerUsuariRepository.findByfitxerId(fitxerId);
        if (listFU.isEmpty())
            throw new RuntimeException("Aquest fitxer no es de cap usuari");
        return listFU.get();
    }
}
