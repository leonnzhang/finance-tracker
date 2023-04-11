package persistence;

import model.Expense;
import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonWriterTest {
    User user;

    @BeforeEach
    void runBefore() {
        user = new User();
    }

    @Test
    void testWriterInvalidFile() {
        try {
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testDefaultFile() {
        try {
            JsonWriter writer = new JsonWriter();
            writer.open();
            writer.write(user);
            writer.close();

            JsonReader reader = new JsonReader("./data/default.json");
            user = reader.read();
            assertEquals(0, user.getTotalExpenses());
            assertEquals(0, user.getTotalIncome());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterEmptyUser() {
        try {
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyUser.json");
            writer.open();
            writer.write(user);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyUser.json");
            user = reader.read();
            assertEquals(0, user.getTotalExpenses());
            assertEquals(0, user.getTotalIncome());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testTwoExpensesUser() {
        try {
            user.addExpense(new Expense(100, "One Hundred Dollars", 3));
            user.addExpense(new Expense(200, "Two hundred dollars", 8));
            JsonWriter writer = new JsonWriter("./data/testTwoExpensesUser.json");
            writer.open();
            writer.write(user);
            writer.close();

            JsonReader reader = new JsonReader("./data/testTwoExpensesUser.json");
            user = reader.read();

            assertEquals(100, user.getExpenses().get(0).getAmount());
            assertEquals(200, user.getExpenses().get(1).getAmount());

            assertEquals("One Hundred Dollars", user.getExpenses().get(0).getName());
            assertEquals("Two hundred dollars", user.getExpenses().get(1).getName());

            assertEquals("Debt (Credit cards, loans, etc)", user.getExpenses().get(0).getCategory());
            assertEquals("Other", user.getExpenses().get(1).getCategory());

            assertEquals(2, user.getExpenses().size());
            assertEquals(300, user.getTotalExpenses());

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
