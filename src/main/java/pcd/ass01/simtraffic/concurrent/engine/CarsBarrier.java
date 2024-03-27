package pcd.ass01.simtraffic.concurrent.engine;

import pcd.ass01.simtraffic.concurrent.utils.Barrier;

public class CarsBarrier implements Barrier {
    private final int nCars;
    private int acc;
    public CarsBarrier(int nCars) {
        this.acc = 0;
        this.nCars = nCars;
    }

    @Override
    public synchronized void hitAndWaitAll() throws InterruptedException {
        this.acc++;
        if(this.acc >= this.nCars) {
            notifyAll();
            //TODO reduce latch state counter
        } else {
            while(this.acc < this.nCars) {
                wait();
            }
        }
    }
}
