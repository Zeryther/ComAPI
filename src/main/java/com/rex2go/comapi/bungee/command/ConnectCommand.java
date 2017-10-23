package com.rex2go.comapi.bungee.command;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

import com.rex2go.comapi.bungee.ComAPI;

import net.md_5.bungee.api.config.ServerInfo;

public class ConnectCommand extends Command {

	public ConnectCommand() {
		super("connect");
	}

	@Override
	public void handle(String callbackId, String[] args) {
		if(args != null && callbackId != null) {
			if(args.length >= 2) {
				int exitCode = 0; // 0 = OK, 1 = Player offline, 2 = Already connected, 3 = Server not existing, 4 = Server offline
				String playerName = args[0];
				String serverName = args[1];
				
				if(ComAPI.getInstance().getProxy().getPlayer(playerName) != null) {
					if(!ComAPI.getInstance().getProxy().getPlayer(playerName).getServer().getInfo().getName().equalsIgnoreCase(serverName)) {
						if(ComAPI.getInstance().getProxy().getServers().containsKey(serverName)) {
							ServerInfo targetServer = ComAPI.getInstance().getProxy().getServers().get(serverName);
							try {
								Socket s = new Socket();
								s.connect(new InetSocketAddress(targetServer.getAddress().getHostName(), targetServer.getAddress().getPort()), 15);
								s.close();
								
								ComAPI.getInstance().getProxy().getPlayer(playerName).connect(targetServer);
							} catch (IOException e) {
								exitCode = 4;
							}
						} else {
							exitCode = 3;
						}
					} else {
						exitCode = 2;
					}
				} else {
					exitCode = 1;
				}
				
				ServerInfo serverInfo = ComAPI.getInstance().getProxy().getServerInfo(callbackId.split(":")[0]);
				if(serverInfo != null) {
					sendToBukkit(serverInfo, callbackId, String.valueOf(exitCode));
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
