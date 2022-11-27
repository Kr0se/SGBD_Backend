package com.online.files.online.files.models;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Document(collection = "users")
public class User {

  @Id
  private String id;

  private String firstName;
  private String lastName;

  @DBRef
  private List<FitxerUsuari> fitxerUsuariList = new ArrayList<>();

  /*@DocumentReference
  Collection<Photo> photosList = new ArrayList<>();

  @DocumentReference
  Collection<Video> videosList = new ArrayList<>();*/

  public User() {}

  public User(String firstName, String lastName) {
    this.firstName = firstName;
    this.lastName = lastName;
  }

  public User(User u){
    this.id = u.id;
    this.firstName = u.firstName;
    this.lastName = u.lastName;
    this.fitxerUsuariList = u.fitxerUsuariList;
  }

  public String getId() {
    return this.id;
  }

  public String getFirstName(){return this.firstName;}

  public void addFitxerUsuari(FitxerUsuari fu){
    fitxerUsuariList.add(fu);
  }

  /*public void addPhoto(Photo p){
    photosList.add(p);
  }

  public void addVideo(Video v){
    videosList.add(v);
  }*/

  @Override
  public String toString(){
    return "User [id= " + id + ", firstName= " + firstName + ", lastName= " + lastName + ", filesText: " + fitxerUsuariList.toString() + "]";
  }

}