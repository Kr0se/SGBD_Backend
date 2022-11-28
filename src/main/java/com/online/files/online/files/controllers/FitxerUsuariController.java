package com.online.files.online.files.controllers;

import com.online.files.online.files.models.fitxers.Fitxer;
import com.online.files.online.files.models.FitxerUsuari;
import com.online.files.online.files.models.User;
import com.online.files.online.files.services.FitxerService;
import com.online.files.online.files.services.FitxerUsuariService;
import com.online.files.online.files.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Collection;

@RequestMapping(path = "/fitxersUsuaris")
@RestController
public class FitxerUsuariController {

    @Autowired
    FitxerUsuariService fitxerUsuariService;

    @Autowired
    UserService userService;

    @Autowired
    FitxerService fitxerService;

    @PostMapping(path = "/add")
    public String afegirRelacio(@RequestParam("fitxerID") String fitxerID, @RequestParam("userID") String userID) throws IOException {

        FitxerUsuari fu = fitxerUsuariService.createFitxerUsuari(fitxerID,userID);
        Fitxer f = fitxerService.getFitxer(fitxerID);
        User u = userService.getUser(userID);

        fitxerService.addFitxerUser(f,fu);
        userService.addFitxerUser(u, fu);

        return "redirect:/fitxer:" + fu.getFitxerId() + "/user:" + fu.getUserId();
    }

    @GetMapping()
    public Collection<FitxerUsuari> getFitxersUsuaris(){ return fitxerUsuariService.getFitxersUsuaris();}

    @GetMapping("/usuari")
    public Collection<FitxerUsuari> getFitxersUsuarisByUsuari(@RequestParam("userID") String userID){
        return fitxerUsuariService.getListFitxerUsuariByUsuari(userID);
    }

    @GetMapping("/fitxer")
    public Collection<FitxerUsuari> getFitxersUsuarisByFitxer(@RequestParam("fitxerID") String fitxerID){
        return fitxerUsuariService.getListFitxerUsuariByFitxer(fitxerID);
    }
}
