import java.util.*;

public class EmployeeManagementSystem {

    static Map<Integer, Employee> employeeMap = new HashMap<>();
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        int choice;

        do {
            System.out.println("\n--- Employee Management System ---");
            System.out.println("1. Add Employee");
            System.out.println("2. View Employees");
            System.out.println("3. Update Employee");
            System.out.println("4. Delete Employee");
            System.out.println("5. Exit");
            System.out.print("Enter choice: ");
            choice = sc.nextInt();

            switch (choice) {
                case 1 -> addEmployee();
                case 2 -> viewEmployees();
                case 3 -> updateEmployee();
                case 4 -> deleteEmployee();
                case 5 -> System.out.println("Exiting...");
                default -> System.out.println("Invalid choice! Try again.");
            }
        } while (choice != 5);
    }

    private static void addEmployee() {
        System.out.print("Enter ID: ");
        int id = sc.nextInt();
        sc.nextLine(); // clear buffer

        System.out.print("Enter Name: ");
        String name = sc.nextLine();

        System.out.print("Enter Department: ");
        String dept = sc.nextLine();

        System.out.print("Enter Salary: ");
        double salary = sc.nextDouble();

        Employee emp = new Employee(id, name, dept, salary);
        employeeMap.put(id, emp);
        System.out.println("Employee added successfully!");
    }

    private static void viewEmployees() {
        if (employeeMap.isEmpty()) {
            System.out.println("No employees to show.");
        } else {
            for (Employee emp : employeeMap.values()) {
                System.out.println(emp);
            }
        }
    }

    private static void updateEmployee() {
        System.out.print("Enter ID to update: ");
        int id = sc.nextInt();
        sc.nextLine(); // clear buffer

        if (employeeMap.containsKey(id)) {
            Employee emp = employeeMap.get(id);

            System.out.print("Enter new Name: ");
            String name = sc.nextLine();

            System.out.print("Enter new Department: ");
            String dept = sc.nextLine();

            System.out.print("Enter new Salary: ");
            double salary = sc.nextDouble();

            emp.setName(name);
            emp.setDepartment(dept);
            emp.setSalary(salary);

            System.out.println("Employee updated successfully!");
        } else {
            System.out.println("Employee ID not found.");
        }
    }

    private static void deleteEmployee() {
        System.out.print("Enter ID to delete: ");
        int id = sc.nextInt();

        if (employeeMap.remove(id) != null) {
            System.out.println("Employee deleted successfully!");
        } else {
            System.out.println("Employee ID not found.");
        }
    }
}
