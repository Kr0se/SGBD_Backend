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

    @PostMapping(path = "/add/{username}")
    public ResponseEntity<User> afegirRelacio(@PathVariable("username") String username,@RequestBody RelacioDTO relacioDTO) throws IOException {
        fitxerUsuariService.createFitxerUsuariCompartit(relacioDTO);
        return new ResponseEntity<>(userService.getUserByUserName(username),HttpStatus.OK);
    }

    @PostMapping(path = "/delete/{username}")
    public ResponseEntity<User> borrarRelacio(@PathVariable("username") String username,@RequestBody RelacioDTO relacioDTO) throws IOException {
        fitxerUsuariService.deleteFitxerUsuariCompartit(relacioDTO);
        return new ResponseEntity<>(userService.getUserByUserName(username),HttpStatus.OK);
    }

    @GetMapping()
    public Collection<FitxerUsuari> getFitxersUsuaris(){ return fitxerUsuariService.getFitxersUsuaris();}

    @GetMapping("/{username}")
    public ResponseEntity<Collection<Fitxer>> getFitxersUsuarisByUsuari(@PathVariable("username") String username) {
        return new ResponseEntity<>(fitxerUsuariService.getListFitxerByUsuari(username), HttpStatus.OK);
    }

    @GetMapping("/{username}/compartits")
    public ResponseEntity<Collection<Fitxer>> getFitxersCompartits(@PathVariable("username") String username) {

        return new ResponseEntity<>(fitxerUsuariService.getListFitxerCompartitsByUsuari(username),HttpStatus.OK);
    }

    @GetMapping("/fitxer")
    public Collection<FitxerUsuari> getFitxersUsuarisByFitxer(@RequestParam("fitxerID") String fitxerID){
        return fitxerUsuariService.getListFitxerUsuariByFitxer(fitxerID);
    }

    @GetMapping("{username}/fitxersto")
    public ResponseEntity<Collection<Fitxer>> getFitxersCompartitstoUsers(@PathVariable("username") String username){
        return new ResponseEntity<>(fitxerUsuariService.getListFitxerCompartitsToUsers(username),HttpStatus.OK);
    }

    @GetMapping("/{id}/users")
    public ResponseEntity<Collection<User>> getListUsersCompartits(@PathVariable ("id") String fitxerId){
        return new ResponseEntity<>(fitxerUsuariService.getListUsersCompartits(fitxerId),HttpStatus.OK);
    }

    @GetMapping("/{id}/propietari")
    public ResponseEntity<User> getUserPropietari(@PathVariable("id") String fitxerID){
        return new ResponseEntity<>(fitxerUsuariService.getUserPropietari(fitxerID),HttpStatus.OK);
    }
}
