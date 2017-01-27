package com.dessine.connection;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.omg.CORBA.ORB;
import org.omg.CORBA.ORBPackage.InvalidName;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContext;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.CosNaming.NamingContextPackage.AlreadyBound;
import org.omg.CosNaming.NamingContextPackage.CannotProceed;
import org.omg.CosNaming.NamingContextPackage.NotFound;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;
import org.omg.PortableServer.Servant;
import org.omg.PortableServer.POAManagerPackage.AdapterInactive;
import org.omg.PortableServer.POAPackage.ObjectNotActive;
import org.omg.PortableServer.POAPackage.ServantAlreadyActive;
import org.omg.PortableServer.POAPackage.ServantNotActive;
import org.omg.PortableServer.POAPackage.WrongPolicy;

import com.dessine.corba.CommunicationImpl;
import com.dessine.corba.Event;

import dessine_module.Reject;

public class Connection {
	private static String CONNECTION_ID = "DESSINE_MAITRISSE";
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

	public static Connection createRemoteConnection(String iorFname, String[] args, Properties props,
			ConnectionListener listener) {

		Connection c = new Connection();
		try {
			

			c.orb = ORB.init(args, props);
			c.rootPOA = POAHelper.narrow(c.orb.resolve_initial_references("RootPOA"));
			c.rootPOA.the_POAManager().activate();
			c.instance = new CommunicationImpl();
			c.instance.listener = listener;
			//byte[] connectionID = c.rootPOA.activate_object(c.instance);
			NamingContextExt ctx = NamingContextExtHelper.narrow(c.orb.resolve_initial_references("NameService"));
			registerToContext(c.rootPOA, c.instance, ctx.to_name(CONNECTION_ID), ctx);

			System.err.println("Server set up and running...\n");
		} catch (InvalidName | AdapterInactive //| WrongPolicy | ServantAlreadyActive
				| org.omg.CosNaming.NamingContextPackage.InvalidName ex) {
			Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
		} finally {

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

	/**
	 * Register a Servant to a specified context
	 *
	 * @param rootPOA
	 * @param servant
	 * @param servantID
	 * @param ctx
	 */
	private static void registerToContext(POA rootPOA, Servant servant, NameComponent[] servantID, NamingContext ctx) {

		try {
			org.omg.CORBA.Object objRef = rootPOA.servant_to_reference(servant);
			try {
				ctx.bind(servantID, objRef);
			} catch (AlreadyBound ex) {
				ctx.rebind(servantID, objRef);
			}

		} catch (ServantNotActive | WrongPolicy | org.omg.CosNaming.NamingContextPackage.InvalidName | NotFound
				| CannotProceed ex) {
			Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

}
