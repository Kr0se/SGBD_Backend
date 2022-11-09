package com.online.files.online.files.models;

import org.springframework.data.mongodb.core.mapping.Document;

import java.io.InputStream;

@Document(collection = "videos")
public class Video {
    private String title;
    private InputStream stream;

    public void setTitle(String title) {
        this.title = title;
    }

    public void setStream(InputStream inputStream) {
        this.stream = inputStream;
    }

    public Object getTitle() {
        return this.title;
    }

    public InputStream getStream() {
        return this.stream;
    }
}
