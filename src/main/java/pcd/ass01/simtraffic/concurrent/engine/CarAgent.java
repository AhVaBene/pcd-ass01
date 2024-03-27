package pcd.ass01.simtraffic.concurrent.engine;


import pcd.ass01.simtraffic.concurrent.base.AbstractAgent;
import pcd.ass01.simtraffic.concurrent.base.AbstractEnvironment;
import pcd.ass01.simtraffic.concurrent.utils.*;

import java.util.Optional;

public abstract class CarAgent extends AbstractAgent {
    /* car model */
    protected double maxSpeed;
    protected double currentSpeed;
    protected double acceleration;
    protected double deceleration;

    /* percept and action retrieved and submitted at each step */
    protected CarPercept currentPercept;
    protected Optional<Action> selectedAction;


    public CarAgent(String id, RoadsEnv env, Road road,
                    double initialPos,
                    double acc,
                    double dec,
                    double vmax) {
        super(id);
        this.acceleration = acc;
        this.deceleration = dec;
        this.maxSpeed = vmax;
        env.registerNewCar(this, road, initialPos);
    }

    @Override
    public void run() {
        //TODO implement barrier

        /* sense */
        AbstractEnvironment env = this.getEnv();
        currentPercept = (CarPercept) env.getCurrentPercepts(getName());

        /* decide */
        selectedAction = Optional.empty();
        decide();

        /* act */
        selectedAction.ifPresent(action -> env.doAction(getName(), action));
    }

    protected abstract void decide();

    public double getCurrentSpeed() {
        return currentSpeed;
    }

    protected void log(String msg) {
        System.out.println("[CAR " + getName() + "] " + msg);
    }
}
