package pcd.ass01.simtraffic.concurrent.utils;

public interface Barrier {
    void hitAndWaitAll(int state) throws InterruptedException;
}
