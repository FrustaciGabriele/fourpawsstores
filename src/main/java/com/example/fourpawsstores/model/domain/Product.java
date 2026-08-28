package com.example.fourpawsstores.model.domain;

import java.math.BigDecimal;
import java.sql.Blob;

public class Product {
    private int id;
    private String name;
    private String description;
    private Blob img;
    private BigDecimal price;
    public Product(){
    }
    public Product(int idB, String nameB, String descriptionB, Blob image, BigDecimal priceB) {
        this.id=idB;
        this.name=nameB;
        this.description=descriptionB;
        this.img=image;
        this.price=priceB;
    }
    public Product( String nameB, String descriptionB, Blob image, BigDecimal priceB) {
        this.name=nameB;
        this.description=descriptionB;
        this.img=image;
        this.price=priceB;
    }


    public void setid(int pid) { this.id=pid;
    }

    public void setName(String nameP) {this.name=nameP;
    }

    public void setDescription(String desP) {this.description=desP;
    }

    public void setImage(Blob imgP) {this.img=imgP;
    }

    public void setPrice(BigDecimal priceP) {this.price=priceP;
    }


    public int getId() {return this.id;
    }

    public String getName() {return this.name;
    }

    public String getDescription() {return this.description;
    }

    public Blob getImg() {return this.img;
    }

    public BigDecimal getPrice() {return this.price;
    }


}
