package com.example.fourpawsstores.model.dao;

import com.example.fourpawsstores.exception.DAOException;
import com.example.fourpawsstores.model.domain.Catalogue;
import com.example.fourpawsstores.model.domain.FactoryStore;
import com.example.fourpawsstores.model.domain.Product;
import com.example.fourpawsstores.model.domain.Store;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GetCatalogueDAO {


    public Catalogue findCat(int id) throws DAOException, SQLException {
        Catalogue cat= new Catalogue(id);
        CallableStatement cs=null;
        try { Connection conn= ConnectionFactory.getConnection();
            cs=conn.prepareCall("{call cercaprodotti(?)}");
            cs.setInt(1, id);
            boolean status=cs.execute();
            if (status){
                ResultSet rs=cs.getResultSet();
                while (rs.next()){
                    Product prod=new Product();
                    prod.setid(rs.getInt(1));
                    prod.setName(rs.getString(2));
                    prod.setDescription(rs.getString(3));
                    prod.setImage(rs.getBlob(4));
                    prod.setPrice(rs.getBigDecimal(5));
                    prod.setState(rs.getString(6));
                    cat.addProduct(prod);
                }
            }

        }catch (SQLException e) {throw new DAOException("Error: " + e.getMessage());
        }finally {
            if(cs!= null){
                cs.close();
            }

        }
        return cat;
    }

}
