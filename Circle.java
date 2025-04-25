// Circle.java
class Circle {
    // Private field - encapsulated data
    private double radius;

    // Constructor to initialize radius
    public Circle(double radius) {
        setRadius(radius); // Using setter to apply validation
    }

    // Method to calculate area of the circle
    public double getArea() {
        return Math.PI * radius * radius;
    }

    // Getter for radius
    public double getRadius() {
        return radius;
    }

    // Setter for radius with validation
    public void setRadius(double radius) {
        if (radius >= 0) {
            this.radius = radius;
        } else {
            System.out.println("Radius can't be negative. Setting radius to 0.");
            this.radius = 0;
        }
    }
}
