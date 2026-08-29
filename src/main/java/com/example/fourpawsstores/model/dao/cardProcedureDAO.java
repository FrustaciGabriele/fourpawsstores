package com.example.fourpawsstores.model.dao;

import com.example.fourpawsstores.exception.DAOException;
import com.example.fourpawsstores.model.domain.Card;
import com.example.fourpawsstores.model.domain.Credentials;
import com.example.fourpawsstores.model.domain.Role;

import java.sql.*;

public class cardProcedureDAO {
    public Card getCardUser(String cardNum) throws DAOException, SQLException {
        CallableStatement cs = null;
        Card card =new Card();
        try {
            Connection conn = ConnectionFactory.getConnection();
            cs = conn.prepareCall("{call recuperaCarta(?)}");
            cs.setString(1, cardNum);
            ResultSet rs = cs.executeQuery();
            if(rs.next()){
            card= new Card(rs.getString(1),rs.getString(2),rs.getInt(3),rs.getString(4));}
        } catch(SQLException e) {
            throw new DAOException("Login error: " + e.getMessage());
        }finally {
            if(cs!= null){
                cs.close();
            }
        }
        return card;
    }
}
