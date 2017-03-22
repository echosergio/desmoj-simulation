package com.upm;

import desmoj.core.simulator.Entity;
import desmoj.core.simulator.Model;

public class Food extends Entity {

    private Client client;

    public Food(Model owner, String name, boolean showInTrace) {
        super(owner, name, showInTrace);
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}