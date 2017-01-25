package com.dessine.corba;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import dessine_module.HostInfo;
import dessine_module.Image;

public class Event {
	private final Image image;
	private final HostInfo host;
	private final List<String> comments;

	public Event(Image info, HostInfo host) {
		this.image = info;
		this.host = host;
		comments = new ArrayList<>();
	}

	public void addComment(String s) {
		comments.add(s);
	}

	public void removeComment(String s) {
		comments.remove(s);
	}

	public List<String> comments() {
		return Collections.unmodifiableList(comments);
	}

	public Image image() {
		return image;
	}

	public HostInfo host() {
		return host;
	}

	@Override
	public String toString() {
		return "Event [Image " + +image.width + "x" + image.height + " Bytes: " + image.bytesCount + ", host=" + host
				+ "]";
	}
}
