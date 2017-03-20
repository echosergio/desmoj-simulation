package com.upm;

import desmoj.core.simulator.EventOf2Entities;
import desmoj.core.simulator.Model;
import desmoj.core.simulator.TimeSpan;

import java.util.concurrent.TimeUnit;

public class PrepareOrderEndEvent extends EventOf2Entities<Worker,Client> {

    private McBurgerModel myModel;

    public PrepareOrderEndEvent(Model owner, String name, boolean showInTrace) {
        super(owner, name, showInTrace);
        myModel = (McBurgerModel)owner;
    }

    public void eventRoutine(Worker worker, Client client) {
        ClientPayEndEvent clientPayEndEvent = new ClientPayEndEvent (myModel, "ClientPayEndEvent", true);
        clientPayEndEvent.schedule(worker, client, new TimeSpan(myModel.getClientPayTime(), TimeUnit.MINUTES));
    }
}
