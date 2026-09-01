package com.example.fourpawsstores.dao;

import com.example.fourpawsstores.exception.DAOException;
import com.example.fourpawsstores.model.dao.FindOrdersDao;
import com.example.fourpawsstores.model.domain.ListOrder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.fail;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class FindOrderDAOTest {
    @Test
    void getOrdersTest(){
        ListOrder orders= null;
        try {
            orders= new FindOrdersDao().getOrders("gabbo");
        } catch (DAOException | SQLException e) {
            fail();
        }
        assertNotEquals(null, orders);
    }

}
