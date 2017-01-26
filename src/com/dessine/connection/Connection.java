package com.dessine.connection;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Properties;
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
import com.dessine.corba.Event;

import dessine_module.Reject;

public class Connection {
	private ORB orb;
	private POA rootPOA;
	private CommunicationImpl instance;

	private Connection() {
	}

	public void stop() {
		orb.destroy();
	}

	public void start() {
		orb.run();
	}

	public void pushOutgoing(Event e) throws Reject {
		instance.pushOutgoing(e);
	}

	public static Connection createConnection(String iorFname, String[] args, Properties props,
			ConnectionListener listener) {
		Connection c = new Connection();
		try {
			c.orb = ORB.init(args, props);
			c.rootPOA = POAHelper.narrow(c.orb.resolve_initial_references("RootPOA"));
			c.instance = new CommunicationImpl();
			c.instance.listener = listener;
			c.rootPOA.the_POAManager().activate();
			byte[] connectionID = c.rootPOA.activate_object(c.instance);
			writeORBReferenceToFile(c.orb, c.rootPOA, connectionID, iorFname);

		} catch (InvalidName | AdapterInactive | ObjectNotActive | FileNotFoundException | WrongPolicy
				| ServantAlreadyActive ex) {
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
