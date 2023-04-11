package persistence;

import model.Income;
import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonReaderTest {
    private User user;

    @BeforeEach
    void runBefore() {
        user = new User();
    }

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/inexistentFile.json");
        try {
            User user = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testEmptyUser() {
        JsonWriter writer = new JsonWriter("./data/testEmptyUser.json"); // create json file needed
        JsonReader reader = new JsonReader("./data/testEmptyUser.json");
        try {
            writer.open();
            writer.write(user);
            writer.close();

            user = reader.read();

            assertEquals(0, user.getTotalExpenses());
            assertEquals(0, user.getTotalIncome());
            assertEquals(0, user.getExpenses().size());
            assertEquals(0, user.getIncomes().size());

        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testTwoIncomesUser() {
        try {
            user.addIncome(new Income(100, "One Hundred Dollars", 2));
            user.addIncome(new Income(200, "Two hundred dollars", 0));
            JsonWriter writer = new JsonWriter("./data/testTwoIncomesUser.json");
            writer.open();
            writer.write(user);
            writer.close();

            JsonReader reader = new JsonReader("./data/testTwoIncomesUser.json");
            user = reader.read();

            assertEquals(100, user.getIncomes().get(0).getAmount());
            assertEquals(200, user.getIncomes().get(1).getAmount());

            assertEquals("One Hundred Dollars", user.getIncomes().get(0).getName());
            assertEquals("Two hundred dollars", user.getIncomes().get(1).getName());

            assertEquals("Other", user.getIncomes().get(0).getCategory());
            assertEquals("Job (9-5, etc)", user.getIncomes().get(1).getCategory());

            assertEquals(2, user.getIncomes().size());
            assertEquals(300, user.getTotalIncome());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
