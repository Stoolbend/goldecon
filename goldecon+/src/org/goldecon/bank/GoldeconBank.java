package org.goldecon.bank;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.goldecon.goldeconplus.Goldecon;

public class GoldeconBank {

	Goldecon plugin;
	// Set the text for system messages
	String ver = Goldecon.ver;
	String edition = Goldecon.edition;
	
	public GoldeconBank(Goldecon goldecon){
		this.plugin = goldecon;
	}
	
	public static void playerJoin(PlayerJoinEvent e){
		if (!Goldecon.banks.contains(e.getPlayer().getName()))
		{
		      Goldecon.banks.set(e.getPlayer().getName(), Integer.valueOf(0));
		}
	}
	
	public void deposit(Player player, int amount){
        if (player.getInventory().contains(Material.GOLD_NUGGET, amount))
        {
        	player.getInventory().removeItem(new ItemStack[] { new ItemStack(Material.GOLD_NUGGET, amount) });
        	int old_amount = ((Integer)Goldecon.banks.get(player.getName())).intValue();
        	Goldecon.banks.set(player.getName(), Integer.valueOf(old_amount + amount));
        	player.sendMessage(edition + ChatColor.GOLD + "You now have " + (old_amount + amount) + " in your bank.");
        }
        else
        {
        	player.sendMessage(edition + ChatColor.RED + "You don't have that much money with you.");
        }
	}
	
	public boolean depositToOther(Player sender, String player, int amount){
		if(Goldecon.banks.contains(player))
		{
			//TODO Unsure if needed yet.
        	// sender.getInventory().removeItem(new ItemStack[] { new ItemStack(Material.GOLD_NUGGET, amount) });
        	Goldecon.banks.set(player, (Goldecon.banks.get(player) + amount));
        	sender.sendMessage(edition + ChatColor.GREEN + "You have paid " + ChatColor.DARK_GREEN + amount + ChatColor.GREEN + " to " + ChatColor.DARK_GREEN + player);
			return true;
		}
		else
		{
        	sender.sendMessage(edition + ChatColor.RED + "That player doesn't exist (or hasn't played here yet).");
			return false;
		}
	}

	public void withdraw(Player player, int amount) {
		int bankamount = ((Integer)Goldecon.banks.get(player.getName())).intValue();
        if (bankamount >= amount)
        {
        	player.getInventory().addItem(new ItemStack[] { new ItemStack(Material.GOLD_NUGGET, amount) });
        	Goldecon.banks.set(player.getName(), Integer.valueOf(bankamount - amount));
        	player.sendMessage(edition + ChatColor.GOLD + "You now have " + (bankamount - amount) + " in your bank.");
        }
        else
        {
        	player.sendMessage(edition + ChatColor.RED + "You don't have that much money in your bank.");
        }
	}
	
	public boolean viewBalance(Player sender, String player){
		if(Goldecon.banks.contains(player))
		{
			//TODO Unsure if needed yet.
        	// sender.getInventory().removeItem(new ItemStack[] { new ItemStack(Material.GOLD_NUGGET, amount) });
        	int balance = Goldecon.banks.get(player);
        	sender.sendMessage(edition + ChatColor.GREEN + "Player " + ChatColor.DARK_GREEN + player + ChatColor.GREEN + " has " + ChatColor.DARK_GREEN + balance + ChatColor.GREEN + " in their bank.");
			return true;
		}
		else
		{
        	sender.sendMessage(edition + ChatColor.RED + "That player doesn't exist (or hasn't played here yet).");
			return false;
		}
	}
}
