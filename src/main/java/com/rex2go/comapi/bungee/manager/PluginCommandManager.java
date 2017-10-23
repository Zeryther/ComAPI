package com.rex2go.comapi.bungee.manager;

import java.util.ArrayList;

import com.rex2go.comapi.bungee.command.Command;
import com.rex2go.comapi.bungee.command.ConnectCommand;
import com.rex2go.comapi.bungee.command.GetServerCommand;
import com.rex2go.comapi.bungee.command.GetServersCommand;
import com.rex2go.comapi.bungee.command.IPCommand;
import com.rex2go.comapi.bungee.command.KickPlayerCommand;
import com.rex2go.comapi.bungee.command.MessageCommand;
import com.rex2go.comapi.bungee.command.PlayerCountCommand;
import com.rex2go.comapi.bungee.command.PlayerListCommand;
import com.rex2go.comapi.bungee.command.ServerIPCommand;
import com.rex2go.comapi.bungee.command.UUIDCommand;
import com.rex2go.comapi.bungee.command.WhoAmICommand;

public class PluginCommandManager {

	protected ArrayList<Command> commands = new ArrayList<>();
	
	public PluginCommandManager() {
		registerDefaultCommands();
	}
	
	private void registerDefaultCommands() {
		registerCommand(new PlayerCountCommand());
		registerCommand(new WhoAmICommand());
		registerCommand(new ConnectCommand());
		registerCommand(new IPCommand());
		registerCommand(new PlayerListCommand());
		registerCommand(new GetServersCommand());
		registerCommand(new MessageCommand());
		registerCommand(new GetServerCommand());
		registerCommand(new UUIDCommand());
		registerCommand(new ServerIPCommand());
		registerCommand(new KickPlayerCommand());
	}
	
	public void registerCommand(Command command) {
		commands.add(command);
	}
	
	public ArrayList<Command> getCommands() {
		return commands;
	}
}
