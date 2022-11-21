package com.eval;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;

public class PublisherActor extends AbstractActor {

	private ActorRef broker;
	
	@Override
	public Receive createReceive() {
		return receiveBuilder()
				.match(PublishMsg.class, this::publish)
				.match(ConfigMsg.class, this::configure)
				.build();
	}

	void publish(PublishMsg msg) {
		System.out.println("PUBLISH: topic:  " + msg.getTopic() + " - value:" + msg.getValue());
		broker.tell(msg, self());
	}

	void configure(ConfigMsg msg) {
		System.out.println("PUBLISHER: Received configuration message!");
		broker = msg.getBrokerRef();
	}

	static Props props() {
		return Props.create(PublisherActor.class);
	}

}
