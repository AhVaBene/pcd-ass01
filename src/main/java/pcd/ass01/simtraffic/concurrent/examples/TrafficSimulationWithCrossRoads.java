package pcd.ass01.simtraffic.concurrent.examples;

import pcd.ass01.simtraffic.concurrent.base.AbstractSimulation;
import pcd.ass01.simtraffic.concurrent.engine.*;
import pcd.ass01.simtraffic.concurrent.utils.*;

import java.util.concurrent.CyclicBarrier;

import java.util.Random;

public class TrafficSimulationWithCrossRoads extends AbstractSimulation {

	public TrafficSimulationWithCrossRoads() {
		super();
	}
	
	public void setup() {

		Random  gen = new Random(1);

		int nSteps = 1000;
		int nCars = 4;

		Latch roadsLatch = new RoadLatch(3);
		CyclicBarrier simulationBarrier = new CyclicBarrier(nCars+2);
		Barrier barrier = new CarsBarrier(nCars, roadsLatch);
		Counter counter = new Counter(nSteps);
		this.setup(simulationBarrier, roadsLatch, counter);

		RoadsEnv env = new RoadsEnv();
		this.setupEnvironment(env);
				
		TrafficLight tl1 = env.createTrafficLight(new P2d(740,300), TrafficLight.TrafficLightState.GREEN, 140, 70, 170);
		
		Road r1 = env.createRoad(new P2d(0,300), new P2d(1500,300));
		r1.addTrafficLight(tl1, 740);
		
		CarAgent car1 = new CarAgentImpl("car-1", env, r1, 0, 2.2, 1.2, 10, barrier, counter, simulationBarrier);
		this.addAgent(car1);		
		CarAgent car2 = new CarAgentImpl("car-2", env, r1, 100, 1.5, 0.8, 8, barrier, counter, simulationBarrier);
		this.addAgent(car2);		
		
		TrafficLight tl2 = env.createTrafficLight(new P2d(750,290),  TrafficLight.TrafficLightState.RED, 140, 70, 170);

		Road r2 = env.createRoad(new P2d(750,0), new P2d(750,600));
		r2.addTrafficLight(tl2, 290);

		CarAgent car3 = new CarAgentImpl("car-3", env, r2, 0, 2, 1, 9, barrier, counter, simulationBarrier);
		this.addAgent(car3);		
		CarAgent car4 = new CarAgentImpl("car-4", env, r2, 100, 1, 1, 6, barrier, counter, simulationBarrier);
		this.addAgent(car4);
		
		
		this.syncWithTime(25);
	}	
	
}
