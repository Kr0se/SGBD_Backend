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
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
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

    @Autowired
    FitxerBDService fitxerBDService;

    @PostMapping(path = "/add")
    public String afegirRelacio(@RequestParam("fitxerID") String fitxerID, @RequestParam("userID") String userID, @RequestParam("esPropietari") Boolean esPropietari) throws IOException {

        FitxerUsuari fu = fitxerUsuariService.createFitxerUsuari(fitxerID,userID, esPropietari);
        Fitxer f = fitxerService.getFitxer(fitxerID);
        User u = userService.getUser(userID);

        fitxerService.addFitxerUser(f,fu);
        userService.addFitxerUser(u, fu);

        return "redirect:/fitxer:" + fu.getFitxerId() + "/user:" + fu.getUserId();
    }

    @GetMapping()
    public Collection<FitxerUsuari> getFitxersUsuaris(){ return fitxerUsuariService.getFitxersUsuaris();}

    @GetMapping("/usuari")
    public Collection<FitxerBD> getFitxersUsuarisByUsuari(@RequestBody UserAuthDTO user) throws IOException {
        Collection<FitxerBD> toReturn = new ArrayList<>();
        Collection<FitxerUsuari> fus = fitxerUsuariService.getListFitxerUsuariByUsuari(user);
        for(FitxerUsuari fu:fus){
            String id = fitxerService.getFitxerBD(fu.getFitxerId());
            toReturn.add(fitxerBDService.getFitxerBD(id));
        }
        return toReturn;
    }

    @GetMapping("/fitxer")
    public Collection<FitxerUsuari> getFitxersUsuarisByFitxer(@RequestParam("fitxerID") String fitxerID){
        return fitxerUsuariService.getListFitxerUsuariByFitxer(fitxerID);
    }
}
