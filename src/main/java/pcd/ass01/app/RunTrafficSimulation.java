package pcd.ass01.app;

import pcd.ass01.simtraffic.concurrent.examples.TrafficSimulationSingleRoadWithTrafficLightTwoCars;
import pcd.ass01.simtraffic.concurrent.examples.TrafficSimulationWithCrossRoads;

/**
 * 
 * Main class to create and run a simulation
 * 
 */
public class RunTrafficSimulation {

	public static void main(String[] args) {		

		//var simulation = new TrafficSimulationSingleRoadWithTrafficLightTwoCars();
		var simulation = new TrafficSimulationWithCrossRoads();
		simulation.setup();
		
		RoadSimStatistics stat = new RoadSimStatistics();
		RoadSimView view = new RoadSimView(simulation.getSimulationBarrier(), simulation.getCounter());
		view.display();
		
		simulation.addSimulationListener(stat);
		simulation.addSimulationListener(view);		
		simulation.start();
	}
}
