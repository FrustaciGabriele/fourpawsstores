package com.example.fourpawsstores.model.domain;

import com.example.fourpawsstores.exception.DAOException;
import com.example.fourpawsstores.model.bean.CartBean;
import com.example.fourpawsstores.model.bean.ProductBean;
import com.example.fourpawsstores.model.dao.FindOrdersDao;
import com.example.fourpawsstores.model.dao.insertOrderDAO;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class FacadeCreateOrder {
    private ListOrder list;
    public Order createOrder(Cart cart, int storeid, String paymentType) {
        Order order= new Order();
        for(Product p: cart.getList()){
            Product prod=new Product(p.getId(),p.getName(),p.getDescription(),p.getImg(),p.getPrice());
            order.addToProductList(prod);
            order.addToProdId(p.getId());
        }
        for(int i: cart.getListNumProd()){
            order.addToQuantity(i);
        }
        order.setStoreId(storeid);
        order.setPayType(paymentType);
        order.setClientId(Credentials.getUsername());
        order.setTotal(cart.getTot());
        order.setStateOrder("in attesa");
        order.setDate(Timestamp.valueOf(LocalDateTime.now()));
        return order;
    }

    public void insertOrder(Order newOrder) throws DAOException, SQLException {
        new insertOrderDAO().insertOnDB(newOrder);
    }

    public ListOrder findOrders() throws DAOException, SQLException {
        String username=Profile.getUsername();
        System.out.println("username:"+username);
         list= new FindOrdersDao().getOrders(username);
         return list;
    }
}
