package com.upm;

import java.util.concurrent.TimeUnit;

import desmoj.core.dist.ContDistExponential;
import desmoj.core.simulator.*;

public class McBurgerModel extends Model {

    protected static int NUM_WORKER = 5;
    protected static int NUM_COOK = 1;

    private ContDistExponential clientArrivalTime;
    private ContDistExponential prepareOrderTime;
    private ContDistExponential prepareFoodTime;
    private ContDistExponential clientPayTime;

    protected Queue<Client> clientQueue;
    protected Queue<Client> clientWithOrderQueue;
    protected Queue<Worker> idleWorkerQueue;
    protected Queue<Cook> idleCookQueue;
    protected Queue<Order> orderQueue;
    protected Queue<Food> foodQueue;

    protected int workerNumber;
    protected int cookNumber;

    public McBurgerModel(Model owner, String modelName, boolean showInReport, boolean showInTrace) {
        super(owner, modelName, showInReport, showInTrace);
        workerNumber = NUM_WORKER;
        cookNumber = NUM_COOK;
    }

    public McBurgerModel() {
        this(null, "McBurgerModel", true, true);
    }

    public String description() {
        return "This is the McBurger model description, this model simulates a fast food restaurant client flow.";
    }

    public void doInitialSchedules() {
        ClientGeneratorEvent clientGeneratorEvent = new ClientGeneratorEvent(this, "Client Generator", false);
        clientGeneratorEvent.schedule(new TimeSpan(0, TimeUnit.MINUTES));
    }

    public double getClientArrivalTime() {
        return clientArrivalTime.sample();
    }

    public double getPrepareOrderTime() {
        return prepareOrderTime.sample();
    }

    public double getPrepareFoodTime() {
        return prepareFoodTime.sample();
    }

    public double getClientPayTime() {
        return clientPayTime.sample();
    }

    public void init() {

        // distributions
        clientArrivalTime = new ContDistExponential(this, "clientArrivalTime", 5.0, true, false);
        clientArrivalTime.setNonNegative(true);

        prepareOrderTime = new ContDistExponential(this, "prepareOrderTime", 4.0, true, false);
        prepareOrderTime.setNonNegative(true);

        prepareFoodTime = new ContDistExponential(this, "prepareFoodTime", 9.0, true, false);
        prepareFoodTime.setNonNegative(true);

        clientPayTime = new ContDistExponential(this, "clientPayTime", 2.0, true, false);
        clientPayTime.setNonNegative(true);

        // queues
        clientQueue = new Queue<>(this, "clientQueue", true, false);
        clientWithOrderQueue = new Queue<>(this, "clientWithOrderQueue", true, false);
        idleWorkerQueue = new Queue<>(this, "idleWorkerQueue", true, false);
        idleCookQueue = new Queue<>(this, "idleCookQueue", true, false);
        orderQueue = new Queue<>(this, "orderQueue", true, false);
        foodQueue = new Queue<>(this, "foodQueue", true, false);

        for (int i = 0; i < workerNumber; i++) {
            idleWorkerQueue.insert(new Worker(this, "Worker", true));
        }

        for (int i = 0; i < cookNumber; i++) {
            idleCookQueue.insert(new Cook(this, "Cook", true));
        }
    }

    public static void main(java.lang.String[] args) {
        Experiment.setEpsilon(java.util.concurrent.TimeUnit.SECONDS);
        Experiment.setReferenceUnit(java.util.concurrent.TimeUnit.MINUTES);
        Experiment experiment = new Experiment("McBurger Model");

        McBurgerModel mcBurgerModel = new McBurgerModel(null,"McBurger Model",true,true);
        mcBurgerModel.connectToExperiment(experiment);

        experiment.tracePeriod(new TimeInstant(0), new TimeInstant(100));
        experiment.stop(new TimeInstant(150000));
        experiment.setShowProgressBar(false);
        experiment.start();
        experiment.report();
        experiment.finish();
    }
}