package com.rex2go.comapi.spigot;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.PluginMessageListener;
import org.bukkit.scheduler.BukkitRunnable;

import com.rex2go.comapi.spigot.Callback.FailReason;
import com.rex2go.comapi.spigot.event.PluginMessageReceiveEvent;

public class ComAPI extends JavaPlugin implements PluginMessageListener {
	
	private static ComAPI instance;
	
	public static String serverName = "null";
	
	protected ArrayList<Callback> callbacks = new ArrayList<>();
	
	public void onEnable() {
		instance = this;
		
		getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
		getServer().getMessenger().registerIncomingPluginChannel(this, "BungeeCord", this);
		
		new BukkitRunnable() {
			
			@Override
			public void run() {
				if(Bukkit.getOnlinePlayers().size() > 0) {
					if(ComAPI.serverName.equals("null")) {
						Callback callback = new Callback(ComAPI.serverName) {
							
							@Override
							protected void handleResponse(Message incomingMessage) {
								ComAPI.serverName = incomingMessage.getArgs()[0];
							}
							
							@Override
							protected void handleFail(FailReason failReason) {}
						};
						
						ComAPI.sendToBungeeCord(Bukkit.getOnlinePlayers().toArray(new Player[]{})[0], new Message("whoami", callback.getId(), 
								Bukkit.getOnlinePlayers().toArray(new Player[]{})[0].getName()));
					} else {
						this.cancel();
					}
				}
			}
		}.runTaskTimer(ComAPI.getInstance(), 20, 20);
	}

	@Override
	public synchronized void onPluginMessageReceived(String channel, Player player, byte[] message) {
		if (!channel.equals("BungeeCord")) {
			return;
		}
		
		DataInputStream in = new DataInputStream(new ByteArrayInputStream(message));
		try {
			String callbackId = in.readUTF();
			Callback callback = getCallback(callbackId);
			String command = in.readUTF();
			
			if(command.equalsIgnoreCase("error")) {
				callback.fail(FailReason.ERROR);
				getLogger().log(Level.WARNING, "Callback \"" + callbackId.split(":")[1] + "\" got an error as response: " + in.readUTF());
				return;
			}
			
			getServer().getPluginManager().callEvent(new PluginMessageReceiveEvent(inputStreamToMessage(new DataInputStream(new ByteArrayInputStream(message)))));
			
			if(callback != null) {
				callback.response(new DataInputStream(new ByteArrayInputStream(message))); 
				callbacks.remove(callback);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<Callback> getCallbacks() {
		return callbacks;
	}
	
	public static void sendToBungeeCord(Player player, Message message) {
		sendToBungeeCord(player, getInstance().getCallback(message.getCallbackId()), message.getCommand(), message.getArgs());
	}
	
	protected static void sendToBungeeCord(Player player, String ... args) {
		if(args == null) return;
		
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		DataOutputStream out = new DataOutputStream(stream);
		
		try {
			for(String s : args) {
				out.writeUTF(s);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if(Bukkit.getOnlinePlayers().size() > 0) {
			if(player != null) {
				player.sendPluginMessage(getInstance(), "BungeeCord", stream.toByteArray());
			} else {
				Bukkit.getServer().sendPluginMessage(getInstance(), "BungeeCord", stream.toByteArray());
			}
		}
	}
	
	public static void sendToBungeeCord(Player player, Callback callback, String command, String ... args) {
		ArrayList<String> arr = new ArrayList<>();
		String id;
		if(callback != null) {
			id = callback.getId();
		} else {
			id = "null";
		}
		
		arr.add(id);
		arr.add(command);
		
		for(String arg : args) {
			arr.add(arg);
		}
		
		sendToBungeeCord(player, arr.toArray(new String[]{}));
	}
	
	public Callback getCallback(String callbackId) {
		if(!callbacks.isEmpty()) {
			Optional<Callback> callback = callbacks.stream().filter(c -> c.getId().equals(callbackId)).findAny();
			
			if(callback.isPresent()) {
				return callback.get();
			}
		}
		return null;
	}
	
	public Message inputStreamToMessage(DataInputStream in) throws IOException {
		String callbackId = in.readUTF().split(":")[1];
		String command = in.readUTF();
		ArrayList<String> args = new ArrayList<>();
		
		while(in.available() > 0) {
			args.add(in.readUTF());
		}
		
		return new Message(command, callbackId, args.toArray(new String[]{}));
	}
	
	public static ComAPI getInstance() {
		return instance;
	}
}
