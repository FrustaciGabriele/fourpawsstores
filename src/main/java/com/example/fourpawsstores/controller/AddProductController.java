package com.example.fourpawsstores.controller;

import com.example.fourpawsstores.model.bean.ProductBean;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class AddProductController {
    public ProductBean newProduct() {
        ProductBean prodB = new ProductBean();
        return prodB;
    }

    public boolean checkName(String text) {
        int lenght =text.length();
        if(lenght==0||lenght>45){
            return false;
        }
        return true;
    }

    public boolean checkDescription(String text) {
        int lenght= text.length();
        if (lenght==0||lenght>200){
            return false;
        }
        return true;
    }

    public boolean checkPrice(String text) {
        try {
            BigDecimal price = new BigDecimal(text).setScale(2, RoundingMode.HALF_UP);
            if(price.compareTo(BigDecimal.ZERO)<=0){
            return false;
            }else{
                return true;
            }
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
