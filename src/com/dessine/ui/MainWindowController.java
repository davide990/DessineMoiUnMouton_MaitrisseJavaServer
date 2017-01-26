package com.dessine.ui;

import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;

import com.dessine.corba.Event;

import dessine_module.Image;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.util.Callback;

public class MainWindowController {

	private MainWindowListener listener;

	@FXML
	private Button sendBackButton;

	@FXML
	private Button startServerButton;

	@FXML
	private Button stopServerButton;

	@FXML
	private ListView<EventListEntry> listView;

	@FXML
	private Button addCommentButton;

	@FXML
	private TextField commentTextField;

	@FXML
	private ListView<String> commentsListView;

	public static final ObservableList<EventListEntry> events = FXCollections.observableArrayList();
	public static final ObservableList<String> comments = FXCollections.observableArrayList();

	public void init() {
		listView.setCellFactory(param -> new ListCell<EventListEntry>() {
			@Override
			protected void updateItem(EventListEntry item, boolean empty) {
				super.updateItem(item, empty);

				if (empty || item == null) {
					setText(null);
				} else {
					setText("Image (Ticket:" + Integer.toString(item.ticket()) + ") " + item.image().width + "x"
							+ item.image().height + " Bytes: " + item.image().bytesCount);
				}
			}
		});
	}

	public void addImage(Image image, int Ticket) {

		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				events.add(new EventListEntry(image, Ticket));
				listView.setItems(events);
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

	@FXML
	void onAddCommentButtonClicked(ActionEvent e) {
		Logger.getLogger(MainWindowController.class.getName()).log(Level.INFO, "Added comment");

		String comment = commentTextField.getText();
		comments.add(comment);
		commentsListView.setItems(comments);
		commentTextField.setText("");

		if (listener != null) {
			listener.addedComment(comment);
		}
	}

}
