package com.upm;

import desmoj.core.simulator.EventOf2Entities;
import desmoj.core.simulator.Model;
import desmoj.core.simulator.TimeSpan;

import java.util.concurrent.TimeUnit;

public class ClientPayEndEvent extends EventOf2Entities<Worker,Client> {

    private McBurgerModel myModel;

    public ClientPayEndEvent(Model owner, String name, boolean showInTrace) {
        super(owner, name, showInTrace);
        myModel = (McBurgerModel)owner;
    }

    public void eventRoutine(Worker worker, Client client) {

        myModel.clientWithOrderQueue.insert(client);

        Order order = new Order(myModel, "Food", true);
        order.setClient(client);
        myModel.orderQueue.insert(order);

        if (!myModel.idleCookQueue.isEmpty())
        {
            Cook cook = myModel.idleCookQueue.first();
            myModel.idleCookQueue.remove(cook);

            OrderEndEvent orderEndEvent = new OrderEndEvent (myModel, "OrderEndEvent", true);
            orderEndEvent.schedule(cook, new TimeSpan(0, TimeUnit.MINUTES));
        }

        while (!myModel.foodQueue.isEmpty())
        {
            Food food = myModel.foodQueue.first();
            myModel.foodQueue.remove(food);
            myModel.clientWithOrderQueue.remove(food.getClient());
        }

        myModel.idleWorkerQueue.insert(worker);

        if (!myModel.clientQueue.isEmpty())
        {
            Client nextClient = myModel.clientQueue.first();
            myModel.clientQueue.remove(nextClient);
            myModel.idleWorkerQueue.remove(worker);
            PrepareOrderEndEvent prepareOrderEndEvent = new PrepareOrderEndEvent (myModel, "PrepareOrderEndEvent", true);
            prepareOrderEndEvent.schedule(worker, nextClient, new TimeSpan(myModel.getPrepareOrderTime(), TimeUnit.MINUTES));
        }
    }
}