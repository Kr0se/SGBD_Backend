package com.online.files.online.files.models.fitxers;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "filesText")
public class FileText {

    @Id
    private String id;

    private String title;
    private String content;

    public FileText(String title){ this.title=title;}

    public String getId() {
        return id;
    }

    public String getFileName() {
        return title;
    }

    public void setFileText(String content){ this.content = content; }

    public String getContent(){ return content;}

}
