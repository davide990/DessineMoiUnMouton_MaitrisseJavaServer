package com.dessine;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.dessine.connection.BackgroundConnection;
import com.dessine.connection.ConnectionListener;
import com.dessine.corba.Event;
import com.dessine.ui.MainWindowController;
import com.dessine.ui.MainWindowListener;

import dessine_module.Reject;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Main extends Application implements MainWindowListener, ConnectionListener {


	private MainWindowController mainWindowController;
	private BackgroundConnection connection;
	Event selectedEvent;
	int selectedEventTicket;
	List<String> comments;

	public Main() {
		comments = new ArrayList<>();
	}

	@Override
	public void start(Stage primaryStage) {

		primaryStage.setTitle("Event Handling");

		try {
			FXMLLoader loader = new FXMLLoader(Main.class.getResource("ui/MainWindow.fxml"));
			Pane page = (Pane) loader.load();
			mainWindowController = loader.getController();

			Scene scene = new Scene(page);
			primaryStage.setScene(scene);
			primaryStage.show();

			mainWindowController.setListener(this);
			mainWindowController.init();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void sendBackButtonClicked() {

		Event e = new Event(selectedEvent.image(), selectedEvent.host());
		comments.forEach(c -> e.addComment(c));

		// Push the event in the outgoing events queue
		try {
			connection.connection().pushOutgoing(e);
		} catch (Reject e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}

		// Tell the client that I have something for him
		try {
			sendToPort(e.host().ipAddress, e.host().port, Integer.toString(selectedEventTicket));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return;
		}

		// Clear the comments list and show an information dialog
		mainWindowController.clearComments();
		mainWindowController.removeEvent(e);
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle("Dessine");
		alert.setHeaderText("Comments sent");
		alert.setContentText("Ticket: " + selectedEventTicket);
		alert.showAndWait();
	}

	public void sendToPort(String address, String port, String ticket) throws IOException {
		Socket Socket = null;
		PrintWriter out = null;

		try {
			Socket = new Socket(address, Integer.parseInt(port));
			out = new PrintWriter(Socket.getOutputStream(), true);
			out.print(ticket);
			out.close();
		} catch (IOException e) {
			return;
		} finally {
			Socket.close();
		}
	}

	@Override
	public void startServerButtonClicked() {

		Properties props = new Properties();
		props.put("org.omg.CORBA.ORBInitialPort", mainWindowController.initialHostPort());
		props.put("org.omg.CORBA.ORBInitialHost", mainWindowController.initialHostAddress());

		connection = BackgroundConnection.getConnection(new String[] {}, props, this);
		connection.start();
	}

	@Override
	public void stopServerButtonClicked() {
		connection.stopConnection();

	}

	@Override
	public void connectionStarted() {
		Logger.getLogger(getClass().getName()).log(Level.INFO, "Connection started...");

	}

	@Override
	public void connectionStopped() {
		Logger.getLogger(getClass().getName()).log(Level.INFO, "Connection stopped");
	}

	@Override
	public void receiveResult(int ticket, Event event) {
		mainWindowController.addEvent(event, ticket);
	}

	@Override
	public void addedComment(String s) {
		comments.add(s);
	}

	@Override
	public void imageSelected(Event event, int ticket) {
		selectedEvent = event;
		selectedEventTicket = ticket;
	}

}
