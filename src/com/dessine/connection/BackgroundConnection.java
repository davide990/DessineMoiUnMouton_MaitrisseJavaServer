package com.dessine.connection;

import java.util.Objects;

public class BackgroundConnection extends Thread {

	private Connection connection;
	private ConnectionListener listener;

	private BackgroundConnection() {
	}

	public static BackgroundConnection getConnection(String iorFname, String[] args, ConnectionListener listener) {
		Objects.requireNonNull(listener);
		Objects.requireNonNull(iorFname);
		
		BackgroundConnection c = new BackgroundConnection();
		c.connection = Connection.createConnection(iorFname, args, listener);
		c.listener = listener;
		
		return c;
	}

	public void setListener(ConnectionListener listener) {
		this.listener = listener;
	}

	public void run() {
		listener.connectionStarted();
		connection.start();
	}
	
	public void stopConnection()
	{
		connection.stop();
	}
}
