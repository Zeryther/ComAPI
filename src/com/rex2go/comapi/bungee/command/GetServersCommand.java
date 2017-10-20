package com.rex2go.comapi.bungee.command;

import java.util.Map;

import com.rex2go.comapi.bungee.ComAPI;

import net.md_5.bungee.api.config.ServerInfo;

public class GetServersCommand extends Command {

	public GetServersCommand() {
		super("getservers");
	}

	@Override
	public void handle(String callbackId, String[] args) {
		if (args != null && callbackId != null) {
			String s = "";
			for (Map.Entry<String, ServerInfo> serverInfo : ComAPI.getInstance().getProxy().getServers().entrySet()) {
				s += serverInfo.getKey() + ", ";
			}
			s = s.substring(0, s.length() - 2);

			ServerInfo serverInfo = ComAPI.getInstance().getProxy().getServerInfo(callbackId.split(":")[0]);
			if (serverInfo != null) {
				sendToBukkit(serverInfo, callbackId, new String[] { s });
			}
		}
	}
}
