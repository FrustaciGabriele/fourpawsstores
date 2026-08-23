package com.example.fourpawsstores.model.dao;

import com.example.fourpawsstores.exception.DAOException;
import com.example.fourpawsstores.model.domain.FactoryStore;
import com.example.fourpawsstores.model.domain.ListOrder;
import com.example.fourpawsstores.model.domain.Order;
import com.example.fourpawsstores.model.domain.Store;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FindOrdersDao {
    public ListOrder getOrders(String username) throws DAOException, SQLException {
        System.out.println("user:"+username);
        ListOrder list=new ListOrder();
        CallableStatement cs=null;
        try { Connection conn= ConnectionFactory.getConnection();
            cs=conn.prepareCall("{call trovaordiniutente(?)}");
            cs.setString(1, username);
            boolean status=cs.execute();
            if (status){
                ResultSet rs=cs.getResultSet();
                while (rs.next()){
                    Order order = new Order();
                    order.setStoreId(rs.getInt(1));
                    order.setDate(rs.getTimestamp(2));
                    order.setTotal(rs.getBigDecimal(3));
                    order.setPayType(rs.getString(4));
                    order.setOrderId(rs.getInt(5));
                    order.setStateOrder(rs.getString(6));
                    order.addToProdId(rs.getInt(7));
                    order.addToQuantity(rs.getInt(8));
                    order.setClientId(username);
                    list.addOrder(order);
                }
            }

        }catch (SQLException e) {throw new DAOException("Error: " + e.getMessage());
        }finally {
            if(cs!= null){
                cs.close();
            }
        }
        return list;
    }
}
