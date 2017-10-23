package com.rex2go.comapi.bungee.command;

import com.rex2go.comapi.bungee.ComAPI;

import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class PlayerListCommand extends Command {

	public PlayerListCommand() {
		super("playerlist");
	}

	@Override
	public void handle(String callbackId, String[] args) {
		if(args != null && callbackId != null) {
			if(args.length >= 1) {
				String serverName = args[0];
				
				if(ComAPI.getInstance().getProxy().getServers().containsKey(serverName)) {
					String s = "";
					for(ProxiedPlayer player : ComAPI.getInstance().getProxy().getServers().get(serverName).getPlayers()) {
						s += player.getName()+ ", ";
					}
					s = s.substring(0, s.length() - 2);
					
					ServerInfo serverInfo = ComAPI.getInstance().getProxy().getServerInfo(callbackId.split(":")[0]);
					if(serverInfo != null) {
						sendToBukkit(serverInfo, callbackId, s);
					}
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
