class Employee {
    protected String role = "Employee";

    void work() {
        System.out.println("Employee is working.");
    }
}

class Manager extends Employee {
    void manage() {
        System.out.println(role + " is managing the team."); // Accessing protected field directly
    }
}

public class Main {
    public static void main(String[] args) {
        Manager m = new Manager();
        m.work();   // Inherited method from Employee
        m.manage(); // Method from Manager accessing the protected field
    }
}
