package com.online.files.online.files.models;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;


@Document(collection = "fitxers")
public class Fitxer
{

    @Id
    private String id;

    private String nom;

    //private String tipus;

    private String fitxerDBId;

    @DBRef
    private List<FitxerUsuari> fitxerUsuariList = new ArrayList<>();

    public Fitxer(){}

    public Fitxer(String nom, String idDB){
        this.nom = nom;
        this.fitxerDBId = idDB;
    }

    public String getId() {
        return this.id;
    }

    public String getNom(){return this.nom;}

    public String getFitxerDBId(){return this.fitxerDBId;}

    public void addFitxerUsuari(FitxerUsuari fu){
        fitxerUsuariList.add(fu);
    }

    @Override
    public String toString(){
        return "Fitxer [id= " + id + ", nom= " + nom + ", Fitxer= " + fitxerDBId + "]";
    }
}
