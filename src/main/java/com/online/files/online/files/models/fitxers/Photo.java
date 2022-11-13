package com.online.files.online.files.models.fitxers;

import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "photos")
public class Photo {
    @Id
    private String id;

    private String title;

    private Binary image;

    private Date uploadDate;

    public Photo(String title) {
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Binary getImage(){
        return image;
    }

    public void setImage(Binary binary) {
        this.image = binary;
    }
}
