class Student {
    private String name;
    private int age;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setAge(int age) {
        if(age > 0) this.age = age;
    }

    public int getAge() {
        return age;
    }
}

public class Encap {
    public static void main(String[] args) {
        Student student = new Student();
        student.setName("John");
        student.setAge(20);

        System.out.println("Name: " + student.getName());
        System.out.println("Age: " + student.getAge());
    }
}
