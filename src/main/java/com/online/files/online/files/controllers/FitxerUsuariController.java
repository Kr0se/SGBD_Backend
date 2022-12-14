package com.online.files.online.files.controllers;

import com.online.files.online.files.DTO.RelacioDTO;
import com.online.files.online.files.DTO.UserAuthDTO;
import com.online.files.online.files.models.fitxers.Fitxer;
import com.online.files.online.files.models.FitxerUsuari;
import com.online.files.online.files.models.User;
import com.online.files.online.files.models.fitxers.FitxerBD;
import com.online.files.online.files.services.FitxerBDService;
import com.online.files.online.files.services.FitxerService;
import com.online.files.online.files.services.FitxerUsuariService;
import com.online.files.online.files.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

@RequestMapping(path = "/fitxersUsuaris")
@RestController
public class FitxerUsuariController {

    @Autowired
    private FitxerUsuariService fitxerUsuariService;

    @Autowired
    private UserService userService;

    @Autowired
    private FitxerService fitxerService;

    /**
     * @param username usuari de la base de dades (clau primaria de user)
     * @param relacioDTO amb el Id del fitxer i amb el username del usuari a compartir
     * @return L'usuari propietari amb l'estructura actualitzada amb un fitxer compartit un altre usuari
     */
    @PostMapping(path = "/add/{username}")
    public ResponseEntity<User> afegirRelacio(@PathVariable("username") String username,@RequestBody RelacioDTO relacioDTO) throws IOException {
        fitxerUsuariService.createFitxerUsuariCompartit(relacioDTO);
        return new ResponseEntity<>(userService.getUserByUserName(username),HttpStatus.OK);
    }

    /**
     * @param username usuari de la base de dades (clau primaria de user)
     * @param relacioDTO amb el Id del fitxer i amb el username del usuari a deixar de compartir
     * @return L'usuari propietari amb l'estructura actualitzada amb un fitxer que ha deixat de compartir a un altre usuari
     */
    @PostMapping(path = "/delete/{username}")
    public ResponseEntity<User> borrarRelacio(@PathVariable("username") String username,@RequestBody RelacioDTO relacioDTO) throws IOException {
        fitxerUsuariService.deleteFitxerUsuariCompartit(relacioDTO);
        return new ResponseEntity<>(userService.getUserByUserName(username),HttpStatus.OK);
    }

    /**
     * @param username usuari de la base de dades (clau primaria de user)
     * @return Llista de fitxers del usuari, ja siguin seus o li hagin compartit
     */
    @GetMapping("/{username}")
    public ResponseEntity<Collection<Fitxer>> getFitxersUsuarisByUsuari(@PathVariable("username") String username) {
        return new ResponseEntity<>(fitxerUsuariService.getListFitxerByUsuari(username), HttpStatus.OK);
    }

    /**
     * @param username usuari de la base de dades (clau primaria de user)
     * @return Llista de fitxers del usuari que  altres usuaris li han compartit
     */
    @GetMapping("/{username}/compartits")
    public ResponseEntity<Collection<Fitxer>> getFitxersCompartits(@PathVariable("username") String username) {

        return new ResponseEntity<>(fitxerUsuariService.getListFitxerCompartitsByUsuari(username),HttpStatus.OK);
    }

    /**
     * @param username usuari de la base de dades (clau primaria de user)
     * @return Llista de fitxers del usuari que li ha compartit amb altres usuaris
     */
    @GetMapping("{username}/fitxersto")
    public ResponseEntity<Collection<Fitxer>> getFitxersCompartitstoUsers(@PathVariable("username") String username){
        return new ResponseEntity<>(fitxerUsuariService.getListFitxerCompartitsToUsers(username),HttpStatus.OK);
    }

    /**
     * @param fitxerId del fitxer
     * @return Llista de usuaris amb els que s'ha compartit el fitxer
     */
    @GetMapping("/{id}/users")
    public ResponseEntity<Collection<User>> getListUsersCompartits(@PathVariable ("id") String fitxerId){
        return new ResponseEntity<>(fitxerUsuariService.getListUsersCompartits(fitxerId),HttpStatus.OK);
    }

    /**
     * @param fitxerID del fitxer
     * @return Usuari propietari del fitxer
     */
    @GetMapping("/{id}/propietari")
    public ResponseEntity<User> getUserPropietari(@PathVariable("id") String fitxerID){
        return new ResponseEntity<>(fitxerUsuariService.getUserPropietari(fitxerID),HttpStatus.OK);
    }
}
