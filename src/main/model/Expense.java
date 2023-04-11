package model;

import java.util.Date;

// Represents an Expense with fields inherited from Transaction abstract class.
// Also includes array of categories which includes the String representation of the category int inherited.
public class Expense extends Transaction {
    private static final String[] categories = new String[]{ "Essentials (Housing, utilities, etc)", "Savings",
            "Investments", "Debt (Credit cards, loans, etc)", "Entertainment", "Eating out", "Clothing",
            "Other 'fun' Expenses", "Other" }; // categories used to convert category int to String rep.

    // EFFECTS: constructor inherited from Transaction class; set category to "other"
    public Expense() {
        super();
        this.category = categories.length - 1;
    }

    // REQUIRES: Expense name has non-zero-length and Expense amount has non-zero amount.
    // EFFECTS: amount is set to a, name is set to n, and category is set to c.
    public Expense(double a, String n, int c) {
        super(a, n, c);
    }

    // REQUIRES: Income name has non-zero-length and Expense amount has non-zero amount.
    // EFFECTS: number is set to number, amount is set to amount, name is set to name, category is set to category,
    // date is set to date.
    public Expense(int number, double amount, String name, int category, long date) {
        super(number, amount, name, category, date);
    }

    // EFFECTS: override toString method from Transaction abstract class
    @Override
    public String toString() {
        return String.format(new Date(this.date) + ", #%d; Amount: %.2f, Name: %s, Category: %s",
                this.number, this.amount, this.name, this.getCategory());
    }

    // EFFECTS: override getCategory from Transaction abstract class
    @Override
    public String getCategory() {
        return categories[category];
    }

    public static String[] getCategories() {
        return categories;
    }
}
