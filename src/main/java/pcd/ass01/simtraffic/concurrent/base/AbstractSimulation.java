package pcd.ass01.simtraffic.concurrent.base;

import pcd.ass01.simtraffic.concurrent.utils.SimulationListener;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractSimulation {
    /* environment of the simulation */
    private AbstractEnvironment env;

    /* list of the agents */
    private final List<AbstractAgent> agents;

    /* simulation listeners */
    private final List<SimulationListener> listeners;

    protected AbstractSimulation() {
        agents = new ArrayList<>();
        listeners = new ArrayList<>();
    }

    /**
     *
     * Method used to configure the simulation, specifying env and agents
     *
     */
    protected abstract void setup();

    /**
     * Method running the simulation for a number of steps
     *
     * @param numSteps
     */
    public void run(int numSteps) {
        //init threads (agenti e ambiente)
        //run threads
    }

    protected void setupEnvironment(AbstractEnvironment env) {
        this.env = env;
    }

    protected void addAgent(AbstractAgent agent) {
        agents.add(agent);
    }

    /* methods for listeners */

    public void addSimulationListener(SimulationListener l) {
        this.listeners.add(l);
    }

    private void notifyReset(int t0, List<AbstractAgent> agents, AbstractEnvironment env) {
        for (var l: listeners) {
            l.notifyInit(t0, agents, env);
        }
    }

    private void notifyNewStep(int t, List<AbstractAgent> agents, AbstractEnvironment env) {
        for (var l: listeners) {
            l.notifyStepDone(t, agents, env);
        }
    }
}
