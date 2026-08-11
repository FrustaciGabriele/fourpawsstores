package com.example.fourpawsstores.model.dao;

import com.example.fourpawsstores.exception.DAOException;
import com.example.fourpawsstores.model.bean.ListStoresBean;
import com.example.fourpawsstores.model.bean.StoreBeans;
import com.example.fourpawsstores.model.bean.coordinateBean;
import com.example.fourpawsstores.model.domain.Coordinate;
import com.example.fourpawsstores.model.domain.FactoryStore;
import com.example.fourpawsstores.model.domain.ListStores;
import com.example.fourpawsstores.model.domain.Store;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FindStoresDAO {
    public ListStores FindStores(Coordinate coordinate) throws DAOException, SQLException {
        ListStores ListStores= new ListStores();
        CallableStatement cs=null;
        try { Connection conn= ConnectionFactory.getConnection();
            cs=conn.prepareCall("{call cercanegozi(?,?)}");
            cs.setDouble(1, coordinate.getlat());
            cs.setDouble(2, coordinate.getlon());
            boolean status=cs.execute();
            if (status){
                ResultSet rs=cs.getResultSet();
                while (rs.next()){
                    Store store = FactoryStore.CreateStore();
                    store.setid(rs.getInt(1));
                    store.setname(rs.getString(2));
                    store.setDescription(rs.getString(3));
                    store.setImage(rs.getBlob(4));
                    store.setAddress(rs.getString(5));
                    store.setlat(rs.getDouble(6));
                    store.setLon(rs.getDouble(7));
                    store.setIdCatalog(rs.getInt(8));
                    store.setTel(rs.getString(9));
                    ListStores.addStore(store);

                }
            }

        }catch (SQLException e) {throw new DAOException("Error: " + e.getMessage());
    }finally {
        if(cs!= null){
            cs.close();
        }

    }
        return ListStores;
}
}


