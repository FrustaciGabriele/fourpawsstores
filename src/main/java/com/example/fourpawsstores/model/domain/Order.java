package com.example.fourpawsstores.model.domain;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class Order {
    private int storeId;
    private String clientId;
    private List<Product> listProduct= new ArrayList<>();
    private List<Integer> listProdId=new ArrayList<>();
    private List<Integer> quantity=new ArrayList<>();
    private String typeOfPayment;
    private BigDecimal total;
    private String stateOrder;
    private Timestamp date;
    private int orderId;

    public void addToProductList(Product p) {listProduct.add(p);
    }

    public void addToQuantity(int i) {quantity.add(i);
    }
    public void addToProdId(int j) {listProdId.add(j);
    }
    public void setStoreId(int sId) {this.storeId=sId;
    }

    public void setPayType(String paymentType) { this.typeOfPayment=paymentType;
    }

    public void setClientId(String username) {clientId=username;
    }
    public void setTotal(BigDecimal tot) {total=tot;
    }
    public void setStateOrder(String state) {stateOrder=state;
    }

    public void setDate(Timestamp time) {this.date=time;
    }
    public void setOrderId(int OId) {this.orderId=OId;
    }
    public int getStoreId() { return storeId;
    }

    public String getUserId() { return clientId;
    }

    public Timestamp getDate() { return date;
    }

    public BigDecimal getTotal() {return  total;
    }

    public String getTypeOfPay() { return typeOfPayment;
    }

    public String getState() {return stateOrder;
    }

    public List<Product> getListProduct() {return listProduct;
    }
    public int getOrderId() {return orderId;
    }

    public List<Integer> getQuantity() {return quantity;
    }
    public List<Integer> getListProdId() {return listProdId;
    }
}
