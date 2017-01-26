package com.dessine.ui;

import dessine_module.Image;

class EventListEntry {

	private final Image image;
	private final int ticket;

	public EventListEntry(Image img, int ticket) {
		image = img;
		this.ticket = ticket;
	}

	public Image image() {
		return image;
	}

	public int ticket() {
		return ticket;
	}
}