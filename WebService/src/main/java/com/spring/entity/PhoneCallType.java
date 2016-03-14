package com.spring.entity;

public enum PhoneCallType {
	OUTGOING_TYPE(2), INCOMING_TYPE(1), MISSED_TYPE(3);
	private int value;

	private PhoneCallType(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}
	
}
