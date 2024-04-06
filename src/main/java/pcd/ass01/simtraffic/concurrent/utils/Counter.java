package pcd.ass01.simtraffic.concurrent.utils;

public class Counter {
    private int acc;
    private int max;

    public Counter(int max) {
        this.acc = 0;
        this.max = max;
    }

    public synchronized void inc() {
        acc++;
    }
    public synchronized int getAcc() {
        return acc;
    }
    public synchronized int getMax() { return this.max; }
    public synchronized boolean isNotMax() {
        return acc < max;
    }

    public synchronized void reset(int max){
        this.acc = 0;
        this.max = max;
    }
}
