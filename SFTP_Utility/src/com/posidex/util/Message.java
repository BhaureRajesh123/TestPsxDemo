package com.posidex.util;

import java.util.HashMap;

public class Message {

	private HashMap<String,String> message;
	private boolean status;
	/**
	 * @return the message
	 */
	public HashMap<String, String> getMessage() {
		return message;
	}
	/**
	 * @param message the message to set
	 */
	public void setMessage(HashMap<String, String> message) {
		this.message = message;
	}
	/**
	 * @return the status
	 */
	public boolean isStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(boolean status) {
		this.status = status;
	}
	
	
}