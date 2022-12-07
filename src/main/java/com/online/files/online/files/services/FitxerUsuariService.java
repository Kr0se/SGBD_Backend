package com.online.files.online.files.services;

import com.online.files.online.files.DTO.RelacioDTO;
import com.online.files.online.files.DTO.UserAuthDTO;
import com.online.files.online.files.models.FitxerUsuari;
import com.online.files.online.files.models.User;
import com.online.files.online.files.models.fitxers.Fitxer;
import com.online.files.online.files.models.fitxers.FitxerBD;
import com.online.files.online.files.repositories.FitxerUsuariRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@Service
public class FitxerUsuariService {

    @Autowired
    private FitxerUsuariRepository fitxerUsuariRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private FitxerService fitxerService;

    public Collection<FitxerUsuari> getFitxersUsuaris(){ return fitxerUsuariRepository.findAll();}

    public FitxerUsuari createFitxerUsuari(String f, String u, Boolean esPropietari){

        User user = userService.getUserByUserName(u);
        Fitxer fitxer = fitxerService.getFitxer(f);

        FitxerUsuari fu = new FitxerUsuari(f, user.getId(), esPropietari);
        fitxerUsuariRepository.save(fu);

        fitxerService.addFitxerUser(fitxer,fu);
        userService.addFitxerUser(user, fu);

        return fu;
    }

    public FitxerUsuari createFitxerUsuariCompartit(RelacioDTO relacio){

        User user = userService.getUser(relacio.getUserId());
        Fitxer fitxer = fitxerService.getFitxer(relacio.getFitxerId());

        FitxerUsuari fu = new FitxerUsuari(relacio.getFitxerId(), relacio.getUserId(), false);
        fitxerUsuariRepository.save(fu);

        fitxerService.addFitxerUser(fitxer,fu);
        userService.addFitxerUser(user, fu);

        return fu;
    }

    public Boolean esPropietari(String fitxerId, String userId){

        Optional<FitxerUsuari> fu = fitxerUsuariRepository.findByFitxerIdAndUserId(fitxerId,userId);
        if(fu.isEmpty()){
            throw new RuntimeException("Aquesta relacio no existeix");
        }
        return fu.get().getEsPropietari();
    }


    public Collection<FitxerBD> getListFitxerUsuariByUsuari(UserAuthDTO userDTO) throws IOException {

        Collection<FitxerBD> toReturn = new ArrayList<>();
        User user = userService.getUserByUserName(userDTO.getUsername());
        Optional<Collection<FitxerUsuari>> listFU = fitxerUsuariRepository.findByuserId(user.getId());
        if (listFU.isEmpty())
            throw new RuntimeException("Aquest usuari no te cap fitxer");
        Collection<FitxerUsuari> fus = listFU.get();
        for(FitxerUsuari fu:fus){
            toReturn.add(fitxerService.getFitxerBD(fu.getFitxerId()));
        }
        return toReturn;
    }

    public Collection<FitxerUsuari> getListFitxerUsuariByFitxer(String fitxerId){
        Optional<Collection<FitxerUsuari>> listFU = fitxerUsuariRepository.findByfitxerId(fitxerId);
        if (listFU.isEmpty())
            throw new RuntimeException("Aquest fitxer no es de cap usuari");
        return listFU.get();
    }

    public User deleteFitxerUsuariOfUser(Collection<FitxerUsuari> fus){

        User user = new User();
        for (FitxerUsuari fu:fus){
            userService.deleteFitxerUsuari(fu);
            fitxerUsuariRepository.delete(fu);
            if (fu.getEsPropietari())
                user = userService.getUser(fu.getUserId());
        }
        return user;
    }

}
