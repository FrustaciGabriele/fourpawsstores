package com.example.fourpawsstores.model.domain;

import com.example.fourpawsstores.model.bean.ProductBean;

import java.util.ArrayList;
import java.util.List;

public class Catalogue {
    private int storeid;
    private List <Product> productList= new ArrayList<>();
    public Catalogue(int id) {this.storeid=id;}

    public void addProduct(Product prod) {productList.add(prod);
    }

    public List<Product> getList() {return productList;
    }
    public int getidCat(){return storeid;}
}
