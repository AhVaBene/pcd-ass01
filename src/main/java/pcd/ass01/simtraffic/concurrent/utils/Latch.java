package pcd.ass01.simtraffic.concurrent.utils;

public interface Latch {
    void countDown();

    void await() throws InterruptedException;
    void reset();
}
