package com.online.files.online.files.controllers;

import com.online.files.online.files.models.fitxers.FitxerBD;
import com.online.files.online.files.services.FitxerBDService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public String addVideo(@RequestParam("title") String title,
                           @RequestParam("file") MultipartFile file, Model model) throws IOException {
        String id = fitxerBDService.createVideo(title, file);
        return "redirect:/videos/" + id;
    }

    @GetMapping("/{id}")
    public FitxerBD getVideo(@PathVariable String id, Model model) throws Exception {
        FitxerBD fitxerBD = fitxerBDService.getVideo(id);
        model.addAttribute("title", fitxerBD.getTitle());
        model.addAttribute("url", "/videos/stream/" + id);
        return fitxerBD;
    }

    @GetMapping("/stream/{id}")
    public void streamVideo(@PathVariable String id, HttpServletResponse response) throws Exception {
        FitxerBD fitxerBD = fitxerBDService.getVideo(id);
        FileCopyUtils.copy(fitxerBD.getStream(), response.getOutputStream());
    }

    @GetMapping()
    public Collection<FitxerBD> getVideos() throws IOException {
        return fitxerBDService.getVideos();
    }
}
