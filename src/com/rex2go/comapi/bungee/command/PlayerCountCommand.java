package com.rex2go.comapi.bungee.command;

import com.rex2go.comapi.bungee.ComAPI;

import net.md_5.bungee.api.config.ServerInfo;

public class PlayerCountCommand extends Command {

	public PlayerCountCommand() {
		super("playercount");
	}

	@Override
	public void handle(String callbackId, String[] args) {
		if(args != null && callbackId != null) {
			if(args.length >= 1) {
				String serverName = args[0];
				int playerCount = ComAPI.getInstance().getProxy().getServerInfo(serverName) == null ? 0 : ComAPI.getInstance().getProxy().getServerInfo(serverName).getPlayers().size();
				
				ServerInfo serverInfo = ComAPI.getInstance().getProxy().getServerInfo(callbackId.split(":")[0]);
				if(serverInfo != null) {
					sendToBukkit(serverInfo, callbackId, new String[] { playerCount + "" });
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
