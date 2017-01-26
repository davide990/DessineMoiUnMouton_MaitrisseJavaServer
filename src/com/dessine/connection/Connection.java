package com.dessine.connection;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.omg.CORBA.ORB;
import org.omg.CORBA.ORBPackage.InvalidName;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;
import org.omg.PortableServer.POAManagerPackage.AdapterInactive;
import org.omg.PortableServer.POAPackage.ObjectNotActive;
import org.omg.PortableServer.POAPackage.ServantAlreadyActive;
import org.omg.PortableServer.POAPackage.WrongPolicy;

import com.dessine.corba.CommunicationImpl;

public class Connection {
	private ORB orb;
	private POA rootPOA;

	private Connection() {
	}

	public void stop() {
		orb.destroy();
	}

	public void start() {
		orb.run();
	}

	public static Connection createConnection(String iorFname, String[] args, ConnectionListener listener) {
		Connection c = new Connection();
		try {
			c.orb = ORB.init(args, null);
			c.rootPOA = POAHelper.narrow(c.orb.resolve_initial_references("RootPOA"));

			CommunicationImpl communication = new CommunicationImpl();

			communication.listener = listener;
			/*
			 * communication.listener = new CommunicationListener() {
			 * 
			 * @Override public void receiveResult(int ticket, Event event) {
			 * try { Event e = event; e.host().type = HostType.SERVER;
			 * 
			 * // send the response communication.pushImage(e.image(),
			 * e.host()); } catch (Reject e) { e.printStackTrace(); } } };
			 */

			byte[] objID = c.rootPOA.activate_object(communication);

			// Write the objects reference to file
			writeORBReferenceToFile(c.orb, c.rootPOA, objID, iorFname);

			c.rootPOA.the_POAManager().activate();

		} catch (InvalidName | ServantAlreadyActive | WrongPolicy | ObjectNotActive | FileNotFoundException
				| AdapterInactive ex) {
			Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
		}
		return c;
	}

	private static void writeORBReferenceToFile(ORB orb, POA rootPOA, byte[] objRef, String filePath)
			throws ObjectNotActive, FileNotFoundException, WrongPolicy {
		String reference = orb.object_to_string(rootPOA.id_to_reference(objRef));
		try (PrintWriter file = new PrintWriter(filePath)) {
			file.println(reference);
		}
	}
}
