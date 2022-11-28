package com.online.files.online.files.models;
import com.online.files.online.files.models.fitxers.Fitxer;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.online.files.online.files.models.fitxers.FitxerBD;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Document(collection = "carpetes")
public class Carpeta {

  @Id
  private String nom;
  private List<Carpeta> subCarpetes;
  private List<Fitxer> fitxers;


  @DBRef
  Collection<Fitxer> fitxerList;

  public Carpeta() {}

  public Carpeta(String nom) {
    this.nom = nom;
    this.subCarpetes = new ArrayList<>();
    this.fitxers = new ArrayList<>();
  }

  /**
   * Només mira en un nivell
   * @return true si ja hi ha un subcarpeta amb el nom "name". False altrament
   */
  public Boolean existeixSubcarpeta(String name){
    for(Carpeta c : this.subCarpetes){
      if(c.getNom().equals(name)) return true;
    }
    return false;
  }

  public void addSubCarpeta(Carpeta c) {
    this.subCarpetes.add(c);
  }

  public Carpeta getSubCarpeta(String name) {
    for(Carpeta c : this.subCarpetes){
      if(c.nom.equals(name)){
        return c;
      }
    }
    return null;
  }

  public void deleteCarpeta(String name) {
    Carpeta objectiu = null;
    for(Carpeta c : this.subCarpetes){
      if(c.nom.equals(name)){
        objectiu = c;
      }
    }

    if(objectiu != null) this.subCarpetes.remove(objectiu);
  }

  public String getNom() {
    return this.nom;
  }

  public List<Carpeta> getSubCarpetes() {
    return this.subCarpetes;
  }

  public List<Fitxer> getVideos() {
    return fitxers;
  }
}