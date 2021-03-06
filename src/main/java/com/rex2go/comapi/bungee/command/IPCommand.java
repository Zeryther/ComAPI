package com.rex2go.comapi.bungee.command;

import com.rex2go.comapi.bungee.ComAPI;

import net.md_5.bungee.api.config.ServerInfo;

public class IPCommand extends Command {

	public IPCommand() {
		super("ip");
	}

	@Override
	public void handle(String callbackId, String[] args) {
		if(args != null && callbackId != null) {
			if(args.length >= 1) {
				String playerName = args[0];
				String hostString = "null";
				
				if(ComAPI.getInstance().getProxy().getPlayer(playerName) != null) {
					hostString = ComAPI.getInstance().getProxy().getPlayer(playerName).getAddress().getHostString();
				}
				
				ServerInfo serverInfo = ComAPI.getInstance().getProxy().getServerInfo(callbackId.split(":")[0]);
				if(serverInfo != null) {
					sendToBukkit(serverInfo, callbackId, hostString);
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
