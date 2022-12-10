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

    @PostMapping("/{username}")
    public ResponseEntity<Collection<FitxerBD>> getFitxersUsuarisByUsuari(@PathVariable("username") String username) throws IOException {
        return new ResponseEntity<>(fitxerUsuariService.getListFitxerBDByUsuari(username), HttpStatus.OK);
    }

    @PostMapping("/{username}/compartits")
    public ResponseEntity<Collection<FitxerBD>> getFitxersCompartits(@PathVariable("username") String username) throws IOException {

        return new ResponseEntity<>(fitxerUsuariService.getListFitxerBDCompartitsByUsuari(username),HttpStatus.OK);
    }

    @GetMapping("/fitxer")
    public Collection<FitxerUsuari> getFitxersUsuarisByFitxer(@RequestParam("fitxerID") String fitxerID){
        return fitxerUsuariService.getListFitxerUsuariByFitxer(fitxerID);
    }
}
