package com.dessine;

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
import com.dessine.corba.CommunicationListener;
import com.dessine.corba.Event;

import dessine_module.HostType;
import dessine_module.Reject;

public class Main {
	private static final String IOR_REF_FNAME = "/tmp/CHANNEL_IOR";

	public static void main(String[] args) {
		POA rootPOA = null;
		ORB orb = null;
		try {
			orb = ORB.init(args, null);
			rootPOA = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));

			CommunicationImpl communication = new CommunicationImpl();

			communication.listener = new CommunicationListener() {

				@Override
				public void receiveResult(int ticket, Event event) {
					try {
						Event e = event;
						e.host().type = HostType.SERVER;
						
						//send the response
						communication.pushImage(e.image(), e.host());
					} catch (Reject e) {
						e.printStackTrace();
					}
				}
			};

			byte[] objID = rootPOA.activate_object(communication);

			// Write the objects reference to file
			writeORBReferenceToFile(orb, rootPOA, objID, IOR_REF_FNAME);

			rootPOA.the_POAManager().activate();

			orb.run();
		} catch (InvalidName | ServantAlreadyActive | WrongPolicy | ObjectNotActive | FileNotFoundException
				| AdapterInactive ex) {
			Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	private static void writeORBReferenceToFile(ORB orb, POA rootPOA, byte[] objRef, String filePath)
			throws ObjectNotActive, FileNotFoundException, WrongPolicy {
		String reference = orb.object_to_string(rootPOA.id_to_reference(objRef));
		try (PrintWriter file = new PrintWriter(filePath)) {
			file.println(reference);
		}
	}
}
