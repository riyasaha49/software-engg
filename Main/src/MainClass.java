public class MainClass {
    public static void main(String[] args) {
        final int NUMBER_OF_AGENTS = 3;
        final int PARKING_QUEUE_CAPACITY = 10;
        final int NUMBER_OF_CARS = 20;

        ParkingPool pool = new ParkingPool(PARKING_QUEUE_CAPACITY);

        for (int i = 1; i <= NUMBER_OF_AGENTS; i++) {
            new ParkingAgent(i, pool).start();
        }

        for (int carId = 1; carId <= NUMBER_OF_CARS; carId++) {
            RegistrarParking request = new RegistrarParking(carId);
            try {
                pool.addParkingRequest(request);
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}