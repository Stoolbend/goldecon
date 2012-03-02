package org.goldecon.goldeconplus;

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
		player.sendMessage(ChatColor.GOLD + "<- goldecon+ | Core Commands ->");
        player.sendMessage(ChatColor.RED + "| goldecon+ by Stoolbend - Version " + ver + " |");
        player.sendMessage(ChatColor.RED + "| goldecon by boardinggamer & Kierrow |");
        player.sendMessage(ChatColor.AQUA + "/gebank " + ChatColor.LIGHT_PURPLE + "deposit <amount>" + ChatColor.GREEN + "- deposits gold into bank");
        player.sendMessage(ChatColor.AQUA + "/gebank " + ChatColor.LIGHT_PURPLE + "withdraw <amount>" + ChatColor.GREEN + "- withdraws gold from bank");
        player.sendMessage(ChatColor.AQUA + "/gebank " + ChatColor.LIGHT_PURPLE + "money " + ChatColor.GREEN + "- gets your bank amount");
        player.sendMessage(ChatColor.AQUA + "/ge " + ChatColor.LIGHT_PURPLE + "pay <player name> <amount>" + ChatColor.GREEN + "- pay a player");
        player.sendMessage(ChatColor.AQUA + "/ge " + ChatColor.LIGHT_PURPLE + "stats " + ChatColor.GREEN + "- gets the goldecon stats");
        player.sendMessage(ChatColor.AQUA + "/ge " + ChatColor.LIGHT_PURPLE + "help " + ChatColor.GREEN + "- shows this message");
        return true;
	}
	
public String getTopPlayer()
{
int top_amount = 0;
String top_player = "";
String[] players = Goldecon.banks.getAllKeys();
for (int i = 0; i < players.length; i++) {
int amount = ((Integer)Goldecon.banks.get(players[i])).intValue();
if (amount > top_amount) {
top_amount = amount;
top_player = players[i];
}
}
return top_player;
}
String getSecondPlayer() {
int top_amount = 0;
String top_player = "";
int second_amount = 0;
String second_player = "";
String[] players = Goldecon.banks.getAllKeys();
for (int i = 0; i < players.length; i++) {
int amount = ((Integer)Goldecon.banks.get(players[i])).intValue();
if (amount > top_amount) {
second_amount = top_amount;
second_player = top_player;
top_amount = amount;
top_player = players[i];
} else if (amount > second_amount) {
second_amount = amount;
second_player = players[i];
}
}
return second_player;
}
String getThirdPlayer() {
int top_amount = 0;
	    String top_player = "";

	    int second_amount = 0;
	    String second_player = "";

	    int third_amount = 0;
	    String third_player = "";

	    String[] players = Goldecon.banks.getAllKeys();

	    for (int i = 0; i < players.length; i++) {
	      int amount = ((Integer)Goldecon.banks.get(players[i])).intValue();

	      if (amount > top_amount) {
	        third_amount = second_amount;
	        third_player = second_player;

	        second_amount = top_amount;
	        second_player = top_player;

	        top_amount = amount;
	        top_player = players[i];
	      } else if (amount > second_amount) {
	        third_amount = second_amount;
	        third_player = second_player;

	        second_amount = amount;
	        second_player = players[i];
	      } else if (amount > third_amount) {
	        third_amount = amount;
	        third_player = players[i];
	      }
	    }

	    return third_player;
	  }
}
