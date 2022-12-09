package com.online.files.online.files.models.fitxers;
import com.online.files.online.files.models.FitxerUsuari;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Document(collection = "fitxers")
public class Fitxer
{

    @Id
    private String id;

    private String nom;

    private String tipus;

    private Date dataPujada;

    private String fitxerBDId;

    @DBRef
    private List<FitxerUsuari> fitxerUsuariList = new ArrayList<>();

    public Fitxer(){}

    public Fitxer(String nom, String tipus, Date date, String idDB){
        this.nom = nom;
        this.tipus = tipus;
        this.dataPujada = date;
        this.fitxerBDId = idDB;
    }

    public Fitxer(Fitxer f){
        this.nom = f.nom;
        this.tipus = f.tipus;
        this.dataPujada = f.dataPujada;
        this.fitxerBDId = f.fitxerBDId;
    }

    public String getId() {
        return this.id;
    }

    public String getNom(){return this.nom;}

    public void setNom(String nom){ this.nom = nom;}

    public String getTipus(){return tipus;}

    public void setTipus(String tipus){this.tipus = tipus;}

    public Date getDataPujada(){return this.dataPujada;}

    public void setDataPujada(Date dataPujada) {
        this.dataPujada = dataPujada;
    }

    public String getFitxerDBId(){return this.fitxerBDId;}

    public void setFitxerBDId(String id){ this.fitxerBDId = id;}


    public void addFitxerUsuari(FitxerUsuari fu){
        fitxerUsuariList.add(fu);
    }

	public void removeFitxerUsuari(FitxerUsuari fu){
		fitxerUsuariList.remove(fu);
	}

    @Override
    public String toString(){
        return "Fitxer [id= " + id + ", nom= " + nom + ", Fitxer= " + fitxerBDId + "]";
    }
}
