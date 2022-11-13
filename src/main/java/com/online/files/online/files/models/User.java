package com.online.files.online.files.models;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.online.files.online.files.models.fitxers.FileText;

import java.util.Collection;
import java.util.List;

@Document(collection = "users")
public class User {

  @Id
  private String username;
  private String password;
  private String nom;
  private String cognom;
  private Carpeta mainCarpeta;


  @DBRef
  Collection<FileText> fileTextList;

  public User() {}

  public User(String username, String password, String nom, String cognom) {
    this.username = username;
    this.password = password;
    this.nom = nom;
    this.cognom = cognom;
    this.mainCarpeta = (new Carpeta("main"));
  }

  /**
   * Afageix una nova carpeta anomenada 'folderName' al final de l'estructura de 'subCarpetes'
   * @param subCarpetes
   * @param folderName
   * @throws Exception
   */
  public User updateCarpeta(List<String> subCarpetes, String folderName) throws Exception{
    Carpeta actual = this.mainCarpeta;
    for(String name : subCarpetes){ //Avançem els nivells de les subcarpetes
        actual = actual.getSubCarpeta(name);
        if(actual == null){ //no ha trobat cap subcarpeta que es digui "name"
            throw new Exception("No hi ha cap subcarpeta que es digui: " + folderName);
        }
    }

    if(actual.nameCarpetaTaken(folderName)){ // mirem que no hi hagi una carpeta amb el mateix nom
      throw new Exception(folderName + " ja existeix a aquest directori");
    }
    actual.addSubCarpeta(new Carpeta(folderName));
    
    return this;
  }

  public String getUsername() {
    return this.username;
  }

  public String getPassword() {
    return this.password;
  }

  public String getNom() {
    return this.nom;
  }

  public String getCognom() {
    return this.cognom;
  }

  public Carpeta getMainCarpeta() {
    return this.mainCarpeta;
  }
}