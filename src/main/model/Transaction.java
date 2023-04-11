package model;

import org.json.JSONObject;

import java.util.Date;

// Abstract class Transaction,
// represents a Transaction with transaction number, amount (in dollars), name, category, and date.
public abstract class Transaction {
    protected static int nextTransactionNumber = 1; // tracks next transaction number
    protected int number;                           // transaction number
    protected double amount;                        // transaction amount (in dollars)
    protected String name;                          // transaction name
    protected int category;                         // int representation of category (later converted)
    protected long date;                            // date/time transaction is added to app

    // EFFECTS: transaction number is a unique natural number; amount on account is set to 0; name on account is set
    // to blank; category is set to -1; date is set to current date and time.
    public Transaction() {
        this.number = nextTransactionNumber++;
        this.amount = 0;
        this.name = "";
        this.category = -1;
        this.date = new Date().getTime();
    }

    // REQUIRES: transaction name has a non-zero length
    // EFFECTS: transaction number is a unique natural number; amount on account is set to amount; name on account is
    // set to name; category is set to category; date is set to current date and time.
    public Transaction(double amount, String name, int category) {
        this.number = nextTransactionNumber++;
        this.amount = amount;
        this.name = name;
        this.category = category;
        this.date = new Date().getTime();
    }

    // REQUIRES: transaction name has a non-zero length
    // EFFECTS: transaction number is a unique natural number; amount on account is set to amount; name on account is
    // set to name; category is set to category; date is set to current date and time.
    public Transaction(int number, double amount, String name, int category, long date) {
        this.number = number;
        this.amount = amount;
        this.name = name;
        this.category = category;
        this.date = date;
        nextTransactionNumber++;
    }

    public int getNumber() {
        return this.number;
    }

    public double getAmount() {
        return this.amount;
    }

    public String getName() {
        return this.name;
    }

    public Date getDate() {
        return new Date(this.date);
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public void setDate(long date) {
        this.date = date;
    }

    // EFFECTS: converts short category to String representation according to concrete class
    public abstract String getCategory();

    // EFFECTS: returns string rep. of transaction
    @Override
    public abstract String toString();

    // EFFECTS: returns JSON object representation of Transaction
    public JSONObject toJson() {
        JSONObject json = new JSONObject();

        json.put("number", this.number);
        json.put("amount", this.amount);
        json.put("name", this.name);
        json.put("category", this.category);
        json.put("date", this.date);

        return json;
    }
}
