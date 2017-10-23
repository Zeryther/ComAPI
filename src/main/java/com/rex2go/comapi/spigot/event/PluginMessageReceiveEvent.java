package com.rex2go.comapi.spigot.event;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.rex2go.comapi.spigot.Message;

public class PluginMessageReceiveEvent extends Event {

	private static final HandlerList handlers = new HandlerList();
	private Message message;

	public PluginMessageReceiveEvent(Message message) {
		this.message = message;
	}
	
	public Message getMessage() {
		return message;
	}
	
	@Override
	public HandlerList getHandlers() {
		return handlers;
	}
	
	public static HandlerList getHandlerList() {
        return handlers;
    }
}
