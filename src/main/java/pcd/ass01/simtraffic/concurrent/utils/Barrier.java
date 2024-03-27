package pcd.ass01.simtraffic.concurrent.utils;

public interface Barrier {
    void hitAndWaitAll() throws InterruptedException;
}
