package com.rex2go.comapi.bungee;

import com.rex2go.comapi.bungee.listener.ChannelListener;
import com.rex2go.comapi.bungee.manager.PluginCommandManager;

import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.plugin.Plugin;

public class ComAPI extends Plugin {

	private static ComAPI instance;
	
	private static PluginCommandManager pluginCommandManager;
	
	public void onEnable() {
		instance = this;
		
        BungeeCord.getInstance().registerChannel("BungeeCord");
        
		pluginCommandManager = new PluginCommandManager();
		
		registerListeners();
	}

	private void registerListeners() {
		new ChannelListener();
	}

	public static PluginCommandManager getPluginCommandManager() {
		return pluginCommandManager;
	}
	
	public static ComAPI getInstance() {
		return instance;
	}
}
