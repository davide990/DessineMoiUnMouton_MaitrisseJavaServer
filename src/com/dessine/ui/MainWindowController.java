package com.dessine.ui;

import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

import dessine_module.Image;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

public class MainWindowController {

	private MainWindowListener listener;

	@FXML
	private Button sendBackButton;

	@FXML
	private Button startServerButton;

	@FXML
	private Button stopServerButton;

	@FXML
	private ListView<String> listView;

	public static final ObservableList<String> images = FXCollections.observableArrayList();

	public void addImage(Image image, int Ticket) {
		
		Platform.runLater(new Runnable(){

			@Override
			public void run() {
				images.add("Image (Ticket:" + Integer.toString(Ticket) + ") " + image.width + "x" + image.height + " Bytes: "
						+ image.bytesCount);
				listView.setItems(images);
			}
			});
		
	}

	public void setListener(MainWindowListener listener) {
		Objects.requireNonNull(listener);
		this.listener = listener;
	}

	@FXML
	void onSendBackButtonClicked(ActionEvent e) {
		Logger.getLogger(MainWindowController.class.getName()).log(Level.INFO, "Send back button pressed");
		if (listener != null) {
			listener.sendBackButtonClicked();
		}

	}

	@FXML
	void onStartServerButtonClicked(ActionEvent e) {
		Logger.getLogger(MainWindowController.class.getName()).log(Level.INFO, "Start server button pressed");
		stopServerButton.setDisable(false);
		startServerButton.setDisable(true);
		if (listener != null) {
			listener.startServerButtonClicked();
		}
	}

	@FXML
	void onStopServerButtonClicked(ActionEvent e) {
		Logger.getLogger(MainWindowController.class.getName()).log(Level.INFO, "Stop server button pressed");
		stopServerButton.setDisable(true);
		startServerButton.setDisable(false);
		if (listener != null) {
			listener.stopServerButtonClicked();
		}
	}
}
