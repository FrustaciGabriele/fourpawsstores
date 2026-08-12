package com.example.fourpawsstores.model.dao;

import com.example.fourpawsstores.exception.DAOException;
import com.example.fourpawsstores.model.domain.Order;
import com.example.fourpawsstores.model.domain.Product;

import java.sql.*;

public class insertOrderDAO {
 public void insertOnDB(Order order) throws DAOException, SQLException {
  System.out.println("CIAOO");
  CallableStatement cs=null;
  try { Connection conn= ConnectionFactory.getConnection();
   cs=conn.prepareCall("{call inserisciOrdine(?,?,?,?,?,?,?)}");
   cs.setInt(1,order.getStoreId());
   cs.setString(2,order.getUserId());
   cs.setTimestamp(3,order.getDate());
   cs.setBigDecimal(4,order.getTotal());
   cs.setString(5,order.getTypeOfPay());
   cs.setString(6,order.getState());
   cs.registerOutParameter(7,Types.INTEGER);
   cs.execute();
   order.setOrderId(cs.getInt(7));

  }catch (SQLException e) {throw new DAOException("Error: " + e.getMessage());
  }
  try { Connection conn= ConnectionFactory.getConnection();
   String sql = "INSERT INTO prodottiordini (idordini, idprodotto, quantita, prezzo) VALUES (?, ?, ?, ?)";
   System.out.println("SQL da eseguire: " + sql);
   PreparedStatement ps = conn.prepareStatement(sql);

   for (Product p : order.getListProduct()) {
    ps.setInt(1, order.getOrderId());
    ps.setInt(2, p.getId());
    ps.setInt(3, order.getQuantity().get(order.getListProduct().indexOf(p)));
    ps.setBigDecimal(4, p.getPrice());
    ps.addBatch();
   }

   ps.executeBatch();

  }
  catch (SQLException e) {throw new DAOException("Error: " + e.getMessage());
  } finally{
  } {
   if(cs!= null){
    cs.close();
   }

  }
 }
}
