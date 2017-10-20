package com.rex2go.comapi.spigot;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.UUID;

import org.bukkit.Bukkit;

public abstract class Callback {

	private String id;
	private int timeout = 20, taskId = -1;
	private boolean gotResponse = false;
	
	public Callback(String serverName, int ... timeout) {
		if(serverName == null) {
			fail(FailReason.NO_SERVER_NAME);
		}
		if(timeout != null && timeout.length > 0) {
			this.timeout = timeout[0];
		}
		id = serverName + ":" + UUID.randomUUID().toString();
		startTimeout();
		ComAPI.getInstance().getCallbacks().add(this);
	}
	
	private void startTimeout() {
		taskId = Bukkit.getScheduler().scheduleSyncDelayedTask(ComAPI.getInstance(), new Runnable() {
			
			@Override
			public void run() {
				if(!hasGotResponse()) {
					fail(FailReason.TIMEOUT);
				}
				
			}
		}, this.timeout);
	}
	
	public void response(DataInputStream in) {
		try {
			handleResponse(ComAPI.getInstance().inputStreamToMessage(in));
			gotResponse = true;
		} catch (IOException e) {
			e.printStackTrace();
			fail(FailReason.ERROR);
		}
	}	
	
	public void fail(FailReason failReason) {
		handleFail(failReason);
		if(taskId != -1) Bukkit.getScheduler().cancelTask(taskId);
		if(ComAPI.getInstance().getCallbacks().contains(this)) ComAPI.getInstance().getCallbacks().remove(this);
	}
	
	protected abstract void handleFail(FailReason failReason);
	
	protected abstract void handleResponse(Message incomingMessage);
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public int getTimeout() {
		return timeout;
	}
	
	public int getTaskId()  {
		return taskId;
	}
	
	public boolean hasGotResponse() {
		return gotResponse;
	}
	
	public void setGotResponse(boolean gotResponse) {
		this.gotResponse = gotResponse;
	}
	
	public enum FailReason {
		TIMEOUT, NO_SERVER_NAME, ERROR
	}
}
