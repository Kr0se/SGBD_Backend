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

    /**
     * @return Llista de totes les relacions existents en base de dades
     */
    public Collection<FitxerUsuari> getFitxersUsuaris(){ return fitxerUsuariRepository.findAll();}

    /**
     * @param f és el id del fitxer
     * @param u és el username del usuari
     * @param esPropietari parametre que indica si el usuari es propietari del fitxer o es compartit
     * @return Relacio de Fitxer Usuari creada a partir del fitxer f i del usuari u
     */
    public FitxerUsuari createFitxerUsuari(String f, String u, Boolean esPropietari){

        User user = userService.getUserByUserName(u);
        Fitxer fitxer = fitxerService.getFitxer(f);

        FitxerUsuari fu = new FitxerUsuari(f, user.getId(), esPropietari);
        fitxerUsuariRepository.save(fu);

        fitxerService.addFitxerUser(fitxer,fu);
        userService.addFitxerUser(user, fu);

        return fu;
    }

    /**
     * @param relacio amb el id del fitxer i el username del usuari
     * @return Relacio de Fitxer Usuari creada a partir del fitxer i del usuari
     */
    public FitxerUsuari createFitxerUsuariCompartit(RelacioDTO relacio){

        User user = userService.getUserByUserName(relacio.getUsername());
        Fitxer fitxer = fitxerService.getFitxer(relacio.getFitxerId());

        if(existeixRelacio(relacio.getFitxerId(), user.getId())){
            return new FitxerUsuari();
        }

        FitxerUsuari fu = new FitxerUsuari(relacio.getFitxerId(), user.getId(), false);
        fitxerUsuariRepository.save(fu);

        fitxerService.addFitxerUser(fitxer,fu);
        userService.addFitxerUser(user, fu);

        return fu;
    }

    /**
     * @param fitxerId és el id del fitxer
     * @param userId és el username del usuari
     * @return cert si existeix la relacio entre el fitxer i l'usuari o falts altrement
     */
    public Boolean existeixRelacio(String fitxerId, String userId){
        Optional<FitxerUsuari> fu = fitxerUsuariRepository.findByFitxerIdAndUserId(fitxerId,userId);
        if (fu.isEmpty()) {
            return false;
        }
        return true;
    }

    /**
     * @param relacio amb el id del fitxer i el username del usuari
     * @return Relacio de Fitxer Usuari borrada entre del fitxer i del usuari
     */
    public void deleteFitxerUsuariCompartit(RelacioDTO relacio) {
        User user = userService.getUserByUserName(relacio.getUsername());
        Fitxer fitxer = fitxerService.getFitxer(relacio.getFitxerId());

        Optional<FitxerUsuari> fu = fitxerUsuariRepository.findByFitxerIdAndUserId(relacio.getFitxerId(),user.getId());
        if (fu.isEmpty()) {
            throw new RuntimeException("Aquesta relacio no existeix");
        }
        userService.deleteFitxerUsuari(fu.get());
        fitxerUsuariRepository.delete(fu.get());
    }

    /**
     * @param fitxerId és el id del fitxer
     * @param userId és el username del usuari
     * @return cert l'usuari es el propietari del fitxer i  o falts altrement
     */
    public Boolean esPropietari(String fitxerId, String userId){

        Optional<FitxerUsuari> fu = fitxerUsuariRepository.findByFitxerIdAndUserId(fitxerId,userId);
        if(fu.isEmpty()){
            throw new RuntimeException("Aquesta relacio no existeix");
        }
        return fu.get().getEsPropietari();
    }

    /**
     *
     * @param username usuari de la base de dades (clau primaria de user)
     * @return Llista de relacions de l'usuari amb fitxers
     */
    public Collection<FitxerUsuari> getListFitxerUsuariByUsuari(String username){
        User user = userService.getUserByUserName(username);
        Optional<Collection<FitxerUsuari>> listFU = fitxerUsuariRepository.findByuserId(user.getId());
        if (listFU.isEmpty())
            throw new RuntimeException("Aquest usuari no te cap fitxer");
       return listFU.get();
    }

    /**
     *
     * @param username usuari de la base de dades (clau primaria de user)
     * @return Llista de relacions de l'usuari amb fitxers que sigui el propietari
     */
    public Collection<FitxerUsuari> getListFitxerUsuariByUsuariPropietari(String username){
        User user = userService.getUserByUserName(username);
        Optional<Collection<FitxerUsuari>> listFU = fitxerUsuariRepository.findByUserIdAndEsPropietari(user.getId(),true);
        if (listFU.isEmpty())
            throw new RuntimeException("Aquest usuari no te cap fitxer");
        return listFU.get();
    }


    /**
     *
     * @param fitxerId del fitxer
     * @return Llista de relacions del fitxer amb usuaris
     */
    public Collection<FitxerUsuari> getListFitxerUsuariByFitxer(String fitxerId){
        Optional<Collection<FitxerUsuari>> listFU = fitxerUsuariRepository.findByfitxerId(fitxerId);
        if (listFU.isEmpty())
            throw new RuntimeException("Aquest fitxer no es de cap usuari");
        return listFU.get();
    }

    /**
     *
     * @param esPropietari si la relacio es compartida o no
     * @return Llista de relacions de fitxer usuari segons el parametre
     */
    public Collection<FitxerUsuari> getListFitxerUsuariByEsPropietari(Boolean esPropietari){
        Optional<Collection<FitxerUsuari>> listFU = fitxerUsuariRepository.findByesPropietari(esPropietari);
        if (listFU.isEmpty())
            throw new RuntimeException("No hi ha cap relacio segons el parametre de propietat indicat");
        return listFU.get();
    }

    /**
     *
     * @param username usuari de la base de dades (clau primaria de user)
     * @return Llista de fitxers de l'usuari
     */
    public Collection<Fitxer> getListFitxerByUsuari(String username) {

        Collection<Fitxer> toReturn = new ArrayList<>();
        Collection<FitxerUsuari> fus = this.getListFitxerUsuariByUsuari(username);
        for(FitxerUsuari fu:fus){
            toReturn.add(fitxerService.getFitxer(fu.getFitxerId()));
        }
        return toReturn;
    }

    /**
     *
     * @param username usuari de la base de dades (clau primaria de user)
     * @return Llista de fitxers del usuari que  altres usuaris li han compartit
     */
    public Collection<Fitxer> getListFitxerCompartitsByUsuari(String username) {

        Collection<Fitxer> toReturn = new ArrayList<>();
        Collection<FitxerUsuari> fus = this.getListFitxerUsuariByUsuari(username);
        for(FitxerUsuari fu:fus){
            if (!fu.getEsPropietari())
                toReturn.add(fitxerService.getFitxer(fu.getFitxerId()));
        }
        return toReturn;
    }

    /**
     *
     * @param username usuari de la base de dades (clau primaria de user)
     * @return Llista de fitxers del usuari que li ha compartit amb altres usuaris
     */
    public Collection<Fitxer> getListFitxerCompartitsToUsers(String username){
        Collection<Fitxer> toReturn = new ArrayList<>();
        Collection<FitxerUsuari> fitxersUser = this.getListFitxerUsuariByUsuariPropietari(username);
        Collection<FitxerUsuari> fitxerCompartits = this.getListFitxerUsuariByEsPropietari(false);
        for (FitxerUsuari fUser : fitxersUser){
            for (FitxerUsuari fCompartit : fitxerCompartits){
                if (fUser.getFitxerId().equals(fCompartit.getFitxerId())){
                    Fitxer fitxer = fitxerService.getFitxer(fCompartit.getFitxerId());
                    if(!toReturn.contains(fitxer)){
                        toReturn.add(fitxer);
                    }
                }
            }
        }

        return toReturn;
    }

    /**
     * @param fitxerId del fitxer
     * @return Llista de usuaris amb els que s'ha compartit el fitxer
     */
    public Collection<User> getListUsersCompartits(String fitxerId){
        Collection<User> toReturn = new ArrayList<>();
        Collection<FitxerUsuari> fus = this.getListFitxerUsuariByFitxer(fitxerId);
        for(FitxerUsuari fu:fus){
            if (!fu.getEsPropietari())
                toReturn.add(userService.getUser(fu.getUserId()));
        }
        return toReturn;
    }

    /**
     * @param fitxerId del fitxer
     * @return Usuari propietari del fitxer
     */
    public User getUserPropietari(String fitxerId){
        Optional<FitxerUsuari> fu = fitxerUsuariRepository.findByFitxerIdAndEsPropietari(fitxerId,true);
        if(fu.isEmpty()){
            throw new RuntimeException("Aquesta relacio no existeix, aquest usuari no te cap propietari!");
        }
        return userService.getUser(fu.get().getUserId());
    }

    /**
     * @param fus llista de relacions de fitxer usuari
     * @return L'usuari amb l'estructura actualitzada amb les relacions esborrades
     */
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
