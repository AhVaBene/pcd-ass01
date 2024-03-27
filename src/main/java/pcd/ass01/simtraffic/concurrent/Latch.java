package pcd.ass01.simtraffic.concurrent;

public interface Latch {
    void countDown();

    void await() throws InterruptedException;
}
