package com.upm;

import desmoj.core.simulator.EventOf2Entities;
import desmoj.core.simulator.Model;
import desmoj.core.simulator.TimeSpan;

import java.util.concurrent.TimeUnit;

public class PrepareFoodEndEvent extends EventOf2Entities<Cook,Order> {

    private McBurgerModel myModel;

    public PrepareFoodEndEvent(Model owner, String name, boolean showInTrace) {
        super(owner, name, showInTrace);
        myModel = (McBurgerModel)owner;
    }

    public void eventRoutine(Cook cook, Order order) {

        Food food = new Food(myModel, "Food", true);
        food.setClient(order.getClient());
        myModel.foodQueue.insert(food);
        myModel.idleCookQueue.insert(cook);

        if (!myModel.idleWorkerQueue.isEmpty())
        {
            Worker worker = myModel.idleWorkerQueue.first();
            myModel.idleWorkerQueue.remove(worker);
            myModel.foodQueue.remove(food);
            myModel.clientWithOrderQueue.remove(food.getClient());
            myModel.idleWorkerQueue.insert(worker);
        }

        if (!myModel.orderQueue.isEmpty())
        {
            Order nextOrder = myModel.orderQueue.first();
            myModel.orderQueue.remove(nextOrder);

            this.schedule(cook, nextOrder, new TimeSpan(myModel.getPrepareFoodTime(), TimeUnit.MINUTES));
        }
    }
}
