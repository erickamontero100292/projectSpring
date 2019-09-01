package com.bean.reference.dto;

import java.util.List;

public class Room {
	
	private Door door;
	private List<Window> windows;
	
	
	public Room() {
	}
	public Room(Door door) {
		super();
		this.door = door;
	}
	public Room(Door door, List<Window> windows) {
		super();
		this.door = door;
		this.windows = windows;
	}
	public Door getDoor() {
		return door;
	}
	public void setDoor(Door door) {
		this.door = door;
	}
	public List<Window> getWindows() {
		return windows;
	}
	public void setWindows(List<Window> windows) {
		this.windows = windows;
	}
	@Override
	public String toString() {
		return "Room [door=" + door + ", windows=" + windows + "]";
	}
	
	

}
