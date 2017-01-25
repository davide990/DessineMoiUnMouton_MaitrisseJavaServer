package com.dessine;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.dessine.connection.BackgroundConnection;
import com.dessine.connection.ConnectionListener;
import com.dessine.ui.MainWindowController;
import com.dessine.ui.MainWindowListener;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Main extends Application implements MainWindowListener, ConnectionListener {

	private static String IOR_FNAME = "/tmp/IOR";
	private MainWindowController mainWindowController;
	private BackgroundConnection connection;

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

			setupUI();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void setupUI() {
		mainWindowController.setListener(this);
	}

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void sendBackButtonClicked() {
		// TODO Auto-generated method stub

	}

	@Override
	public void startServerButtonClicked() {
		connection = BackgroundConnection.getConnection(IOR_FNAME, new String[] {}, this);
		connection.start();
		Logger.getLogger(getClass().getName()).log(Level.INFO, "Connection started...");
	}

	@Override
	public void stopServerButtonClicked() {
		connection.stopConnection();
		Logger.getLogger(getClass().getName()).log(Level.INFO, "Connection stopped");
	}

	@Override
	public void connectionStarted() {
		// TODO Auto-generated method stub

	}

	@Override
	public void connectionStopped() {
		// TODO Auto-generated method stub

	}

}
