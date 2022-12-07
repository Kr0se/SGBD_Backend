package com.online.files.online.files.models.fitxers;

import org.springframework.data.mongodb.core.mapping.Document;

import java.io.InputStream;

@Document(collection = "videos")
public class FitxerBD {
    private String title;
    private String type;
    private InputStream stream;

    public FitxerBD(){}

    public FitxerBD(String title, InputStream stream){
        this.title = title;
        this.stream = stream;
    }

    public FitxerBD(FitxerBD v){
        this.title = v.title;
        this.stream = v.stream;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setType(String type){ this.type = type;}

    public void setStream(InputStream inputStream) {
        this.stream = inputStream;
    }

    public Object getTitle() {
        return this.title;
    }

    public String getType(){ return this.type;}

    public InputStream getStream() {
        return this.stream;
    }

    @Override
    public String toString(){
        return "Fitxer [title= " + title + ", stream= " + stream.toString() + "]";
    }
}
