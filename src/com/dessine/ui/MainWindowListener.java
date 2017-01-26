package com.dessine.ui;

import com.dessine.corba.Event;


public interface MainWindowListener {
	void sendBackButtonClicked();
	void startServerButtonClicked();
	void stopServerButtonClicked();
	void addedComment(String s);
	void imageSelected(Event i, int ticket);
}
