package com.online.files.online.files.controllers;

import com.online.files.online.files.DTO.FitxerDTO;
import com.online.files.online.files.models.FitxerUsuari;
import com.online.files.online.files.models.fitxers.Fitxer;
import com.online.files.online.files.models.fitxers.FitxerBD;
import com.online.files.online.files.repositories.FitxerRepository;
import com.online.files.online.files.services.FitxerService;
import com.online.files.online.files.services.FitxerBDService;
import com.online.files.online.files.services.FitxerUsuariService;
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

    @GetMapping()
    public Collection<Fitxer> getFitxers(){ return fitxerService.getFitxers();}

    /*@PostMapping(path = "/add", value = "/data", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Fitxer> addFitxer(@RequestParam("title") String title, @RequestParam("type") String tipus,
                                    @RequestParam("file") MultipartFile file, Model model) throws IOException {
        String id = fitxerBDService.createFitxerBD(title,tipus, file);
        Date d = fitxerBDService.getUploadDate(id);
        Fitxer f = fitxerService.createFitxer(title,tipus,d,id);
        return  "redirect:/fitxers/" + f.getNom();
    }*/

    @PostMapping("/fitxerBD")
    public ResponseEntity<FitxerBD> getFitxer(@RequestBody FitxerDTO fitxer) throws IOException {

        String fitxerDBId = fitxerService.getFitxerBD(fitxer);
        return new ResponseEntity<>(fitxerBDService.getFitxerBD(fitxerDBId),HttpStatus.OK);

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
    public String deleteFitxer(@RequestParam("fitxerID") String fitxerId){

        Collection<FitxerUsuari> fus = fitxerUsuariService.getListFitxerUsuariByFitxer(fitxerId);
        fitxerUsuariService.deleteFitxerUsuariOfUser(fus);
        fitxerService.deleteFitxer(fitxerId);
        return "Fitxer amb ID: " + fitxerId + " s'ha esborrat correctament";
    }

    @GetMapping("/tipus")
    public Collection<Fitxer> getFitxerByTipus(@RequestParam("tipus") String tipus) {
        return fitxerService.getFitxerByTipus(tipus);
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