package ui;

import model.Expense;
import model.Income;
import model.User;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

// Personal Finance Application - Equity
public class EquityApp {
    private static final String JSON_STORE = "./data/user.json";
    private Scanner input;
    private User user;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // EFFECTS: runs Equity app
    public EquityApp() throws FileNotFoundException {
        init();

        runApp();
    }

    // MODIFIES: this
    // EFFECTS: initializes scanner, user, and persistence related objects
    public void init() {
        input = new Scanner(System.in);
        user = new User();

        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
    }

    // MODIFIES: this
    // EFFECTS: process user input
    public void runApp() {
        mainMenu();
        boolean continueRunning = true;
        String currentCommand = null;

        while (continueRunning) {
            currentCommand = input.nextLine().toLowerCase();

            if (currentCommand.equals("8")) {
                continueRunning = false;
            } else {
                handleCommands(currentCommand);
            }
        }

        System.out.println("Thanks for being an active Equity user. See you next time!");
    }

    // EFFECTS: display menu of options to user
    private void mainMenu() {
        System.out.println("Welcome to Equity");
        System.out.println("Enter the number of the respective action you would like to do.");
        System.out.println("1. Create a new expense");
        System.out.println("2. Create a new pay cheque");
        System.out.println("3. View Expenses");
        System.out.println("4. View income");
        System.out.println("5. View financial analytics");
        System.out.println("6. Load previous session");
        System.out.println("7. Save current session");
        System.out.println("8. Exit");
    }

    // MODIFIES: this
    // EFFECTS: handle all user commands from main menu
    private void handleCommands(String cmd) {
        if (cmd.equals("1")) {
            // create new expense
            createExpense();
        } else if (cmd.equals("2")) {
            // create new pay cheque (add to income)
            createIncome();
        } else if (cmd.equals("3")) {
            // view expenses
            viewExpenses();
        } else if (cmd.equals("4")) {
            // view income
            viewIncomes();
        } else if (cmd.equals("5")) {
            // view analytics
            viewAnalytics();
        } else if (cmd.equals("6")) {
            // load
            loadUser();
        } else if (cmd.equals("7")) {
            // save
            saveUser();
        } else {
            // not valid selection
            System.out.println("Not a valid selection. Please try again!");
        }
        mainMenu();
    }

    // MODIFIES: this
    // EFFECTS: creates a new Expense and adds to all expenses
    private void createExpense() {
        System.out.println("Creating a new expense...");

        System.out.println("How much was the expense (amount)?");
        double amount = input.nextDouble();
        input.nextLine(); // scanner bug i think?

        System.out.println("What is the name of the expense?");
        String name = input.nextLine();

        System.out.println("Which category of expense is this?");
        pickExpenseCategory();
        int category = input.nextInt();
        category = category < Expense.getCategories().length ? category : Expense.getCategories().length - 1;
        input.nextLine(); // scanner bug again i believe

        Expense newExpense = new Expense(amount, name, (short) category);

        user.addExpense(newExpense);
    }

    // EFFECTS: print all Expense categories for user to view
    private void pickExpenseCategory() {
        for (int i = 0; i < Expense.getCategories().length; i++) {
            System.out.println(i + ". " + Expense.getCategories()[i]);
        }
    }

    // MODIFIES: this
    // EFFECTS: creates a new Income/pay cheque and adds to all incomes
    private void createIncome() {
        System.out.println("Creating a new pay cheque...");

        System.out.println("How much was the pay cheque (amount)?");
        double amount = input.nextDouble();
        input.nextLine(); // scanner bug

        System.out.println("What is the name of the pay?");
        String name = input.nextLine();

        System.out.println("Which category of pay is this?");
        createIncomeCategory();
        int category = input.nextInt();
        category = category < Income.getCategories().length ? category : Income.getCategories().length - 1;
        input.nextLine(); // scanner bug

        Income newIncome = new Income(amount, name, (short) category);

        user.addIncome(newIncome);
    }

    // EFFECTS: print all Income categories for user to view
    private void createIncomeCategory() {
        for (int i = 0; i < Income.getCategories().length; i++) {
            System.out.println(i + ". " + Income.getCategories()[i]);
        }
    }

    // EFFECTS: print all expenses if there are more than 0 expenses, otherwise print no expenses.
    private void viewExpenses() {
        ArrayList<Expense> expenses = user.getExpenses();

        if (expenses.size() < 1) {
            System.out.println("No expenses available.");
        } else {
            for (int i = 0; i < expenses.size(); i++) {
                System.out.println(expenses.get(i));
            }
        }
    }

    // EFFECTS: print all incomes if there are more than 0 incomes, otherwwise print no incomes.
    private void viewIncomes() {
        ArrayList<Income> incomes = user.getIncomes();

        if (incomes.size() < 1) {
            System.out.println("No incomes available.");
        } else {
            for (int i = 0; i < incomes.size(); i++) {
                System.out.println(incomes.get(i));
            }
        }
    }

    // EFFECTS: view expense and income analytics
    private void viewAnalytics() {
        double totalExpenses = user.getTotalExpenses();
        double totalIncome = user.getTotalIncome();

        System.out.println("Total Expenses are: " + totalExpenses);
        System.out.println("Total Income is: " + totalIncome);

        double netWorth = totalIncome - totalExpenses;
        if (totalExpenses > totalIncome) {
            System.out.printf("Total expenses are greater.\nYou are at a net negative of $%.2f\n", netWorth);
        } else {
            System.out.printf("Total income is greater!\nYou are at a net positive of $%.2f\n", netWorth);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads user from a file
    private void loadUser() {
        try {
            user = jsonReader.read();
            System.out.println("Imported your last session from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

    // EFFECTS: saves user to file
    private void saveUser() {
        try {
            jsonWriter.open();
            jsonWriter.write(user);
            jsonWriter.close();
            System.out.println("Saved current session to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }
}
