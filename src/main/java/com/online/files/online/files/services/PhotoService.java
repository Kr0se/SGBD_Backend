package com.online.files.online.files.services;

import com.online.files.online.files.models.Photo;
import com.online.files.online.files.repositories.PhotoRepository;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collection;

@Service
public class PhotoService {

    @Autowired
    private PhotoRepository photoRepo;

    public String addPhoto(String title, MultipartFile file) throws IOException {
        Photo photo = new Photo(title);
        photo.setImage(
                new Binary(BsonBinarySubType.BINARY, file.getBytes()));
        photo = photoRepo.insert(photo); return photo.getId();
    }

    public Photo getPhotoId(String id) {
        return photoRepo.findById(id).get();
    }

    public Photo getPhoto(String title){
        return photoRepo.findBytitle(title);
    }

    public Collection<Photo> getPhotos () {
        return photoRepo.findAll();
    }
}
