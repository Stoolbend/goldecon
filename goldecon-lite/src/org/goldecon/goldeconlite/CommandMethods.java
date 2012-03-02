package org.goldecon.goldeconlite;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class CommandMethods {

	Goldecon plugin;
	// Set the text for system messages
	String ver = Goldecon.ver;
	String edition = Goldecon.edition;
	
	public CommandMethods(Goldecon goldecon){
		this.plugin = goldecon;
	}

	public boolean coreHelp(Player player){
		player.sendMessage(ChatColor.GOLD + "<- goldecon | Core Commands ->");
        player.sendMessage(ChatColor.RED + "| goldecon-lite by Stoolbend - Version " + ver + " |");
        player.sendMessage(ChatColor.RED + "| goldecon by boardinggamer & Kierrow |");
        player.sendMessage(ChatColor.AQUA + "/gebank " + ChatColor.LIGHT_PURPLE + "deposit <amount>" + ChatColor.GREEN + "- deposits gold into bank");
        player.sendMessage(ChatColor.AQUA + "/gebank " + ChatColor.LIGHT_PURPLE + "withdraw <amount>" + ChatColor.GREEN + "- withdraws gold from bank");
        player.sendMessage(ChatColor.AQUA + "/gebank " + ChatColor.LIGHT_PURPLE + "money " + ChatColor.GREEN + "- gets your bank amount");
        player.sendMessage(ChatColor.AQUA + "/ge " + ChatColor.LIGHT_PURPLE + "pay <player name> <amount>" + ChatColor.GREEN + "- pay a player");
        player.sendMessage(ChatColor.AQUA + "/ge " + ChatColor.LIGHT_PURPLE + "stats " + ChatColor.GREEN + "- gets the goldecon stats");
        player.sendMessage(ChatColor.AQUA + "/ge " + ChatColor.LIGHT_PURPLE + "help " + ChatColor.GREEN + "- shows this message");
        return true;
	}
}
