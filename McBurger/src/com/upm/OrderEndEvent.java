package com.upm;

import desmoj.core.simulator.EventOf2Entities;
import desmoj.core.simulator.Model;
import desmoj.core.simulator.TimeSpan;

import java.util.concurrent.TimeUnit;

public class OrderEndEvent extends EventOf2Entities<Cook,Client> {

    private McBurgerModel myModel;

    public OrderEndEvent(Model owner, String name, boolean showInTrace) {
        super(owner, name, showInTrace);
        myModel = (McBurgerModel)owner;
    }

    public void eventRoutine(Cook cook, Client client) {
        sendTraceNote(cook + " finish the order");
        sendTraceNote(client + " gets the order");

        myModel.clientWithOrderQueue.remove(client);
        myModel.idleCookQueue.insert(cook);

        if (!myModel.clientWithOrderQueue.isEmpty())
        {
            Client nextClient = myModel.clientWithOrderQueue.first();
            myModel.clientWithOrderQueue.remove(nextClient);
            myModel.idleCookQueue.remove(cook);
            this.schedule(cook, client, new TimeSpan(myModel.getOrderTime(), TimeUnit.MINUTES));
        }
    }
}
