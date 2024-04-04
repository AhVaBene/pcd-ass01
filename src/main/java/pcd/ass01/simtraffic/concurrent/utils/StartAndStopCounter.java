package pcd.ass01.simtraffic.concurrent.utils;

public class StartAndStopCounter {
    private boolean isStopped;

    public StartAndStopCounter() {
        isStopped = false;
    }

    public synchronized void stop() {
        isStopped = true;
    }

    public synchronized void start() {
        isStopped = false;
    }
    public synchronized boolean getIsStopped() {
        return isStopped;
    }
}
