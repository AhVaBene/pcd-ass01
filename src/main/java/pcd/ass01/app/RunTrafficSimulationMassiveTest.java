package pcd.ass01.app;

import pcd.ass01.simtraffic.concurrent.examples.TrafficSimulationSingleRoadMassiveNumberOfCars;

import java.util.Random;

public class RunTrafficSimulationMassiveTest {

	public static void main(String[] args) throws InterruptedException {

		int numCars = 1000;
		int nSteps = 2000;
		int level = 2;
		boolean isRandom = false;
		var simulation = new TrafficSimulationSingleRoadMassiveNumberOfCars(numCars, nSteps);
		//var simulation = new TrafficSimulationSingleRoadMassiveNumberOfCars(level);
		simulation.setup(isRandom);

		log("Running the simulation: " + simulation.getCarsNumber() + " cars, for " + simulation.getStepsNumber() + " steps ...");
		
		simulation.start();

		simulation.join();
		double d = simulation.getSimulationDuration();
		log("Completed in " + d + " ms - average time per step: " + simulation.getAverageTimePerCycle() + " ms");
	}
	
	private static void log(String msg) {
		System.out.println("[ SIMULATION ] " + msg);
	}
}
