package pcd.ass01.simtraffic.concurrent.engine;

import pcd.ass01.simtraffic.concurrent.base.AbstractEnvironment;
import pcd.ass01.simtraffic.concurrent.utils.*;

import java.util.HashMap;
import java.util.List;

public class RoadsEnv extends AbstractEnvironment {
    /* list of roads */
    private List<Road> roads;

    /* traffic lights */
    private List<TrafficLight> trafficLights;

    /* cars situated in the environment */
    private HashMap<String, CarAgentInfo> registeredCars;

    public RoadsEnv() {
        super("environment");
    }

    @Override
    public void init() {
        //setup e run thread semafori
    }

    @Override
    public void run() {
        //comportamento latch
    }

    public Road createRoad(P2d p0, P2d p1) {
        Road r = new Road(p0, p1);
        this.roads.add(r);
        return r;
    }

    public TrafficLight createTrafficLight(P2d pos, TrafficLight.TrafficLightState initialState, int greenDuration, int yellowDuration, int redDuration) {
        TrafficLight tl = new TrafficLight(pos, initialState, greenDuration, yellowDuration, redDuration);
        this.trafficLights.add(tl);
        return tl;
    }

    @Override
    public Percept getCurrentPercepts(String agentId) {
        return null;
    }

    @Override
    public void doAction(String agentId, Action act) {

    }

    public void registerNewCar(CarAgent carAgent, Road road, double initialPos) {
    }
}
