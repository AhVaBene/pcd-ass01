package pcd.ass01.simtraffic.concurrent;

public class RoadLatch implements Latch {
    private int states;
    public RoadLatch() {
        this.states = 3;
    }

    @Override
    public synchronized void countDown() {
        if(--this.states == 0) {
            notifyAll();
        }
    }

    @Override
    public synchronized void await() throws InterruptedException {
        while(this.states > 0) {
            wait();
        }
        this.states = 3;
    }
}
