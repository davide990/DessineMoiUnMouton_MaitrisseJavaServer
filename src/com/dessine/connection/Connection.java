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
		} catch (InvalidName | AdapterInactive ex) {
			Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
		}
		return c;
	}
}
