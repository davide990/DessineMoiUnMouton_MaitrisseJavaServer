package com.dessine.connection;

import java.util.Objects;
import java.util.Properties;

public class BackgroundConnection extends Thread {

	private Connection connection;
	private ConnectionListener listener;

	private BackgroundConnection() {
	}

	public static BackgroundConnection getConnection(String iorFname, String[] args, Properties prop,
			ConnectionListener listener) {
		Objects.requireNonNull(listener);
		Objects.requireNonNull(iorFname);

		BackgroundConnection c = new BackgroundConnection();

		// c.connection = Connection.createConnection(iorFname, args, prop,
		// listener);

		c.connection = Connection.createRemoteConnection(iorFname, args, prop, listener);

		c.listener = listener;

		return c;
	}

	public void setListener(ConnectionListener listener) {
		this.listener = listener;
	}

	public Connection connection() {
		return connection;
	}

	public void run() {
		listener.connectionStarted();
		connection.start();
	}

	public void stopConnection() {
		connection.stop();
	}
}
