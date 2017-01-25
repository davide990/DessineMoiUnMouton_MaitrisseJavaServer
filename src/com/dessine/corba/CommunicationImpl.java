package com.dessine.corba;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.omg.CORBA.ORB;

import com.dessine.connection.ConnectionListener;

import dessine_module.Communication;
import dessine_module.CommunicationHelper;
import dessine_module.CommunicationPOA;
import dessine_module.HostInfo;
import dessine_module.HostType;
import dessine_module.Image;
import dessine_module.Reject;

public class CommunicationImpl extends CommunicationPOA {
	private Object lock;
	private EventQueue incomingQueue;
	private EventQueue outoingQueue;
	private List<HostInfo> producers;
	public ConnectionListener listener;

	public CommunicationImpl() {
		lock = new Object();
		incomingQueue = new EventQueue();
		outoingQueue = new EventQueue();
		producers = new ArrayList<>();
	}

	private void notifyToConsumer(int ticket, Event event) {
		if (listener != null) {
			System.out.println("notifying back to " + event.host().hostName);
			listener.receiveResult(ticket, event);
		}
	}

	@Override
	public void registerProducer(HostInfo info) throws Reject {
		if (!producers.contains(info)) {
			producers.add(info);
		}
	}

	@Override
	public String pushImage(Image info, HostInfo host) throws Reject {
		Event e = new Event(info, host);
		int ticket = -1;

		if (host.type == HostType.CLIENT) {
			// Client->Server
			synchronized (lock) {
				ticket = incomingQueue.insert(e);
			}
			Logger.getLogger(CommunicationImpl.class.getName()).info("Added incoming event: " + e);

			// Notify to the consumer that a new information has been sent
			notifyToConsumer(ticket, e);
		} else {
			// Server->Client
			synchronized (lock) {
				ticket = outoingQueue.insert(e);
			}
			Logger.getLogger(CommunicationImpl.class.getName()).info("Added outgoing event: " + e);
			readyForPull(Integer.toString(ticket), host); // host -> client
		}

		return Integer.toString(ticket);
	}

	// Invocata quando il CONSUMER ha finito di modificare l'elemento.
	// Invia la risposta in pratica
	@Override
	public void readyForPull(String ticket, HostInfo producer) {

		// create and initialize the ORB
		try {
			ORB orb = ORB.init(new String[] {}, null);

			// get the root naming context
			org.omg.CORBA.Object objRef = orb.string_to_object(producer.ior);
			Communication c = CommunicationHelper.narrow(objRef);
			// Provide a ticket to the producer
			c.readyForPull(ticket, producer);
			orb.destroy();
		} catch (Exception e) {
			System.out.println("ERROR : " + e);
			e.printStackTrace(System.out);
		}

	}

	// TODO remove consumerInfo param
	@Override
	public String[] pullComments(String ticket) throws Reject {
		Event e;
		Logger.getLogger(CommunicationImpl.class.getName()).info("Pulling info with ticket: " + ticket);
		synchronized (lock) {
			e = outoingQueue.events().get(Integer.parseInt(ticket));
			outoingQueue.remove(Integer.parseInt(ticket));
		}
		if (e == null)
			return new String[] { "[EMPTY]" };

		return e.comments().toArray(new String[0]);

	}

	@Override
	public void disconnect(HostInfo info) {
		// TODO Auto-generated method stub

	}

}
