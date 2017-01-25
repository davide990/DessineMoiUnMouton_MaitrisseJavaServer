package com.dessine.corba;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class EventQueue {

	private int idx = 0;
	private HashMap<Integer, Event> events;

	public EventQueue() {
		events = new HashMap<>();
	}

	public Map<Integer, Event> events() {
		return Collections.unmodifiableMap(events);
	}

	public int insert(Event event) {
		events.put(idx, event);
		return idx++;
	}

	public void remove(Integer key) {
		events.remove(key);
	}

}
