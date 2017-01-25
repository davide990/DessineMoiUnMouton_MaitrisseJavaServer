package com.dessine.connection;

import com.dessine.corba.Event;

public interface ConnectionListener {
	void connectionStarted();
	void connectionStopped();
	
	
	void receiveResult(int ticket, Event event);
}
