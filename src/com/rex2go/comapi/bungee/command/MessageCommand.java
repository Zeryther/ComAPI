package com.rex2go.comapi.bungee.command;

import com.rex2go.comapi.bungee.ComAPI;

import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.config.ServerInfo;

public class MessageCommand extends Command {

	public MessageCommand() {
		super("message");
	}

	@Override
	public void handle(String callbackId, String[] args) {
		if (args != null && callbackId != null) {
			if(args.length >= 2) {
				int exitCode = 1; // 0 = OK, 1 = Player offline
				String playerName = args[0];
				String message = args[1];
				
				if(ComAPI.getInstance().getProxy().getPlayer(playerName) != null) {
					ComAPI.getInstance().getProxy().getPlayer(playerName).sendMessage(new TextComponent(message));
					exitCode = 0;
				}
				
				ServerInfo serverInfo = ComAPI.getInstance().getProxy().getServerInfo(callbackId.split(":")[0]);
				if(serverInfo != null) {
					sendToBukkit(serverInfo, callbackId, new String[] { exitCode + "" });
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
