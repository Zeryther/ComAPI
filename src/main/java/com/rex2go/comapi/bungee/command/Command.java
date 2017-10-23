package com.rex2go.comapi.bungee.command;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.md_5.bungee.api.config.ServerInfo;

public abstract class Command {

	private String command;
	
	public Command(String command) {
		this.command = command;
	}
	
	public abstract void handle(String callbackId, String[] args);
	
	public String getCommand() {
		return command;
	}
	
	protected void sendToBukkit(ServerInfo server, String callbackId, String ... message) {
		sendToBukkit(server, callbackId, getCommand(), message);
	}
	
	protected void sendErrorToBukkit(ServerInfo server, String callbackId, String ... message) {
		sendToBukkit(server, callbackId, "error", message);
	}
	
	private void sendToBukkit(ServerInfo server, String callbackId, String command, String ... message) {
		if(message != null) {
			ByteArrayOutputStream stream = new ByteArrayOutputStream();
			DataOutputStream out = new DataOutputStream(stream);
			try {
				out.writeUTF(callbackId);
				out.writeUTF(command);
				
				for(String s : message) {
					out.writeUTF(s);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			server.sendData("BungeeCord", stream.toByteArray());
		}
	}
}
