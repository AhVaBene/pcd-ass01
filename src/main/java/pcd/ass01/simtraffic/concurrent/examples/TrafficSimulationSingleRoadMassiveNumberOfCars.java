package pcd.ass01.simtraffic.concurrent.examples;

import pcd.ass01.simtraffic.concurrent.base.AbstractSimulation;
import pcd.ass01.simtraffic.concurrent.engine.*;
import pcd.ass01.simtraffic.concurrent.utils.*;

import java.util.Random;
import java.util.concurrent.CyclicBarrier;

public class TrafficSimulationSingleRoadMassiveNumberOfCars extends AbstractSimulation {

	private int numCars;
	private int nSteps;
	Random gen;
	
	public TrafficSimulationSingleRoadMassiveNumberOfCars(int numCars, int nSteps) {
		super();
		this.numCars = numCars;
		this.nSteps = nSteps;
	}

	public TrafficSimulationSingleRoadMassiveNumberOfCars(int level) {
		super();
		this.gen = new Random(1);
		int boundCars = switch (level) {
            case 1 -> 800;
            case 2 -> 1000;
            case 3 -> 5000;
            case 4 -> 8000;
            default -> 500;
        };
		int boundSteps = switch (level) {
			case 1 -> 5000;
			case 2 -> 4000;
			case 3 -> 2500;
			case 4 -> 2000;
			default -> 3000;
		};
        this.numCars = gen.nextInt(1, boundCars);
		this.nSteps = gen.nextInt(1, boundSteps);
	}
	
	public void setup(boolean isRandom) {
		Latch roadsLatch = new RoadLatch(3);
		CyclicBarrier simulationBarrier = new CyclicBarrier(numCars+1);
		Barrier barrier = new CarsBarrier(numCars, roadsLatch);
		Counter counter = new Counter(nSteps);
		this.setup(simulationBarrier, roadsLatch, counter);

		RoadsEnv env = new RoadsEnv();
		this.setupEnvironment(env);

		Road road = env.createRoad(new P2d(0,300), new P2d(15000,300));

		for (int i = 0; i < numCars; i++) {
			double initialPos = i*10;
			double carAcceleration = 2;
			double carDeceleration = 1;
			double carMaxSpeed = 9;
			if(isRandom) {
				carAcceleration = gen.nextDouble(1, 2.5);
				carDeceleration = gen.nextDouble(1, 2.5);
				carMaxSpeed = gen.nextDouble(4, 10);
			}

			String carId = "car-" + i;
			CarAgent car = new CarAgentImpl(carId, env,
									road,
									initialPos, 
									carAcceleration, 
									carDeceleration,
									carMaxSpeed, barrier, counter, simulationBarrier);
			this.addAgent(car);
			
			/* no sync with wall-time */
		}
	}

	public int getCarsNumber() {
		return this.numCars;
	}
}
	