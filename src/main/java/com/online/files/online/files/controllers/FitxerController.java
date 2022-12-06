package com.online.files.online.files.controllers;

import com.online.files.online.files.DTO.FitxerDTO;
import com.online.files.online.files.models.FitxerUsuari;
import com.online.files.online.files.models.User;
import com.online.files.online.files.models.fitxers.Fitxer;
import com.online.files.online.files.models.fitxers.FitxerBD;
import com.online.files.online.files.repositories.FitxerRepository;
import com.online.files.online.files.services.FitxerService;
import com.online.files.online.files.services.FitxerBDService;
import com.online.files.online.files.services.FitxerUsuariService;
import com.online.files.online.files.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.util.Collection;
import java.util.Date;

@RequestMapping(path = "/fitxers")
@RestController
public class FitxerController
{

    @Autowired
    private FitxerRepository fitxerRepository;

    @Autowired
    private FitxerBDService fitxerBDService;

    @Autowired
    private FitxerService fitxerService;

    @Autowired
    private FitxerUsuariService fitxerUsuariService;

    @Autowired
    private UserService userService;

    @GetMapping()
    public Collection<Fitxer> getFitxers(){ return fitxerService.getFitxers();}

    @PostMapping(path = "/{username}/upload")
    public ResponseEntity<Fitxer> pujarFitxer(@PathVariable("username") String username,
            @RequestParam("file") MultipartFile file, Model model) throws IOException {

        String id = fitxerBDService.createFitxerBD(file);
        Date d = fitxerBDService.getUploadDate(id);
        Fitxer f = fitxerService.createFitxer(file.getOriginalFilename(),file.getContentType().split("/")[0],d,id);
        fitxerUsuariService.createFitxerUsuari(f.getId(),username,true);
        return  new ResponseEntity<>(f,HttpStatus.OK);
    }

    @PostMapping("/fitxerBD")
    public ResponseEntity<FitxerBD> getFitxer(@RequestBody FitxerDTO fitxer) throws IOException {
        return new ResponseEntity<>(fitxerService.getFitxerBD(fitxer),HttpStatus.OK);
    }

    /*@GetMapping("/dataPujada")
    public Date getUploadData(@RequestParam("fitxerID") String fitxerId) throws IOException {

        String fitxerDBId = fitxerService.getFitxerBD(fitxerId);
        return fitxerBDService.getUploadDate(fitxerDBId);
    }*/

    /*
    * get relacions
    * borrar relacions de list del user
    * borrar relacions com a tal
    * borrar fitxer bd
    * borrar fitxer */
    @DeleteMapping()
    public ResponseEntity<Collection<User>> deleteFitxer(@RequestBody FitxerDTO fitxer){

        Collection<FitxerUsuari> fus = fitxerUsuariService.getListFitxerUsuariByFitxer(fitxer.getId());
        Collection<User> users = fitxerUsuariService.deleteFitxerUsuariOfUser(fus);
        fitxerService.deleteFitxer(fitxer.getId());
        return new ResponseEntity<>(users,HttpStatus.OK);
    }

    @PostMapping("/tipus")
    public ResponseEntity<Collection<FitxerBD>> getFitxerByTipus(@RequestBody FitxerDTO fitxer) throws IOException {
        return new ResponseEntity<>(fitxerService.getFitxerByTipus(fitxer.getTipus()),HttpStatus.OK);
    }

    @GetMapping("/data")
    public Collection<Fitxer> getFitxerByData(@RequestParam("data") Date data) {

        return fitxerService.getFitxerByDataPujada(data);
    }
    @GetMapping("/databetween")
    public Collection<Fitxer> getFitxerBetweenData(@RequestParam("data1") Date data1, @RequestParam("data1") Date data2) {

        return fitxerService.getFitxerByDataPujadaBetween(data1,data2);
    }
    @GetMapping("/nomstarts")
    public Collection<Fitxer> getFitxerByNomStarts(@RequestParam("nom") String nom) {

        return fitxerService.getFitxerByNomStartsWith(nom);
    }
    @GetMapping("/nomends")
    public Collection<Fitxer> getFitxerByNomEnds(@RequestParam("nom") String nom) {

        return fitxerService.getFitxerByNomEndsWith(nom);
    }

}