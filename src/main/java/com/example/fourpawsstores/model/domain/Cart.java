package com.example.fourpawsstores.model.domain;

import com.example.fourpawsstores.model.bean.ProductBean;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Cart {
    private List<Product> list= new ArrayList<>();
    private List<Integer> numProd= new ArrayList<Integer>();
    private BigDecimal Total= BigDecimal.valueOf(0);

    public void addToCart(Product p, int q) {
        if (q==1){
            list.add(p);
            numProd.add(q);
            Total=Total.add(p.getPrice());
        }
        else if(q>1){
            numProd.set(list.indexOf(p), q);
            Total=Total.add(p.getPrice());
        }
    }

    public void deletefromCart(Product p, int q) {
        if (q>=1){
            numProd.set(list.indexOf(p), q);
            Total=Total.subtract(p.getPrice());
        }else if(q==0){
            Total=Total.subtract(p.getPrice());
            numProd.remove(list.indexOf(p));
            list.remove(p);
        }
    }

    public List<Product> getList() {return list;
    }
    public List<Integer> getListNumProd() {return numProd;
    }

    public BigDecimal getTot() {return Total;
    }
    public int getLenght(){return list.size();}
}
