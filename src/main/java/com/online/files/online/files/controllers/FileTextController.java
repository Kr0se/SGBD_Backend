package com.online.files.online.files.controllers;

import com.online.files.online.files.models.FileText;
import com.online.files.online.files.services.FileTextService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RequestMapping(path = "/filesText")
@RestController
public class FileTextController {

    @Autowired
    FileTextService textService;

    @PostMapping("/add")
    public String addFileText(@RequestParam("title") String title,
                              @RequestParam("content") String content){
        String id = textService.addFileText(title,content);
        return "redirect:/fileText/" + id;
    }

    @GetMapping()
    public Collection<FileText> getFilesText(){ return textService.getFilesText();}
}
