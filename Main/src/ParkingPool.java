import java.util.LinkedList;
import java.util.Queue;

public class ParkingPool {
    private final Queue<RegistrarParking> parkingQueue = new LinkedList<>();
    private final int capacity;

    public ParkingPool(int capacity) {
        this.capacity = capacity;
    }

    public synchronized void addParkingRequest(RegistrarParking request) throws InterruptedException {
        while (parkingQueue.size() >= capacity) {
            wait();
        }
        parkingQueue.offer(request);
        System.out.println(request + " added to the parking queue.");
        notifyAll();
    }

    public synchronized RegistrarParking getParkingRequest() throws InterruptedException {
        while (parkingQueue.isEmpty()) {
            wait();
        }
        RegistrarParking request = parkingQueue.poll();
        notifyAll();
        return request;
    }
}