package com.online.files.online.files.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.online.files.online.files.models.fitxers.Photo;

import java.util.List;

@Repository
public interface PhotoRepository extends MongoRepository<Photo, String> {

    public List<Photo> findAll();
    public Photo findBytitle(String title);
}