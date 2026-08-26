package com.example.fourpawsstores.model.dao;

import com.example.fourpawsstores.exception.DAOException;
import com.example.fourpawsstores.model.domain.Credentials;
import com.example.fourpawsstores.model.domain.Profile;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProfileProcDAO {
    public void getProfile() throws DAOException, SQLException {
        CallableStatement cs;
        if (Credentials.getRole().getId()==2){
            try {
                Connection conn = ConnectionFactory.getConnection();
                cs = conn.prepareCall("{call recuperaProfiloUtente(?)}");
                cs.setString(1, Credentials.getUsername());
                boolean status = cs.execute();
                if (status) {
                    ResultSet rs = cs.getResultSet();
                    if (rs.next()) {
                        Profile.setUsername(rs.getString(1));
                        Profile.setName(rs.getString(2));
                        Profile.setCredit(rs.getString(3));
                    }
                }
            } catch (SQLException e) {
            throw new DAOException("recupera profilo error: " + e.getMessage());
            }
        }
        else{
            try {
            Connection conn = ConnectionFactory.getConnection();
            cs = conn.prepareCall("{call recuperaProfiloNegozio(?)}");
            cs.setString(1, Credentials.getUsername());
            boolean status = cs.execute();
            if (status) {
                ResultSet rs = cs.getResultSet();
                if (rs.next()) {
                    Profile.setUsername(rs.getString(1));
                    Profile.setName(rs.getString(2));
                    Profile.setIdStore(rs.getInt(3));
                }
            }
        } catch (SQLException e) {
            throw new DAOException("recupera profilo error: " + e.getMessage());
        }

        }
    }
}
