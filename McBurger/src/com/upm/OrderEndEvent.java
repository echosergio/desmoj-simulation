package com.upm;

import desmoj.core.simulator.Event;
import desmoj.core.simulator.Model;
import desmoj.core.simulator.TimeSpan;

import java.util.concurrent.TimeUnit;

public class OrderEndEvent extends Event<Cook> {

    private McBurgerModel myModel;

    public OrderEndEvent(Model owner, String name, boolean showInTrace) {
        super(owner, name, showInTrace);
        myModel = (McBurgerModel)owner;
    }

    public void eventRoutine(Cook cook) {

        if (!myModel.orderQueue.isEmpty())
        {
            Order order = myModel.orderQueue.first();
            myModel.orderQueue.remove(order);
            myModel.idleCookQueue.remove(cook);

            PrepareFoodEndEvent prepareFoodEndEvent = new PrepareFoodEndEvent (myModel, "PrepareFoodEndEvent", true);
            prepareFoodEndEvent.schedule(cook, order, new TimeSpan(myModel.getPrepareFoodTime(), TimeUnit.MINUTES));
        }
    }
}
