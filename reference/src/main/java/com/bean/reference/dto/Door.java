package com.bean.reference.dto;

public class Door {
	
	private String type;
	private String mark;
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getMark() {
		return mark;
	}
	public void setMark(String mark) {
		this.mark = mark;
	}
	@Override
	public String toString() {
		return "Door [type=" + type + ", mark=" + mark + "]";
	}
	
	

}
