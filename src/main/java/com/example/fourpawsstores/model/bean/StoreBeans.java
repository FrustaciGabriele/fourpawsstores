package com.example.fourpawsstores.model.bean;

import java.sql.Blob;

public class StoreBeans {
    private int idStore;
    private String name;
    private String description;
    private Blob image;
    private String indirizzo;
    private Double lat;
    private Double lon;
    private int catalog;
    private String tel;
    public StoreBeans(){
        
    }

    public StoreBeans(int id, String nameB, String descriptionB, Blob imageB, String addressB, Double latB, Double lonB, int idCatalogB,String telB) {
        this.idStore=id;
        this.name=nameB;
        this.description=descriptionB;
        this.image=imageB;
        this.indirizzo=addressB;
        this.lat=latB;
        this.lon=lonB;
        this.catalog=idCatalogB;
        this.tel=telB;
    }
    public int getid() { return idStore;}
    public String getName(){return name;}
    public String getDescription(){return description;}
    public Blob getImage(){return image;}
    public String getAddress(){return indirizzo;}
    public Double getLat(){return lat;}
    public Double getLon(){return lon;}
    public int getIdCatalog(){return catalog;}
    public String getTel(){return tel;}
}
