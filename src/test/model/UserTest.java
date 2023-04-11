package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class UserTest {
    private User testUser;

    @BeforeEach
    void runBefore() {
        testUser = new User();
    }

    @Test
    void testConstructor() {
        assertEquals(testUser.getExpenses(), new ArrayList<>());
        assertEquals(testUser.getIncomes(), new ArrayList<>());
    }

    @Test
    void testGetExpenses() {
        ArrayList<Expense> testExpenseArray = new ArrayList<>();

        assertEquals(testUser.getExpenses(), new ArrayList<>());

        Expense testExpense = new Expense(49.99, "Expense 1", (short) 0);

        testExpenseArray.add(testExpense);
        testUser.addExpense(testExpense);

        assertTrue(testUser.getExpenses().equals(testExpenseArray));
    }

    @Test
    void testGetIncomes() {
        ArrayList<Income> testIncomeArray = new ArrayList<>();

        assertEquals(testUser.getIncomes(), new ArrayList<>());

        Income testIncome = new Income(69.99, "Income 1", (short) 0);

        testIncomeArray.add(testIncome);
        testUser.addIncome(testIncome);

        assertTrue(testUser.getIncomes().equals(testIncomeArray));
    }

    @Test
    void testGetTotalExpenses() {
        assertEquals(testUser.getTotalExpenses(), 0);

        testUser.addExpense(new Expense(100, "Name", (short) 0));
        assertEquals(testUser.getTotalExpenses(), 100);

        testUser.addExpense(new Expense(400, "Name", (short) 0));
        assertEquals(testUser.getTotalExpenses(), 500);
    }

    @Test
    void testGetTotalIncome() {
        assertEquals(testUser.getTotalIncome(), 0);

        testUser.addIncome(new Income(525.94, "Name", (short) 0));
        assertEquals(testUser.getTotalIncome(), 525.94);

        testUser.addIncome(new Income(720.1, "Name", (short) 0));
        assertEquals(testUser.getTotalIncome(), 525.94+720.1);
    }

    @Test
    void testViewAnalytics() {
        assertEquals(testUser.viewAnalytics(), 0);
        testUser.addExpense(new Expense(123.45, "Test", 0));
        assertEquals(testUser.viewAnalytics(), -123.45);
        testUser.addIncome(new Income(987.65, "Test 2", 0));
        assertEquals(testUser.viewAnalytics(), 987.65 - 123.45);
    }
}
