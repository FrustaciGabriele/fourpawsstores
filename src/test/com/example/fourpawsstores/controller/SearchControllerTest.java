package com.example.fourpawsstores.controller;

import com.example.fourpawsstores.exception.DAOException;
import com.example.fourpawsstores.model.bean.ListStoresBean;
import com.example.fourpawsstores.model.bean.addressBean;
import com.example.fourpawsstores.model.domain.ListStores;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.io.IOException;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SearchControllerTest {
    SearchController controller= null;

    @BeforeAll
    void setup(){controller=new SearchController();
   }
   @Test
    void obtainStoreSuccessful(){
        addressBean address=new addressBean("Via del corso 60");
        ListStoresBean list=null;
        try{
           list= controller.obtainStores(address);
        } catch (DAOException | SQLException | IOException e) {
            throw new RuntimeException(e);
        }
       assertNotEquals(null, list);
    }
    @Test
    void obtainStoreUnsuccessful(){
        addressBean address2=new addressBean("sdaefcecacefa");
        ListStoresBean list2=null;
        try{
            list2= controller.obtainStores(address2);
        } catch (DAOException | SQLException | IOException e) {
            throw new RuntimeException(e);
        }
        assertNotEquals(null, list2);
    }
}
