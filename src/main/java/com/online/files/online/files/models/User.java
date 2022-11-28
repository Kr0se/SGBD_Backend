package com.online.files.online.files.models;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "users")
public class User {

  @Id
  private String id;
  private String username;
  private String password;
  private String nom;
  private String cognom;
  private Carpeta mainCarpeta;

  @DBRef
  private List<FitxerUsuari> fitxerUsuariList = new ArrayList<>();

  public User() {}

  public User(String username, String password, String nom, String cognom) {
    this.username = username;
    this.password = password;
    this.nom = nom;
    this.cognom = cognom;
    this.mainCarpeta = (new Carpeta("main"));
  }

  public User(User u){
    this.id = u.id;
    this.username = u.username;
    this.password = u.password;
    this.nom = u.nom;
    this.cognom = u.cognom;
    this.fitxerUsuariList = u.fitxerUsuariList;
  }

  /**
   * Afageix una nova carpeta anomenada 'folderName' al final de l'estructura de 'subCarpetes'
   * @param subCarpetes
   * @param folderName
   * @throws Exception
   */
  public User addFolder(List<String> subCarpetes, String folderName) throws Exception{
    Carpeta actual = this.mainCarpeta;
    for(String name : subCarpetes){ //Avançem els nivells de les subcarpetes
      actual = actual.getSubCarpeta(name);
      if(actual == null){ //no ha trobat cap subcarpeta que es digui "name"
        throw new Exception("No hi ha cap subcarpeta que es digui: " + name);
      }
    }

    if(actual.existeixSubcarpeta(folderName)){ // mirem que no hi hagi una carpeta amb el mateix nom
      throw new Exception(folderName + " ja existeix a aquest directori");
    }
    actual.addSubCarpeta(new Carpeta(folderName));

    return this;
  }
  /**
   * Elimina la carpeta anomenada 'folderName' de l'estructura de 'subCarpetes'
   * @param subCarpetes
   * @param folderName
   * @throws Exception
   */
  public User removeFolder(List<String> subCarpetes, String folderName) throws Exception{
    Carpeta actual = this.mainCarpeta;
    for(String name : subCarpetes){ //Avançem els nivells de les subcarpetes
      actual = actual.getSubCarpeta(name);
      if(actual == null){ //no ha trobat cap subcarpeta que es digui "name"
        throw new Exception("No hi ha cap subcarpeta que es digui: " + name);
      }
    }

    if(!actual.existeixSubcarpeta(folderName)){ // si no existeix la carpeta que volem borrar
      throw new Exception(folderName + " no existeix");
    }

    actual.deleteCarpeta(folderName);

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


  public String getId() {
    return this.id;
  }

  public String getCognom() {
    return this.cognom;
  }

  public void addFitxerUsuari(FitxerUsuari fu){
    fitxerUsuariList.add(fu);
  }

  public Carpeta getMainCarpeta() {
    return this.mainCarpeta;
  }

  @Override
  public String toString(){
    return "User [id= " + id + ", username= " + username + ", nom= " + nom + ", filesText: " + fitxerUsuariList.toString() + "]";
  }

}