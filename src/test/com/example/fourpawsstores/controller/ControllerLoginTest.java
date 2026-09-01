package com.example.fourpawsstores.controller;

import com.example.fourpawsstores.exception.DAOException;
import com.example.fourpawsstores.model.bean.CredentialsBean;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.io.IOException;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.fail;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ControllerLoginTest {
    ControllerLogin controllerlogin= null;

    @BeforeAll
    void setup(){controllerlogin=new ControllerLogin();}
    @Test
    void startSuccessfull() throws DAOException, SQLException, IOException {
        CredentialsBean cred= new CredentialsBean("gabbo","gabbo");
        try {
            controllerlogin.start(cred);
        }  catch (DAOException | SQLException | IOException e) {
        fail();
        }

    }
    @Test
    void startUnuccessfull() throws DAOException, SQLException, IOException {
        CredentialsBean cred= new CredentialsBean("dummy","dummy");
        try {
            controllerlogin.start(cred);
        }  catch (DAOException | SQLException | IOException e) {
            fail();
        }

    }
}
