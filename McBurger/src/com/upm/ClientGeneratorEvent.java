package com.upm;

import desmoj.core.simulator.ExternalEvent;
import desmoj.core.simulator.Model;
import desmoj.core.simulator.TimeSpan;

import java.util.concurrent.TimeUnit;

public class ClientGeneratorEvent extends ExternalEvent {

	public ClientGeneratorEvent(Model owner, String name, boolean showInTrace) {
		super(owner, name, showInTrace);
	}

	public void eventRoutine() {
		McBurgerModel model = (McBurgerModel)getModel();
		Client client = new Client(model, "Client", true);
		ClientArrivalEvent clientArrivalEvent = new ClientArrivalEvent(model, "ClientArrivalEvent", true);
		clientArrivalEvent.schedule(client, new TimeSpan(0, TimeUnit.MINUTES));
		this.schedule(new TimeSpan(model.getClientArrivalTime(), TimeUnit.MINUTES));
	}
}
