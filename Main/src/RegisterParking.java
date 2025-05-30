public class RegisterParking {
}
class RegistrarParking {
    private final int carId;

    public RegistrarParking(int carId) {
        this.carId = carId;
    }

    public int getCarId() {
        return carId;
    }

    @Override
    public String toString() {
        return "Car#" + carId;
    }
}