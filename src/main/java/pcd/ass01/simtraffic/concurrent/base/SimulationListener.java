package pcd.ass01.simtraffic.concurrent.base;

import pcd.ass01.simtraffic.concurrent.base.AbstractAgent;
import pcd.ass01.simtraffic.concurrent.base.AbstractEnvironment;

import java.util.List;

public interface SimulationListener {

	/**
	 * Called at the beginning of the simulation
	 * 
	 * @param t
	 * @param agents
	 * @param env
	 */
	void notifyInit(int t, List<AbstractAgent> agents, AbstractEnvironment env);
	
	/**
	 * Called at each step, updater all updates
	 * @param t
	 * @param agents
	 * @param env
	 */
	void notifyStepDone(int t, List<AbstractAgent> agents, AbstractEnvironment env);
}
