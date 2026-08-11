package com.example.fourpawsstores.model.bean;

import java.util.ArrayList;
import java.util.List;

public class CatalogueBean {
    private List<ProductBean> listbean= new ArrayList<>();
    private int storeId;
    public CatalogueBean(int id) {this.storeId=id;
    }

    public void addProductBean(ProductBean prodB) {
        listbean.add(prodB);
    }

    public List<ProductBean> getListProdB() {return listbean;
    }
}
