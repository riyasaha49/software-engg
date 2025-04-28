class Student {
    String name;
    int rollNumber;

    void study() {
        System.out.println(name + " is studying...");
    }
}

public class Mainn {
    public static void main(String[] args) {
        Student student1 = new Student(); // object creation
        student1.name = "Alice";
        student1.rollNumber = 101;
        student1.study();
    }
}
