package pcd.ass01.simtraffic.concurrent.examples;

import pcd.ass01.simtraffic.concurrent.base.AbstractSimulation;
import pcd.ass01.simtraffic.concurrent.engine.*;
import pcd.ass01.simtraffic.concurrent.utils.*;

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
		Latch roadLatch = new RoadLatch();
		RoadsEnv env = new RoadsEnv();
		this.setupEnvironment(env);
				
		Road road = env.createRoad(new P2d(0,300), new P2d(1500,300));
		TrafficLight tl = env.createTrafficLight(new P2d(740,300), TrafficLight.TrafficLightState.GREEN, 75, 25, 100);
		road.addTrafficLight(tl, 740);

		int nCars = 30;
		Barrier barrier = new CarsBarrier(nCars);
		for (int i = 0; i < nCars; i++) {
			String carId = "car-" + i;
			// double initialPos = i*30;
			double initialPos = i*10;
			double carAcceleration = 1; //  + gen.nextDouble()/2;
			double carDeceleration = 0.3; //  + gen.nextDouble()/2;
			double carMaxSpeed = 7; // 4 + gen.nextDouble();
			CarAgent car = new CarAgentImpl(carId, env,
					road,
					initialPos,
					carAcceleration,
					carDeceleration,
					carMaxSpeed);

			this.addAgent(car);
		}

		//this.syncWithTime(25);
	}	
	
}
