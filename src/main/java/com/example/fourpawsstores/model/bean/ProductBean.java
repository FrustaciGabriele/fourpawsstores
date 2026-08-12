package com.example.fourpawsstores.model.bean;

import java.math.BigDecimal;
import java.sql.Blob;

public class ProductBean {
    private int idB;
    private String nameB;
    private String descriptionB;
    private Blob imgB;
    private BigDecimal priceB;
    public ProductBean(int id, String name, String description, Blob img, BigDecimal price) {
        this.idB=id;
        this.nameB=name;
        this.descriptionB=description;
        this.imgB=img;
        this.priceB=price;
    }

    public String getNameB() {return nameB;}

    public String getDescriptionB() {return descriptionB;
    }

    public Blob getImage() { return imgB;
    }

    public BigDecimal getPriceB() {return priceB;
    }

    public int getId() {return idB;
    }

}
