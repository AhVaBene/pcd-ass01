package pcd.ass01.simtraffic.concurrent.base;

import pcd.ass01.simtraffic.concurrent.utils.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public abstract class AbstractSimulation extends Thread {
    /* environment of the simulation */
    private AbstractEnvironment env;
    /* list of the agents */
    private final List<AbstractAgent> agents;
    /* simulation listeners */
    private final List<SimulationListener> listeners;
    /* for time statistics*/
    private long currentWallTime;
    private long startWallTime;
    private long endWallTime;
    private double averageTimePerStep;
    /* in the case of sync with wall-time */
    private boolean toBeInSyncWithWallTime;
    private int nStepsPerSec;
    private Counter counter;
    private Latch roadsLatch;
    private CyclicBarrier barrier;

    protected AbstractSimulation() {
        agents = new ArrayList<>();
        listeners = new ArrayList<>();
    }

    /**
     *
     * Method used to configure the simulation, specifying env and agents
     *
     */
    protected void setup(CyclicBarrier simulationBarrier, Latch roadsLatch, Counter counter) {
        this.counter = counter;
        this.barrier = simulationBarrier;
        this.roadsLatch = roadsLatch;
    }

    public CyclicBarrier getSimulationBarrier(){
        return this.barrier;
    }

    public Counter getCounter(){return this.counter;}

    /**
     * Method running the simulation for a number of steps
     *
     *
     */
    public void run() {
        this.startWallTime = System.currentTimeMillis();
        env.init();
        for (var a: agents) {
            a.init(env);
        }
        this.notifyReset(0, agents, env);

        //run threads
        long timePerStep = 0;
        for (var a: agents) {
            a.start();
        }

        currentWallTime = System.currentTimeMillis();
        while (counter.isNotMax()) {
            try {
                this.roadsLatch.await();
                this.roadsLatch.reset();
                this.produce();
                notifyNewStep(agents, env);
                timePerStep += System.currentTimeMillis() - currentWallTime;
                this.barrier.await();
                currentWallTime = System.currentTimeMillis();
                if (toBeInSyncWithWallTime) {
                    syncWithWallTime();
                }
            } catch (InterruptedException | BrokenBarrierException e) {
                throw new RuntimeException(e);
            }
        }
        this.endWallTime = System.currentTimeMillis();
        long simTime = endWallTime - startWallTime;
        this.averageTimePerStep = (double) timePerStep / counter.getAcc();
        System.out.println("Simulation Ended in " + simTime + "ms with an average time per step of " + averageTimePerStep + "ms");
    }

    protected void setupEnvironment(AbstractEnvironment env) {
        this.env = env;
    }

    protected void addAgent(AbstractAgent agent) {
        agents.add(agent);
    }

    public synchronized void produce() throws InterruptedException {
        this.counter.inc();
        this.env.step(System.currentTimeMillis());
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

    private void notifyNewStep(List<AbstractAgent> agents, AbstractEnvironment env) {
        for (var l: listeners) {
            l.notifyStepDone(counter.getAcc(), agents, env);
        }
    }
    public long getSimulationDuration() {
        return endWallTime - startWallTime;
    }

    public double getAverageTimePerCycle() {
        return averageTimePerStep;
    }
    protected void syncWithTime(int nCyclesPerSec) {
        this.toBeInSyncWithWallTime = true;
        this.nStepsPerSec = nCyclesPerSec;
    }
    private void syncWithWallTime() {
        try {
            long newWallTime = System.currentTimeMillis();
            long delay = 1000 / this.nStepsPerSec;
            long wallTimeDT = newWallTime - currentWallTime;
            if (wallTimeDT < delay) {
                Thread.sleep(delay - wallTimeDT);
            }
        } catch (Exception ex) {}
    }

    public int getStepsNumber() {
        return this.counter.getMax();
    }
}
