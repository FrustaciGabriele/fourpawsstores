package com.example.fourpawsstores.model.domain;

import java.math.BigDecimal;
import java.sql.Blob;

public class Product {
    private int id;
    private String name;
    private String description;
    private Blob img;
    private BigDecimal price;
    private int stock;
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

    public void setNum(int num) {this.stock=num;
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

    public int getStoc() {return this.stock;
    }
}
