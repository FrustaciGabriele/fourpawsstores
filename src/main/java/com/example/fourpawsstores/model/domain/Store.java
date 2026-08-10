package com.example.fourpawsstores.model.domain;


import java.sql.Blob;

public class Store {
    private int idStore;
    private String name;
    private String description;
    private Blob image;
    private String indirizzo;
    private Double lat;
    private Double lon;
    private int catalog;

    public void setid(int anInt) {this.idStore=anInt;
    }

    public void setname(String string) {this.name=string;
    }

    public void setDescription(String des) {this.description=des;
    }

    public void setImage(Blob img) {this.image=img;
    }

    public void setAddress(String addr) {this.indirizzo=addr;
    }

    public void setlat(double latD) {this.lat=latD;
    }

    public void setLon(double lonD) {this.lon=lonD;
    }

    public void setIdCatalog(int cat) {this.catalog=cat;
    }

    public int getid() { return idStore;}
    public String getName(){return name;}
    public String getDescription(){return description;}
    public Blob getImage(){return image;}
    public String getAddress(){return indirizzo;}
    public Double getLat(){return lat;}
    public Double getLon(){return lon;}
    public int getIdCatalog(){return catalog;}


}
