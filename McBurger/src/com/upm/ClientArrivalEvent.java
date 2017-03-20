package com.upm;


import desmoj.core.simulator.Event;
import desmoj.core.simulator.Model;
import desmoj.core.simulator.TimeSpan;

import java.util.concurrent.TimeUnit;

public class ClientArrivalEvent extends Event<Client> {

    private McBurgerModel myModel;

    public ClientArrivalEvent(Model owner, String name, boolean showInTrace) {
        super(owner, name, showInTrace);
        myModel = (McBurgerModel)owner;
    }

    public void eventRoutine(Client client) {
        myModel.clientQueue.insert(client);

        if (!myModel.idleWorkerQueue.isEmpty())
        {
            Worker worker = myModel.idleWorkerQueue.first();
            myModel.idleWorkerQueue.remove(worker);
            myModel.clientQueue.remove(client);
            PrepareOrderEndEvent prepareOrderEndEvent = new PrepareOrderEndEvent (myModel, "PrepareOrderEndEvent", true);
            prepareOrderEndEvent.schedule(worker, client, new TimeSpan(myModel.getPrepareOrderTime(), TimeUnit.MINUTES));
        }
    }
}
