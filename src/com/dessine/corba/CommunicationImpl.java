package com.dessine.corba;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.dessine.connection.ConnectionListener;

import dessine_module.CommunicationPOA;
import dessine_module.HostInfo;
import dessine_module.HostType;
import dessine_module.Image;
import dessine_module.Reject;

public class CommunicationImpl extends CommunicationPOA {
	private Object lock;
	private EventQueue incomingQueue;
	private EventQueue outgoingQueue;
	private List<HostInfo> producers;
	public ConnectionListener listener;

	public CommunicationImpl() {
		lock = new Object();
		incomingQueue = new EventQueue();
		outgoingQueue = new EventQueue();
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

	public String pushOutgoing(Event e) throws Reject {
		int ticket = -1;
		synchronized (lock) {
			ticket = outgoingQueue.insert(e);
		}
		Logger.getLogger(CommunicationImpl.class.getName()).info("Added outgoing event: " + e);
		// readyForPull(Integer.toString(ticket), e.host()); // host -> client

		return Integer.toString(ticket);
	}

	@Override
	public String pushImage(Image info, HostInfo host) throws Reject {
		if (host.type == HostType.SERVER)
			throw new Reject("Forbidden. Only Clients ");

		Event e = new Event(info, host);
		int ticket = -1;

		if (host.type == HostType.CLIENT) {
			// Client->Server
			synchronized (lock) {
				ticket = incomingQueue.insert(e);
			}
			Logger.getLogger(CommunicationImpl.class.getName())
					.info("[INFO] Received image from " + host.ipAddress + ":" + host.port);
			// Logger.getLogger(CommunicationImpl.class.getName()).info("Added
			// incoming event: " + e);

			// Notify to the consumer that a new information has been sent
			notifyToConsumer(ticket, e);
		}

		return Integer.toString(ticket);
	}

	// Invocata quando il CONSUMER ha finito di modificare l'elemento.
	// Invia la risposta in pratica
	@Override
	public void readyForPull(String ticket, HostInfo producer) {
		/*
		 * // create and initialize the ORB try { ORB orb = ORB.init(new
		 * String[] {}, null); Properties props = new Properties();
		 * //props.put("org.omg.CORBA.ORBInitialPort", producer.port);
		 * //props.put("org.omg.CORBA.ORBInitialHost", producer.ipAddress);
		 * 
		 * props.put("org.omg.CORBA.ORBInitialPort", "2809");
		 * props.put("org.omg.CORBA.ORBInitialHost", "127.0.0.1");
		 * 
		 * String ObjectID = "DESSINE"; //CORBALOC
		 * 
		 * //org.omg.CORBA.Object obj =
		 * orb.string_to_object("corbaloc::"+producer.ipAddress+":"+producer.
		 * port+"/"+ObjectID);
		 * 
		 * org.omg.CORBA.Object obj = orb.resolve_initial_references(ObjectID);
		 * 
		 * 
		 * 
		 * // get the root naming context //org.omg.CORBA.Object objRef =
		 * orb.string_to_object(producer.ior); Communication c =
		 * CommunicationHelper.narrow(obj); // Provide a ticket to the producer
		 * c.readyForPull(ticket, producer); orb.destroy(); } catch (Exception
		 * e) { System.out.println("ERROR : " + e);
		 * e.printStackTrace(System.out); }
		 */
	}

	// TODO remove consumerInfo param
	@Override
	public String[] pullComments(String ticket) throws Reject {
		Event e = null;
		Logger.getLogger(CommunicationImpl.class.getName()).info("Pulling info with ticket: \"" + ticket + "\"");

		synchronized (lock) {
			e = outgoingQueue.events().get(Integer.parseInt(ticket));
			outgoingQueue.remove(Integer.parseInt(ticket));
		}
		if (e == null)
			return new String[] {};

		for (String s : e.comments()) {
			Logger.getLogger(CommunicationImpl.class.getName()).info("--> " + s);
		}

		return e.comments().toArray(new String[0]);

	}

	@Override
	public void disconnect(HostInfo info) {
		// TODO Auto-generated method stub

	}

}
