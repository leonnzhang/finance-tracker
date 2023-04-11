package model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

// Represents a user with lists of expenses and incomes
public class User {
    private ArrayList<Expense> expenses; // list of Expenses
    private ArrayList<Income> incomes;  // list of Incomes

    // EFFECTS: list of expenses and list of incomes are both set to empty ArrayLists of their respective type
    public User() {
        this.expenses = new ArrayList<>();
        this.incomes = new ArrayList<>();
    }

    public void addExpense(Expense e) {
        this.expenses.add(e);
        EventLog.getInstance().logEvent(new Event("New expense \"" + e.getName() + "\" was added to User."));
    }

    public void addIncome(Income i) {
        this.incomes.add(i);
        EventLog.getInstance().logEvent(new Event("New income \"" + i.getName() + "\" was added to User."));
    }

    // EFFECTS: view income/expense analytics
    public double viewAnalytics() {
        double expenseTotal = getTotalExpenses();
        double incomeTotal = getTotalIncome();

        double netWorth = incomeTotal - expenseTotal;

        EventLog.getInstance().logEvent(
                new Event("Analytics were calculated for a net worth of: $" + netWorth + "."));
        return netWorth;
    }

    // EFFECTS: return all expenses
    public ArrayList<Expense> getExpenses() {
        return expenses;
    }

    // EFFECTS: return all incomes
    public ArrayList<Income> getIncomes() {
        return incomes;
    }

    // EFFECTS: add up all expenses and return it
    public double getTotalExpenses() {
        double expense = 0;
        for (int i  = 0; i < expenses.size(); i++) {
            expense += expenses.get(i).getAmount();
        }
        return expense;
    }

    // EFFECTS: add up all incomes and return it
    public double getTotalIncome() {
        double income = 0;
        for (int i  = 0; i < incomes.size(); i++) {
            income += incomes.get(i).getAmount();
        }
        return income;
    }

    // EFFECTS: returns all expenses and incomes as JSON object
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("expenses", expensesToJson());
        json.put("incomes", incomesToJson());

        return json;
    }

    // EFFECTS: returns all expenses as a JSON array
    private JSONArray expensesToJson() {
        JSONArray jsonArray = new JSONArray();

        for (int i = 0; i < this.expenses.size(); i++) {
            jsonArray.put(this.expenses.get(i).toJson());
        }

        return jsonArray;
    }

    // EFFECTS: returns all incomes as a JSON array
    private JSONArray incomesToJson() {
        JSONArray jsonArray = new JSONArray();

        for (int i = 0; i < this.incomes.size(); i++) {
            jsonArray.put(this.incomes.get(i).toJson());
        }

        return jsonArray;
    }
}
