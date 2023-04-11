package ui;

import model.*;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.io.IOException;

// Personal Finance Application - EquityGUI
public class GUI {
    // bottom level components of GUI
    private JFrame frame;
    private JPanel mainPanel;

    // elements for the table
    private JScrollPane scroll1;
    private JScrollPane scroll2;
    private final String[] columnHeadings = new String[]{"Date", "Number", "Amount", "Name", "Category"};
    private DefaultTableModel incomeModel = new DefaultTableModel(0, columnHeadings.length);
    private DefaultTableModel expenseModel = new DefaultTableModel(0, columnHeadings.length);

    // button elements
    private JButton buttonSaveIncome;
    private JButton buttonSaveExpense;
    private JButton buttonSave;
    private JButton buttonLoad;
    private JButton buttonAnalytics;

    // other fields require from CLI version of app
    private static final String JSON_STORE = "./data/user.json";
    private User user;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // EFFECTS: runs Equity app GUI version
    public GUI() throws FileNotFoundException {
        this.user = new User();

        this.jsonWriter = new JsonWriter(JSON_STORE);
        this.jsonReader = new JsonReader(JSON_STORE);

        initPanel();
        initButtons();

        createGUI();
    }

    // MODIFIES: this
    // EFFECTS: initialize panel, table, and all ui elements
    private void initPanel() {
        this.mainPanel = new JPanel();

        this.buttonSaveIncome = new JButton("Create new Income");
        this.buttonSaveExpense = new JButton("Create new Expense");
        this.buttonSave = new JButton("Save");
        this.buttonLoad = new JButton("Load");
        this.buttonAnalytics = new JButton("Analytics");

        incomeModel.setColumnIdentifiers(columnHeadings);
        expenseModel.setColumnIdentifiers(columnHeadings);
        JTable incomeTable = new JTable(incomeModel);
        this.scroll1 = new JScrollPane(incomeTable);
        JTable expenseTable = new JTable(expenseModel);
        this.scroll2 = new JScrollPane(expenseTable);
    }

    // EFFECTS: initialize buttons
    private void initButtons() {
        buttonSaveIncome.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buttonSaveIncome();
            }
        });

        buttonSaveExpense.addActionListener(e -> buttonSaveExpense());

        buttonSave.addActionListener(e -> saveUser());

        buttonLoad.addActionListener(e -> loadUser());

        buttonAnalytics.addActionListener(e -> buttonAnalytics());
    }

    // MODIFIES: this
    // EFFECTS: create income and add it to table
    private void buttonSaveIncome() {
        JTextField amount = new JTextField("");
        JTextField name = new JTextField("");
        JComboBox categories = new JComboBox<>(Income.getCategories());

        Income income = new Income();

        addAmountListener(amount, income);
        addNameListener(name, income);
        addCategoryListener(categories, income);

        int choice = JOptionPane.showConfirmDialog(null, createFields(amount, name, categories),
                "Create a new Income!", JOptionPane.OK_CANCEL_OPTION);

        if (choice == 0) {
            user.addIncome(income);
            incomeModel.addRow(new Object[]{income.getDate(), income.getNumber(), income.getAmount(),
                    income.getName(), income.getCategory()});
        } else {
            System.out.println("User did not choose OK to save Income");
        }
    }

    // MODIFIES: this
    // EFFECTS: create expense and add it to table
    private void buttonSaveExpense() {
        JTextField amount = new JTextField("");
        JTextField name = new JTextField("");
        JComboBox categories = new JComboBox<>(Expense.getCategories());

        Expense expense = new Expense();

        addAmountListener(amount, expense);
        addNameListener(name, expense);
        addCategoryListener(categories, expense);

        int choice = JOptionPane.showConfirmDialog(null, createFields(amount, name, categories),
                "Create a new Expense!", JOptionPane.OK_CANCEL_OPTION);

        if (choice == 0) {
            user.addExpense(expense);
            expenseModel.addRow(new Object[]{expense.getDate(), expense.getNumber(), expense.getAmount(),
                    expense.getName(), expense.getCategory()});
        } else {
            System.out.println("User did not choose OK to save expense");
        }
    }

    // EFFECTS: visual component with financial analytics feature
    private void buttonAnalytics() {
        double net = user.viewAnalytics();
        JDialog dialog = new JDialog(frame, "Your net worth is: " + net);
        ImageIcon icon;

        if (net < 0) {
            icon = new ImageIcon("./data/broke.png");
            dialog.setSize(667, 500);
        } else {
            icon = new ImageIcon("./data/rich.png");
            dialog.setSize(1111, 500);
        }

        JLabel label = new JLabel(icon);
        dialog.add(label);
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }

    // EFFECTS: add focus listeners to amount JTextField
    private void addAmountListener(JTextField amount, Transaction transaction) {
        amount.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {}

            @Override
            public void focusLost(FocusEvent e) {
                transaction.setAmount(Double.parseDouble(amount.getText()));
            }
        });
    }

    // EFFECTS: add focus listeners to name JTextField
    private void addNameListener(JTextField name, Transaction transaction) {
        name.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {}

            @Override
            public void focusLost(FocusEvent e) {
                transaction.setName(name.getText());
            }
        });
    }

    // EFFECTS: add focus listeners to category JComboBox
    private void addCategoryListener(JComboBox category, Transaction transaction) {
        category.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {}

            @Override
            public void focusLost(FocusEvent e) {
                transaction.setCategory(category.getSelectedIndex());
            }
        });
    }

    // EFFECTS: creates the 3 input fields for popup menu used to create transaction
    private Object[] createFields(JTextField amount, JTextField name, JComboBox categories) {
        return new Object[] {
                "Amount (in $):", amount,
                "Name:", name,
                "Category:", categories
        };
    }

    // MODIFIES: this
    // EFFECTS: loads user from a file
    private void loadUser() {
        try {
            user = jsonReader.read();
            refreshTable();
            System.out.println("Imported your last session from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

    // EFFECTS: load all things in user into table
    private void refreshTable() {
        for (int i = 0; i < user.getExpenses().size(); i++) {
            Expense expense = user.getExpenses().get(i);
            expenseModel.addRow(new Object[]{expense.getDate(), expense.getNumber(), expense.getAmount(),
                    expense.getName(), expense.getCategory()});
        }
        for (int i = 0; i < user.getIncomes().size(); i++) {
            Income income = user.getIncomes().get(i);
            incomeModel.addRow(new Object[]{income.getDate(), income.getNumber(), income.getAmount(),
                    income.getName(), income.getCategory()});
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

    // EFFECTS: create GUI and adds all elements required
    private void createGUI() {
        this.frame = new JFrame("Equity");
        frame.setContentPane(this.mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

        frame.setSize(925, 600);
        frame.add(this.buttonSaveIncome);
        frame.add(this.buttonSaveExpense);
        frame.add(this.buttonSave);
        frame.add(this.buttonLoad);
        frame.add(this.buttonAnalytics);
        frame.add(this.scroll1);
        frame.add(this.scroll2);
        frame.setLocationRelativeTo(null);

        this.frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                for (Event event : EventLog.getInstance()) {
                    System.out.println(event);
                }
            }
        });
    }
}
