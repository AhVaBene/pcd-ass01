package pcd.ass01.simtraffic.concurrent.engine;

import pcd.ass01.simtraffic.concurrent.utils.P2d;

public class TrafficLight extends Thread {
    public static enum TrafficLightState {GREEN, YELLOW, RED}
    private TrafficLightState state, initialState;
    private int currentTimeInState;
    private int redDuration, greenDuration, yellowDuration;
    private P2d pos;

    public TrafficLight(P2d pos, TrafficLightState initialState, int greenDuration, int yellowDuration, int redDuration) {
        this.redDuration = redDuration;
        this.greenDuration = greenDuration;
        this.yellowDuration = yellowDuration;
        this.pos = pos;
        this.initialState = initialState;
    }

    public void run() {
        //TODO
    }

    public boolean isGreen() {
        return state.equals(TrafficLightState.GREEN);
    }

    public boolean isRed() {
        return state.equals(TrafficLightState.RED);
    }

    public boolean isYellow() {
        return state.equals(TrafficLightState.YELLOW);
    }

    public P2d getPos() {
        return pos;
    }
}
