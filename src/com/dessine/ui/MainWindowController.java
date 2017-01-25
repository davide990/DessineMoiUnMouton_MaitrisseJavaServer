package com.dessine.ui;

import dessine_module.Image;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

public class MainWindowController {

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
		images.add("Image (Ticket:" + Integer.toString(Ticket) + ") " + image.width + "x" + image.height + " Bytes: "
				+ image.bytesCount);
		listView.setItems(images);
	}

	@FXML
	void onSendBackButtonClicked(ActionEvent e) {

	}

	@FXML
	void onStartServerButtonClicked(ActionEvent e) {

	}

	@FXML
	void onStopServerButtonClicked(ActionEvent e) {

	}
}
