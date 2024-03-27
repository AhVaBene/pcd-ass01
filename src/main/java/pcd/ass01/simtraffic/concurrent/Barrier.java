package pcd.ass01.simtraffic.concurrent;

public interface Barrier {
    void hitAndWaitAll() throws InterruptedException;
}
