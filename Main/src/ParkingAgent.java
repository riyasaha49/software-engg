public class ParkingAgent extends Thread {
    private final int agentId;
    private final ParkingPool parkingPool;

    public ParkingAgent(int agentId, ParkingPool pool) {
        this.agentId = agentId;
        this.parkingPool = pool;
    }

    @Override
    public void run() {
        try {
            while (true) {
                RegistrarParking request = parkingPool.getParkingRequest();
                System.out.println("Agent#" + agentId + " processing " + request);
                Thread.sleep(1000); // simulate time to park the car
                System.out.println("Agent#" + agentId + " parked " + request);
            }
        } catch (InterruptedException e) {
            System.out.println("Agent#" + agentId + " was interrupted.");
        }
    }
}