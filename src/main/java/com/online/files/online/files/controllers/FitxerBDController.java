package com.online.files.online.files.controllers;

import com.online.files.online.files.models.fitxers.FitxerBD;
import com.online.files.online.files.services.FitxerBDService;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

@RequestMapping(path = "/videos")
@RestController
public class FitxerBDController {

    @Autowired
    FitxerBDService fitxerBDService;

    @PostMapping("/add")
    public String addFitxerBD(@RequestParam("title") String title,@RequestParam("type") String tipus,
                           @RequestParam("file") MultipartFile file, Model model) throws IOException {
        String id = fitxerBDService.createFitxerBD(file);
        return "redirect:/videos/" + id;
    }

    @PostMapping("/update")
    public String updateFitxerBD(@RequestParam("id") String id,
                                 @RequestParam("file") MultipartFile file, Model model) throws IOException {
        String idNou = fitxerBDService.updateFitxerBD(id,file);
        return "redirect:/videos/" + idNou;
    }

    @GetMapping("/{id}")
    public String getFitxerBD(@PathVariable String id, Model model) throws Exception {
        FitxerBD fitxerBD = fitxerBDService.getFitxerBD(id);
        model.addAttribute("title", fitxerBD.getTitle());
        model.addAttribute("url", "/videos/stream/" + id);
        return "videos";
    }

    @GetMapping("/stream/{id}")
    public void streamFitxerBD(@PathVariable String id, HttpServletResponse response) throws Exception {
        FitxerBD fitxerBD = fitxerBDService.getFitxerBD(id);
        FileCopyUtils.copy(fitxerBD.getStream(), response.getOutputStream());
    }

    @GetMapping()
    public Collection<FitxerBD> getFitxersBD() throws IOException {
        return fitxerBDService.getFitxersBD();
    }
}
