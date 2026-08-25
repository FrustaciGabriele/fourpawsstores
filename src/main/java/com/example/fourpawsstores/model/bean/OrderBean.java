package com.example.fourpawsstores.model.bean;

import com.example.fourpawsstores.controller.OrderController;
import com.example.fourpawsstores.model.domain.Product;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class OrderBean {
        private int storeIdB;
        private String clientIdB;
        private List<ProductBean> listProductB= new ArrayList<>();
        private List<Integer> listProdIdB=new ArrayList<>();
        private List<Integer> quantityB=new ArrayList<>();
        private String typeOfPaymentB;
        private BigDecimal totalB;
        private String stateOrderB;
        private Timestamp dateB;
        private int orderIdB;

        public OrderBean(int sId,String cId,String pay, BigDecimal tot, String state, Timestamp date, int ordid){
                this.storeIdB=sId;
                this.clientIdB=cId;
                this.typeOfPaymentB=pay;
                this.totalB=tot;
                this.stateOrderB=state;
                this.dateB=date;
                this.orderIdB=ordid;
        }

        public void addToProductListB(ProductBean p) {listProductB.add(p);
        }

        public void addToQuantityB(int i) {quantityB.add(i);
        }
        public void addToProdIdB(int j) {listProdIdB.add(j);
        }
        public void setStoreIdB(int sId) {this.storeIdB=sId;
        }

        public void setPayTypeB(String paymentType) { this.typeOfPaymentB=paymentType;
        }

        public void setClientIdB(String username) {clientIdB=username;
        }
        public void setTotalB(BigDecimal tot) {totalB=tot;
        }
        public void setStateOrderB(String state) {stateOrderB=state;
        }

        public void setDateB(Timestamp time) {this.dateB=time;
        }
        public void setOrderIdB(int OId) {this.orderIdB=OId;
        }
        public int getStoreIdB() { return storeIdB;
        }

        public String getUserIdB() { return clientIdB;
        }

        public Timestamp getDateB() { return dateB;
        }
        public BigDecimal getTotalB() {return  totalB;
        }

        public String getTypeOfPayB() { return typeOfPaymentB;
        }

        public String getStateB() {return stateOrderB;
        }

        public List<ProductBean> getListProductB() {return listProductB;
        }
        public int getOrderIdB() {return orderIdB;
        }

        public List<Integer> getQuantityB() {return quantityB;
        }

        public void setListProductB(List<Product> listProduct) {

        }
}
