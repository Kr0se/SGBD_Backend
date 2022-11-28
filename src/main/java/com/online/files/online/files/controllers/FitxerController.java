package com.online.files.online.files.controllers;

import com.online.files.online.files.models.fitxers.Fitxer;
import com.online.files.online.files.models.fitxers.FitxerBD;
import com.online.files.online.files.repositories.FitxerRepository;
import com.online.files.online.files.services.FitxerService;
import com.online.files.online.files.services.FitxerBDService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collection;

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

    @GetMapping()
    public Collection<Fitxer> getFitxers(){ return fitxerService.getFitxers();}

    @PostMapping("/add")
    public String addFitxer(@RequestParam("title") String title,
                            @RequestParam("file") MultipartFile file, Model model) throws IOException {
        String id = fitxerBDService.createVideo(title, file);
        Fitxer f = fitxerService.createFitxer(title,id);
        return  "redirect:/fitxers/" + f.getNom();
    }

    @GetMapping("/fitxerBD")
    public FitxerBD getFitxer(@RequestParam("fitxerID") String fitxerId) throws IOException {

        String fitxerDBId = fitxerService.getFitxerBD(fitxerId);
        return fitxerBDService.getVideo(fitxerDBId);

    }
}