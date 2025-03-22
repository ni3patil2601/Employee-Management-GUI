import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class EmployeeGUI extends JFrame {

    private Map<Integer, Employee> employeeMap = new HashMap<>();

    private JTextField idField, nameField, deptField, salaryField;
    private JTextArea outputArea;
    private final String DATA_FILE = "employees.dat";

    public EmployeeGUI() {
        setTitle("Advanced Employee Management System");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        loadFromFile(); // Load data at startup

        // Form
        JPanel formPanel = new JPanel(new GridLayout(5, 2));

        idField = new JTextField();
        nameField = new JTextField();
        deptField = new JTextField();
        salaryField = new JTextField();

        formPanel.add(new JLabel("ID:")); formPanel.add(idField);
        formPanel.add(new JLabel("Name:")); formPanel.add(nameField);
        formPanel.add(new JLabel("Department:")); formPanel.add(deptField);
        formPanel.add(new JLabel("Salary:")); formPanel.add(salaryField);

        JButton addBtn = new JButton("Add");
        JButton viewBtn = new JButton("View All");
        JButton updateBtn = new JButton("Update");
        JButton deleteBtn = new JButton("Delete");
        JButton saveBtn = new JButton("Save to File");

        formPanel.add(addBtn); formPanel.add(viewBtn);
        formPanel.add(updateBtn); formPanel.add(deleteBtn);
        formPanel.add(saveBtn);

        add(formPanel, BorderLayout.NORTH);

        // Output area
        outputArea = new JTextArea();
        outputArea.setEditable(false);
        add(new JScrollPane(outputArea), BorderLayout.CENTER);

        // Button actions
        addBtn.addActionListener(e -> addEmployee());
        viewBtn.addActionListener(e -> viewEmployees());
        updateBtn.addActionListener(e -> updateEmployee());
        deleteBtn.addActionListener(e -> deleteEmployee());
        saveBtn.addActionListener(e -> saveToFile());

        setVisible(true);
    }

    private void addEmployee() {
        try {
            int id = Integer.parseInt(idField.getText());
            if (employeeMap.containsKey(id)) {
                JOptionPane.showMessageDialog(this, "Employee ID already exists.");
                return;
            }
            String name = nameField.getText();
            String dept = deptField.getText();
            double salary = Double.parseDouble(salaryField.getText());

            employeeMap.put(id, new Employee(id, name, dept, salary));
            outputArea.append("Employee added successfully.\n");
            clearFields();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Invalid input.");
        }
    }

    private void viewEmployees() {
        outputArea.setText("");
        if (employeeMap.isEmpty()) {
            outputArea.setText("No employees found.\n");
        } else {
            for (Employee emp : employeeMap.values()) {
                outputArea.append(emp + "\n");
            }
        }
    }

    private void updateEmployee() {
        try {
            int id = Integer.parseInt(idField.getText());
            if (!employeeMap.containsKey(id)) {
                JOptionPane.showMessageDialog(this, "Employee ID not found.");
                return;
            }
            String name = nameField.getText();
            String dept = deptField.getText();
            double salary = Double.parseDouble(salaryField.getText());

            Employee emp = employeeMap.get(id);
            emp.setName(name);
            emp.setDepartment(dept);
            emp.setSalary(salary);

            outputArea.append("Employee updated successfully.\n");
            clearFields();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Invalid input.");
        }
    }

    private void deleteEmployee() {
        try {
            int id = Integer.parseInt(JOptionPane.showInputDialog(this, "Enter ID to delete:"));
            if (employeeMap.remove(id) != null) {
                outputArea.append("Employee deleted successfully.\n");
            } else {
                outputArea.append("Employee ID not found.\n");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Invalid ID.");
        }
    }

    private void saveToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DATA_FILE))) {
            oos.writeObject(employeeMap);
            outputArea.append("Data saved to file.\n");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Failed to save data.");
        }
    }

    @SuppressWarnings("unchecked")
    private void loadFromFile() {
        File file = new File(DATA_FILE);
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                employeeMap = (HashMap<Integer, Employee>) ois.readObject();
                outputArea.append("Loaded " + employeeMap.size() + " employees from file.\n");
            } catch (Exception e) {
                System.out.println("Failed to load file.");
            }
        }
    }

    private void clearFields() {
        idField.setText("");
        nameField.setText("");
        deptField.setText("");
        salaryField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(EmployeeGUI::new);
    }
}
