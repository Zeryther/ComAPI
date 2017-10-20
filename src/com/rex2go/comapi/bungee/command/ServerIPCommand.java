package com.rex2go.comapi.bungee.command;

import com.rex2go.comapi.bungee.ComAPI;

import net.md_5.bungee.api.config.ServerInfo;

public class ServerIPCommand extends Command {

	public ServerIPCommand() {
		super("serverip");
	}

	@Override
	public void handle(String callbackId, String[] args) {
		if(args != null && callbackId != null) {
			if(args.length >= 1) {
				String serverName = args[0];
				String hostString = "null";
				
				if(ComAPI.getInstance().getProxy().getServers().containsKey(serverName)) {
					hostString = ComAPI.getInstance().getProxy().getServerInfo(serverName).getAddress().getHostString() + ":" +
							ComAPI.getInstance().getProxy().getServerInfo(serverName).getAddress().getPort();
				}
				
				ServerInfo serverInfo = ComAPI.getInstance().getProxy().getServerInfo(callbackId.split(":")[0]);
				if(serverInfo != null) {
					sendToBukkit(serverInfo, callbackId, new String[] { hostString });
				}
			} else {
				ServerInfo serverInfo = ComAPI.getInstance().getProxy().getServerInfo(callbackId.split(":")[0]);
				if(serverInfo != null) {
					sendErrorToBukkit(serverInfo, callbackId, new String[] { "Too few arguments" });
				}
			}
		}
	}
}
