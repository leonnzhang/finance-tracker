package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class TransactionTest {
    private Expense testExpense;

    @BeforeEach
    void runBefore() {
        testExpense = new Expense();
    }

    @Test
    void testSetters() {
        testExpense.setAmount(123.45);
        assertEquals(123.45, testExpense.getAmount());
        testExpense.setName("Test123");
        assertTrue(testExpense.getName().equals("Test123"));
        testExpense.setCategory(2);
        assertEquals(testExpense.getCategory(), "Investments");
    }

    @Test
    void testGetDate() {
        Date date = new Date();
        testExpense.setDate(date.getTime());
        assertEquals(testExpense.getDate(), date);
    }
}
