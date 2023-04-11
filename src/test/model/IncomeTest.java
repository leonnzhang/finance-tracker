package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class IncomeTest {
    private Income defaultIncome;
    private Income testIncome;

    @BeforeEach
    void runBefore() {
        defaultIncome = new Income();
        testIncome = new Income(15000, "Software Engineer Job @Google", 0);
    }

    @Test
    void testDefaultConstructor() {
        assertEquals(0, defaultIncome.getAmount());
        assertEquals("", defaultIncome.getName());
        assertEquals("Other", defaultIncome.getCategory());
        assertTrue(defaultIncome.getNumber() > 0);
    }

    @Test
    void testConstructor() {
        assertEquals(15000, testIncome.getAmount());
        assertEquals("Software Engineer Job @Google", testIncome.getName());
        assertEquals("Job (9-5, etc)", testIncome.getCategory());
        assertTrue(testIncome.getNumber() > 0);
    }

    @Test
    void testGetCategories() {
        String[] categories = new String[]{ "Job (9-5, etc)", "Investments", "Other"};
        for (int i = 0; i < Income.getCategories().length; i++) {
            assertTrue(Income.getCategories()[i].equals(categories[i]));
        }
    }

    @Test
    void testGetCategory() {
        assertTrue(defaultIncome.getCategory().equals("Other"));
        assertTrue(testIncome.getCategory().equals("Job (9-5, etc)"));
    }

    @Test
    void testToString() {
        assertTrue(defaultIncome.toString().contains("#1; Amount: 0.00, Name: , Category: Other"));
        assertTrue(testIncome.toString().contains("#2; Amount: 15000.00, Name: Software Engineer Job @Google, " +
                "Category: Job (9-5, etc)"));
    }
}
