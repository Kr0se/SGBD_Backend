package com.online.files.online.files.DTO;

import java.util.Date;

public class FitxerDTO {
    private String id;
    private String nom;
    private String tipus;
    private Date dataPujada;
    private String fitxerBDId;

    public String getId(){ return id;}
    public void setId(String id){this.id = id;}
    public String getNom(){ return nom;}
    public void setNom(String nom){this.nom = nom;}
    public String getTipus(){return  tipus;}
    public void setTipus(String tipus){this.tipus = tipus;}
    public Date getDataPujada(){return dataPujada;}
    public void setDataPujada(Date dataPujada){this.dataPujada = dataPujada;}
    public String getFitxerBDId(){return fitxerBDId;}
    public void setFitxerBDId(String fitxerBDId){this.fitxerBDId = fitxerBDId;}
}
