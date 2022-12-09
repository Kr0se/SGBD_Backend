package com.online.files.online.files.DTO;

import java.util.Date;

public class FitxerDTO {
    private String id;
    private String nom;
    private String nouNom;
    private String tipus;
    private Date dataPujada;
    private Date dataCerca2;
    private String fitxerBDId;
    private String path;

    public String getId(){ return id;}
    public void setId(String id){this.id = id;}
    public String getNom(){ return nom;}
    public void setNom(String nom){this.nom = nom;}
    public String getNouNom(){ return nouNom;}
    public void setNouNom(String nouNom){this.nouNom = nouNom;}
    public String getTipus(){return  tipus;}
    public void setTipus(String tipus){this.tipus = tipus;}
    public Date getDataPujada(){return dataPujada;}
    public void setDataPujada(Date dataPujada){this.dataPujada = dataPujada;}
    public Date getDataCerca2(){return dataCerca2;}
    public void setDataCerca2(Date dataCerca2){this.dataCerca2 = dataCerca2;}
    public String getFitxerBDId(){return fitxerBDId;}
    public void setFitxerBDId(String fitxerBDId){this.fitxerBDId = fitxerBDId;}
    public String getPath() {
        return path;
    }
    public void setPath(String path) {
        this.path = path;
    }
}
