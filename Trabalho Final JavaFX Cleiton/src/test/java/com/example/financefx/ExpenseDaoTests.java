
package com.example.financefx;

import com.example.financefx.dao.Database;
import com.example.financefx.dao.ExpenseDao;
import com.example.financefx.model.Expense;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class ExpenseDaoTests {

    static int userId = 1;
    ExpenseDao dao = new ExpenseDao();

    @Test
    void insert_and_delete_roundtrip() {
        int before = dao.listByUser(userId).size();
        Expense e = new Expense(0, "Teste JUnit", "Outros", 12.34, LocalDate.now().toString(), userId);
        int id = dao.insert(e);
        assertTrue(id > 0);

        int afterInsert = dao.listByUser(userId).size();
        assertEquals(before + 1, afterInsert);

        dao.delete(id, userId);
        int afterDelete = dao.listByUser(userId).size();
        assertEquals(before, afterDelete);
    }
}
