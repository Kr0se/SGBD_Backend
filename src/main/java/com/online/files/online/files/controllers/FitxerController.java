package com.online.files.online.files.controllers;

import com.online.files.online.files.models.FitxerUsuari;
import com.online.files.online.files.models.fitxers.Fitxer;
import com.online.files.online.files.models.fitxers.FitxerBD;
import com.online.files.online.files.repositories.FitxerRepository;
import com.online.files.online.files.services.FitxerService;
import com.online.files.online.files.services.FitxerBDService;
import com.online.files.online.files.services.FitxerUsuariService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    @PostMapping("/add")
    public String addFitxer(@RequestParam("title") String title,@RequestParam("type") String tipus,
                            @RequestParam("file") MultipartFile file, Model model) throws IOException {
        String id = fitxerBDService.createFitxerBD(title,tipus, file);
        Date d = fitxerBDService.getUploadDate(id);
        Fitxer f = fitxerService.createFitxer(title,tipus,d,id);
        return  "redirect:/fitxers/" + f.getNom();
    }

    @GetMapping("/fitxerBD")
    public FitxerBD getFitxer(@RequestParam("fitxerID") String fitxerId) throws IOException {

        String fitxerDBId = fitxerService.getFitxerBD(fitxerId);
        return fitxerBDService.getFitxerBD(fitxerDBId);

    }

    @GetMapping("/data")
    public Date getUploadData(@RequestParam("fitxerID") String fitxerId) throws IOException {

        String fitxerDBId = fitxerService.getFitxerBD(fitxerId);
        return fitxerBDService.getUploadDate(fitxerDBId);
    }

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

}