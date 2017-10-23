package com.rex2go.comapi.bungee.command;

import com.rex2go.comapi.bungee.ComAPI;

import net.md_5.bungee.api.config.ServerInfo;

public class GetServerCommand extends Command {

	public GetServerCommand() {
		super("getserver");
	}

	@Override
	public void handle(String callbackId, String[] args) {
		if(args != null && callbackId != null) {
			if(args.length >= 1) {
				String playerName = args[0];
				String serverName = "null";
				
				if(ComAPI.getInstance().getProxy().getPlayer(playerName) != null) {
					serverName = ComAPI.getInstance().getProxy().getPlayer(playerName).getServer().getInfo().getName();
				}
				
				ServerInfo serverInfo = ComAPI.getInstance().getProxy().getServerInfo(callbackId.split(":")[0]);
				if(serverInfo != null) {
					sendToBukkit(serverInfo, callbackId, serverName);
				}
			} else {
				ServerInfo serverInfo = ComAPI.getInstance().getProxy().getServerInfo(callbackId.split(":")[0]);
				if(serverInfo != null) {
					sendErrorToBukkit(serverInfo, callbackId, "Too few arguments");
				}
			}
		}
	}
}
