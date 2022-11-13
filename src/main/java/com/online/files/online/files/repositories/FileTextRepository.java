package com.online.files.online.files.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.online.files.online.files.models.fitxers.FileText;

import java.util.List;

@Repository
public interface FileTextRepository extends MongoRepository<FileText,String> {

    public List<FileText> findAll();
    public FileText findBytitle(String title);
}