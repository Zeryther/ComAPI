package com.rex2go.comapi.bungee.listener;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import com.rex2go.comapi.bungee.ComAPI;
import com.rex2go.comapi.bungee.command.Command;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.event.PluginMessageEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class ChannelListener implements Listener {

	public ChannelListener() {
		ProxyServer.getInstance().getPluginManager().registerListener(ComAPI.getInstance(), this);
	}

	@EventHandler
	public void onPluginMessage(PluginMessageEvent e) {
		if (e.getTag().equalsIgnoreCase("BungeeCord")) {
			DataInputStream in = new DataInputStream(new ByteArrayInputStream(e.getData()));
			
			try {
				String callbackId = in.readUTF();
				String command = in.readUTF();
				
				for(Command command1 : ComAPI.getPluginCommandManager().getCommands()) {
					if(command1.getCommand().equalsIgnoreCase(command)) {
						ArrayList<String> args = new ArrayList<>();
						
						while(in.available() > 0) {
							args.add(in.readUTF());
						}
						
						command1.handle(callbackId, args.toArray(new String[]{}));
					}
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}

		}
	}

	public void sendToBukkit(String channel, ServerInfo server, String ... message) {
		if(message != null) {
			ByteArrayOutputStream stream = new ByteArrayOutputStream();
			DataOutputStream out = new DataOutputStream(stream);
			try {
				out.writeUTF(channel);
				
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
