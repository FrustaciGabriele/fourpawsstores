package com.example.fourpawsstores.model.dao;

import com.example.fourpawsstores.exception.DAOException;
import com.example.fourpawsstores.model.bean.OrderBean;
import com.example.fourpawsstores.model.domain.*;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FindOrdersDao {
    public ListOrder getOrders(String username) throws DAOException, SQLException {
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

    public Order getCompleteOrder(OrderBean order) throws DAOException, SQLException {
        Order compOrder = new Order(order.getStoreIdB(),order.getUserIdB(),order.getTypeOfPayB(),order.getTotalB(),order.getStateB(),order.getDateB(),order.getOrderIdB());
        CallableStatement cs=null;
        try { Connection conn= ConnectionFactory.getConnection();
            cs=conn.prepareCall("{call trovaprodottiordine(?)}");
            cs.setInt(1, order.getOrderIdB());
            boolean status=cs.execute();
            if (status){
                ResultSet rs=cs.getResultSet();
                while (rs.next()){
                    Product prod= new Product();
                    prod.setid(rs.getInt(1));
                    prod.setPrice(rs.getBigDecimal(3));
                    prod.setName(rs.getString(4));
                    prod.setDescription(rs.getString(5));
                    prod.setImage(rs.getBlob(6));
                    compOrder.addToProdId(prod.getId());
                    compOrder.addToQuantity(rs.getInt(2));
                    compOrder.addToProductList(prod);

                }
            }

        }catch (SQLException e) {throw new DAOException("Error: " + e.getMessage());
        }finally {
            if(cs!= null){
                cs.close();
            }
        }
        return compOrder;
    }
}
