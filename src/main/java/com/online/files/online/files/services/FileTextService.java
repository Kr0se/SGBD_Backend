package com.online.files.online.files.services;

import com.online.files.online.files.models.fitxers.FileText;
import com.online.files.online.files.repositories.FileTextRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class FileTextService {
    @Autowired
    private FileTextRepository textRepo;

    public String addFileText(String title, String content){
        FileText text = new FileText(title);
        text.setFileText(content);
        text = textRepo.insert(text);
        return text.getId();
    }

    public Collection<FileText> getFilesText(){
        return textRepo.findAll();
    }
}
