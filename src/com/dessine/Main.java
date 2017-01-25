package com.dessine;

import java.io.IOException;

import com.dessine.ui.MainWindowController;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Main extends Application {

	private MainWindowController mainWindowController;

	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle("Event Handling");

		try {
			FXMLLoader loader = new FXMLLoader(Main.class.getResource("ui/MainWindow.fxml"));
			mainWindowController = loader.getController();
			Pane page = (Pane) loader.load();
			Scene scene = new Scene(page);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}

}
