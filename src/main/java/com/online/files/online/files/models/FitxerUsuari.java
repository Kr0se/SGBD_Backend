package com.online.files.online.files.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "relacions")
public class FitxerUsuari {

    @Id
    private String id;

    private String fitxerId;
    private String userId;

    //private Fitxer fitxer;
    //private User user;

    public FitxerUsuari(){}

    public FitxerUsuari(String fitxerId, String userId){
        this.fitxerId = fitxerId;
        this.userId = userId;
        //this.fitxer = fitxer;
        //this.user = user;
    }

    public FitxerUsuari(FitxerUsuari fu){
        this.id = fu.id;
        this.fitxerId = fu.fitxerId;
        this.userId = fu.userId;
        //this.fitxer = fu.fitxer;
        //this.user = fu.user;
    }

    public String getId() {
        return this.id;
    }

    public String getFitxerId(){ return fitxerId;}

    public String getUserId(){ return userId;}

    @Override
    public String toString(){
        return "FitxerUsuari [id= " + id + ", Fitxer= " + fitxerId + ", Usuari= " + userId + "]";
    }
}
