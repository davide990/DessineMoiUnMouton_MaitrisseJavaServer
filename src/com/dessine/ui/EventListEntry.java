package com.dessine.ui;

import com.dessine.corba.Event;

class EventListEntry {

	private final Event event;
	private final int ticket;

	public EventListEntry(Event img, int ticket) {
		event = img;
		this.ticket = ticket;
	}

	public Event event() {
		return event;
	}

	public int ticket() {
		return ticket;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof EventListEntry))
			return false;

		EventListEntry e = (EventListEntry) obj;
		/*
		 * System.out.println("My ticket: "+ticket);
		 * System.out.println("Other ticket: "+e.ticket());
		 */
		return ticket == e.ticket();
	}

	@Override
	public int hashCode() {
		return ticket;
	}

}