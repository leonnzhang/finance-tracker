package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ExpenseTest {
    private Expense defaultExpense;
    private Expense testExpense;

    @BeforeEach
    void runBefore() {
        defaultExpense = new Expense();
        testExpense = new Expense(32500.23, "Car", 1);
    }

    @Test
    void testDefaultConstructor() {
        assertEquals(0, defaultExpense.getAmount());
        assertEquals("", defaultExpense.getName());
        assertEquals("Other", defaultExpense.getCategory());
        assertTrue(defaultExpense.getNumber() > 0);
    }

    @Test
    void testConstructor() {
        assertEquals(32500.23, testExpense.getAmount());
        assertEquals("Car", testExpense.getName());
        assertEquals("Savings", testExpense.getCategory());
        assertTrue(testExpense.getNumber() > 0);
    }

    @Test
    void testGetCategories() {
        String[] categories = new String[]{ "Essentials (Housing, utilities, etc)", "Savings",
                "Investments", "Debt (Credit cards, loans, etc)", "Entertainment", "Eating out", "Clothing",
                "Other 'fun' Expenses", "Other" };
        for (int i = 0; i < Expense.getCategories().length; i++) {
            assertTrue(Expense.getCategories()[i].equals(categories[i]));
        }
    }

    @Test
    void testGetCategory() {
        assertTrue(defaultExpense.getCategory().equals("Other"));
        assertTrue(testExpense.getCategory().equals("Savings"));
    }

    @Test
    void testToString() {
        assertTrue(defaultExpense.toString().contains("#1; Amount: 0.00, Name: , Category: Other"));
        assertTrue(testExpense.toString().contains("#2; Amount: 32500.23, Name: Car, Category: Savings"));
    }
}
