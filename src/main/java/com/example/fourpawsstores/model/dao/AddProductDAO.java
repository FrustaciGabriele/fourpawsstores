package com.example.fourpawsstores.model.dao;

import com.example.fourpawsstores.exception.DAOException;
import com.example.fourpawsstores.model.domain.FactoryStore;
import com.example.fourpawsstores.model.domain.Product;
import com.example.fourpawsstores.model.domain.Profile;
import com.example.fourpawsstores.model.domain.Store;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AddProductDAO {
    public void AddProduct(Product prod) throws DAOException, SQLException {
        CallableStatement cs=null;
        try { Connection conn= ConnectionFactory.getConnection();
            cs=conn.prepareCall("{call inserisciProdotto(?,?,?,?,?)}");
            cs.setInt(1, Profile.getStoreId());
            cs.setString(2, prod.getName());
            cs.setString(3,prod.getDescription());
            cs.setBlob(4,prod.getImg());
            cs.setBigDecimal(5,prod.getPrice());
            cs.execute();


        }catch (SQLException e) {throw new DAOException("Error: " + e.getMessage());
        }finally {
            if(cs!= null){
                cs.close();
            }

        }
    }
}
