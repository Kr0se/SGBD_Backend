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
    private Boolean esPropietari;

    public FitxerUsuari(){}

    public FitxerUsuari(String fitxerId, String userId, Boolean propietari){
        this.fitxerId = fitxerId;
        this.userId = userId;
        this.esPropietari = propietari;
    }

    public FitxerUsuari(FitxerUsuari fu){
        this.id = fu.id;
        this.fitxerId = fu.fitxerId;
        this.userId = fu.userId;
        this.esPropietari = fu.esPropietari;
    }

    public String getId() {
        return this.id;
    }

    public String getFitxerId(){ return fitxerId;}

    public String getUserId(){ return userId;}

    public Boolean getEsPropietari(){return esPropietari;}

    @Override
    public String toString(){
        return "FitxerUsuari [id= " + id + ", Fitxer= " + fitxerId + ", Usuari= " + userId + ", EsPropietari= " + esPropietari + "]";
    }
}
