package com.online.files.online.files.models;
import com.online.files.online.files.models.fitxers.Fitxer;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "carpetes")
public class Carpeta {

  @Id
  private String nom;
  private List<Carpeta> subCarpetes;
  private List<Fitxer> fitxers;

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

  public void renameCarpeta(String name, String newFolderName) {
    Carpeta objectiu = null;
    int i = 0;
    for(Carpeta c : this.subCarpetes){
      if(c.nom.equals(name)){
        objectiu = c;
        break;
      }
      i++;
    }

    if(objectiu != null){
      /*this.subCarpetes.remove(objectiu);
      objectiu.nom = newFolderName;
      this.subCarpetes.add(objectiu);*/

      this.subCarpetes.get(i).nom = newFolderName;
    }
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

  public List<Fitxer> getFiles() {
    return fitxers;
  }

  public boolean existFiles(String fNom) {
    for (Fitxer fitxer : fitxers) {
      if (fitxer.getNom().equals(fNom))
        return true;
    }
    return false;
  }

  public Carpeta addFile(Fitxer f){
    fitxers.add(f);
    return this;
  }

  public Carpeta removeFile(String fNom){
    for (Fitxer fitxer : fitxers) {
      if (fitxer.getNom().equals(fNom)){
        fitxers.remove(fitxer);
        return this;
      }
    }
    return this;
  }
}