package com.dessine.ui;

import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.dessine.corba.Event;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class MainWindowController {

	private MainWindowListener listener;

	@FXML
	private Button sendBackButton;

	@FXML
	private Button startServerButton;

	@FXML
	private Button stopServerButton;

	@FXML
	private ListView<EventListEntry> imageListView;

	@FXML
	private Button addCommentButton;

	@FXML
	private TextField commentTextField;

	@FXML
	private TextField initialHostAddressTextField;

	@FXML
	private TextField initialHostPortTextField;

	@FXML
	private ListView<String> commentsListView;

	@FXML
	private TextArea imageTextArea;

	public static final ObservableList<EventListEntry> events = FXCollections.observableArrayList();
	public static final ObservableList<String> comments = FXCollections.observableArrayList();

	public String initialHostAddress() {
		return initialHostAddressTextField.getText();
	}

	public String initialHostPort() {
		return initialHostPortTextField.getText();
	}

	public void init() {
		imageListView.setCellFactory(param -> new ListCell<EventListEntry>() {
			@Override
			protected void updateItem(EventListEntry item, boolean empty) {
				super.updateItem(item, empty);

				if (empty || item == null) {
					setText(null);
				} else {
					setText("Image (Ticket:" + Integer.toString(item.ticket()) + ") " + item.event().image().width + "x"
							+ item.event().image().height + " Bytes: " + item.event().image().bytesCount);
				}
			}
		});

		imageListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<EventListEntry>() {
			@Override
			public void changed(ObservableValue<? extends EventListEntry> observable, EventListEntry oldValue,
					EventListEntry newValue) {
				Logger.getLogger(MainWindowController.class.getName()).log(Level.INFO,
						"Selected ticket " + newValue.ticket());

				imageTextArea.setText(new String(newValue.event().image().data));

				if (listener != null) {
					listener.imageSelected(newValue.event(), newValue.ticket());
				}
			}
		});
	}

	public void addEvent(Event event, int Ticket) {

		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				events.add(new EventListEntry(event, Ticket));
				imageListView.setItems(events);
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

		comments.clear();
		commentTextField.clear();
		events.clear();
		imageListView.setItems(events);

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

	public void removeEvent(Event e) {
		events.removeIf(x -> x.event() == e);
		imageListView.setItems(events);
	}

	public void clearComments() {
		comments.clear();
		commentsListView.setItems(comments);
	}

}
