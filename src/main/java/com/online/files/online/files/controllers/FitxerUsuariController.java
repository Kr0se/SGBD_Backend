package com.online.files.online.files.controllers;

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


    /*@PostMapping(path = "/add")
    public String afegirRelacio(@RequestParam("fitxerID") String fitxerID, @RequestParam("userID") String userID, @RequestParam("esPropietari") Boolean esPropietari) throws IOException {

        FitxerUsuari fu = fitxerUsuariService.createFitxerUsuari(fitxerID,userID, esPropietari);
        Fitxer f = fitxerService.getFitxer(fitxerID);
        User u = userService.getUser(userID);

        fitxerService.addFitxerUser(f,fu);
        userService.addFitxerUser(u, fu);

        return "redirect:/fitxer:" + fu.getFitxerId() + "/user:" + fu.getUserId();
    }*/

    @GetMapping()
    public Collection<FitxerUsuari> getFitxersUsuaris(){ return fitxerUsuariService.getFitxersUsuaris();}

    @PostMapping("/usuari")
    public ResponseEntity<Collection<FitxerBD>> getFitxersUsuarisByUsuari(@RequestBody UserAuthDTO user) throws IOException {
        return new ResponseEntity<>(fitxerUsuariService.getListFitxerUsuariByUsuari(user), HttpStatus.OK);
    }

    @GetMapping("/fitxer")
    public Collection<FitxerUsuari> getFitxersUsuarisByFitxer(@RequestParam("fitxerID") String fitxerID){
        return fitxerUsuariService.getListFitxerUsuariByFitxer(fitxerID);
    }
}
