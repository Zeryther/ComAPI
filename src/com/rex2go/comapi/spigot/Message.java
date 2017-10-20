package com.rex2go.comapi.spigot;

public class Message {

	String command, callbackId;
	String[] args;

	public Message(String command, String callbackId, String ... args) {
		this.command = command;
		this.callbackId = callbackId;
		this.args = args;
	}
	
	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}

	public String getCallbackId() {
		return callbackId;
	}

	public void setCallbackId(String callbackId) {
		this.callbackId = callbackId;
	}

	public String[] getArgs() {
		return args;
	}

	public void setArgs(String[] args) {
		this.args = args;
	}
}
