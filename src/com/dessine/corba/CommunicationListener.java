package com.dessine.corba;

public interface CommunicationListener {
	void receiveResult(int ticket, Event event);
}
