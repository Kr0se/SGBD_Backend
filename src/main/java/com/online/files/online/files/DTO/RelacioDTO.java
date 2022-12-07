package com.online.files.online.files.DTO;

public class RelacioDTO {
    private String id;
    private String fitxerId;
    private String userId;
    private Boolean esPropietari;

    public String getId(){ return id;}
    public void setId(String id){ this.id = id;}
    public String getFitxerId(){ return fitxerId;}
    public void setFitxerId(String fitxerId){ this.fitxerId = fitxerId;}
    public String getUserId(){ return userId;}
    public void setUserId(String userId){ this.userId = userId;}
    public Boolean getEsPropietari(){ return esPropietari;}
    public void setEsPropietari(Boolean esPropietari){ this.esPropietari = esPropietari;}
}
