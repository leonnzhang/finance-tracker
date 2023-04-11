package persistence;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import model.Expense;
import model.Income;
import model.User;
import org.json.*;

// JsonReader class modelled from example on edx
// Represents a reader that reads workroom from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads User from file and returns it;
    // throws IOException if an error occurs reading data from file
    public User read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseUser(jsonObject);
    }

    // EFFECTS: reads source file as String and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses User from JSON object and returns it
    private User parseUser(JSONObject jsonObject) {
        User user = new User();
        addExpenses(user, jsonObject);
        addIncomes(user, jsonObject);
        return user;
    }

    // MODIFIES: user
    // EFFECTS: parses expenses from JSON object and adds them to workroom
    private void addExpenses(User user, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("expenses");
        for (Object json : jsonArray) {
            JSONObject nextExpense = (JSONObject) json;
            addExpense(user, nextExpense);
        }
    }

    // MODIFIES: user
    // EFFECTS: parses expenses from JSON object and adds it to user
    private void addExpense(User user, JSONObject jsonObject) {
        int number = jsonObject.getInt("number");
        double amount = jsonObject.getDouble("amount");
        String name = jsonObject.getString("name");
        int category = jsonObject.getInt("category");
        long date = jsonObject.getLong("date");

        Expense expense = new Expense(number, amount, name, category, date);

        user.addExpense(expense);
    }

    // MODIFIES: user
    // EFFECTS: parses expenses from JSON object and adds them to workroom
    private void addIncomes(User user, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("incomes");
        for (Object json : jsonArray) {
            JSONObject nextIncome = (JSONObject) json;
            addIncome(user, nextIncome);
        }
    }

    // MODIFIES: user
    // EFFECTS: parses expenses from JSON object and adds it to user
    private void addIncome(User user, JSONObject jsonObject) {
        int number = jsonObject.getInt("number");
        double amount = jsonObject.getDouble("amount");
        String name = jsonObject.getString("name");
        int category = jsonObject.getInt("category");
        long date = jsonObject.getLong("date");

        Income income = new Income(number, amount, name, category, date);

        user.addIncome(income);
    }
}
