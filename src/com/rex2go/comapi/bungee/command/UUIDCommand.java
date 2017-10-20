package com.rex2go.comapi.bungee.command;

import com.rex2go.comapi.bungee.ComAPI;

import net.md_5.bungee.api.config.ServerInfo;

public class UUIDCommand extends Command {

	public UUIDCommand() {
		super("uuid");
	}

	@SuppressWarnings("deprecation")
	@Override
	public void handle(String callbackId, String[] args) {
		if(args != null && callbackId != null) {
			if(args.length >= 1) {
				String playerName = args[0];
				String uuid = "null";
				
				if(ComAPI.getInstance().getProxy().getPlayer(playerName) != null) {
					uuid = ComAPI.getInstance().getProxy().getPlayer(playerName).getUUID();
				}
				
				ServerInfo serverInfo = ComAPI.getInstance().getProxy().getServerInfo(callbackId.split(":")[0]);
				if(serverInfo != null) {
					sendToBukkit(serverInfo, callbackId, new String[] { uuid });
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
