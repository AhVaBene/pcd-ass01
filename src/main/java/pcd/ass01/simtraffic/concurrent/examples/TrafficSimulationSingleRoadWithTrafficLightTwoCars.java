package pcd.ass01.simtraffic.concurrent.examples;

import pcd.ass01.simtraffic.concurrent.base.AbstractSimulation;
import pcd.ass01.simtraffic.concurrent.engine.*;
import pcd.ass01.simtraffic.concurrent.utils.*;

import java.util.concurrent.CyclicBarrier;

/**
 * 
 * Traffic Simulation about 2 cars moving on a single road, with one semaphore
 * 
 */
public class TrafficSimulationSingleRoadWithTrafficLightTwoCars extends AbstractSimulation {

	public TrafficSimulationSingleRoadWithTrafficLightTwoCars() {
		super();
	}
	
	public void setup() {
		int nSteps = 500;
		int nCars = 40;

		Latch roadsLatch = new RoadLatch(3);
		CyclicBarrier simulationBarrier = new CyclicBarrier(nCars+1);
		Barrier barrier = new CarsBarrier(nCars, roadsLatch);
		Counter counter = new Counter(nSteps);

		this.setup(simulationBarrier, roadsLatch, counter);
		RoadsEnv env = new RoadsEnv();
		this.setupEnvironment(env);
				
		Road road = env.createRoad(new P2d(0,300), new P2d(1500,300));
		TrafficLight tl = env.createTrafficLight(new P2d(740,300), TrafficLight.TrafficLightState.GREEN, 140, 70, 170);
		road.addTrafficLight(tl, 740);

		for (int i = 0; i < nCars; i++) {
			String carId = "car-" + i;
			// double initialPos = i*30;
			double initialPos = i*10;
			double carAcceleration = 2; //  + gen.nextDouble()/2;
			double carDeceleration = 1; //  + gen.nextDouble()/2;
			double carMaxSpeed = 9; // 4 + gen.nextDouble();
			CarAgent car = new CarAgentImpl(carId, env,
					road, initialPos, carAcceleration,
					carDeceleration, carMaxSpeed,
					barrier, counter,
					simulationBarrier
					);

			this.addAgent(car);
		}

		this.syncWithTime(25);
	}	
	
}
