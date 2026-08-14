package com.example.fourpawsstores.model.bean;

import com.example.fourpawsstores.model.domain.Product;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class CartBean {
    private List<ProductBean> list= new ArrayList<>();
    private List<Integer> numProd= new ArrayList<Integer>();
    private BigDecimal Total= BigDecimal.valueOf(0);

    public void addToCart(ProductBean p, int q) {
        if (q==1){
            list.add(p);
            numProd.add(q);
            Total=Total.add(p.getPriceB());
        }
        else if(q>1){
            numProd.set(list.indexOf(p), q);
            Total=Total.add(p.getPriceB());
        }
    }

    public void deletefromCart(ProductBean p, int q) {
        if (q>=1){
            numProd.set(list.indexOf(p), q);
            Total=Total.subtract(p.getPriceB());
        }else if(q==0){
            Total=Total.subtract(p.getPriceB());
            numProd.remove(list.indexOf(p));
            list.remove(p);
        }
    }

    public List<ProductBean> getList() {return list;
    }
    public List<Integer> getListNumProd() {return numProd;
    }

    public BigDecimal getTot() {return Total;
    }
    public int getLenght(){return list.size();}
}
