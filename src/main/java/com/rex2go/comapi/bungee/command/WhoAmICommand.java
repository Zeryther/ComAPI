package com.rex2go.comapi.bungee.command;

import com.rex2go.comapi.bungee.ComAPI;

import net.md_5.bungee.api.config.ServerInfo;

public class WhoAmICommand extends Command {

	public WhoAmICommand() {
		super("whoami");
	}

	@Override
	public void handle(String callbackId, String[] args) {
		if(args != null && callbackId != null) {
			if(args.length >= 1) {
				if(ComAPI.getInstance().getProxy().getPlayer(args[0]) != null) {
					ServerInfo serverInfo = ComAPI.getInstance().getProxy().getServerInfo(ComAPI.getInstance().getProxy().getPlayer(args[0]).getServer().getInfo().getName());
					if(serverInfo != null) {
						sendToBukkit(serverInfo, callbackId, serverInfo.getName());
					}
				}
			}
		}
	}
}
