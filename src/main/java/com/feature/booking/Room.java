package com.feature.booking;

import lombok.Data;

@Data
public class Room {

	private final String id;
	private final int capacity;

	public Room(String id, int capacity) {
		this.id = id;
		this.capacity = capacity;
	}

}
