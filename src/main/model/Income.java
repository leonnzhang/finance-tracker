package model;

import java.util.Date;

// Represents an Income with fields inherited from Transaction abstract class.
// Also includes array of categories which includes the String representation of the category short inherited.
public class Income extends Transaction {
    private static final String[] categories = new String[]{ "Job (9-5, etc)",
            "Investments", "Other"}; // categories used to convert category short to String rep.

    // EFFECTS: constructor inherited from Transaction class; set category to "other"
    public Income() {
        super();
        this.category = categories.length - 1;
    }

    // REQUIRES: Income name has non-zero-length and Income amount has non-zero amount.
    // EFFECTS: amount is set to a, name is set to n, and category is set to c.
    public Income(double a, String n, int c) {
        super(a, n, c);
    }

    // REQUIRES: Income name has non-zero-length and Income amount has non-zero amount.
    // EFFECTS: number is set to number, amount is set to amount, name is set to name, category is set to category,
    // date is set to date.
    public Income(int number, double amount, String name, int category, long date) {
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
