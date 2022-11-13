package com.online.files.online.files.controllers;

import com.online.files.online.files.models.fitxers.Photo;
import com.online.files.online.files.services.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.Collection;

@RequestMapping(path="/photos")
@RestController
public class PhotoController {

    @Autowired
    PhotoService photoService;

    @PostMapping("/add")
    public String addPhoto(@RequestParam("title") String title,
                           @RequestParam("image") MultipartFile image, Model model)
            throws IOException {
        String id = photoService.addPhoto(title, image);
        return "redirect:/photos/" + id;
    }

    @GetMapping()
    public Collection<Photo> getPhotos(){
        return photoService.getPhotos();
    }

    @GetMapping(path = "/{id}")
    public String getPhoto(@PathVariable("id") String id, Model model) {
        Photo photo = photoService.getPhotoId(id);
        model.addAttribute("title", photo.getTitle());
        model.addAttribute("image",
                Base64.getEncoder().encodeToString(photo.getImage().getData()));
        return "photos";
    }

}
