package org.goldecon.regionmarket;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.goldecon.goldeconplus.Goldecon;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;

public class GoldeconRegionListener implements Listener
{
	boolean debug = false;
	Goldecon plugin;
	WorldGuardPlugin wg;
	String edition = Goldecon.edition;
	ProtectedRegion pr;
	RegionManager rm;
  
	public GoldeconRegionListener(Goldecon goldecon, WorldGuardPlugin wgp)
	{
	  this.plugin = goldecon;
	  this.wg = wgp;
	}
	
	@EventHandler
	public void regionSignBreak(BlockBreakEvent event)
	{
		Block block = event.getBlock();
		Player plr = event.getPlayer();

	    if ((block.getTypeId() == 63) || (block.getTypeId() == 68)) {
	      Sign s = (Sign)block.getState();
	      String line0 = s.getLine(0);
	      String line1 = s.getLine(1);

	      if (line0.equalsIgnoreCase("[region]"))
	        if (!plr.getName().equalsIgnoreCase(line1)) {
	          event.setCancelled(true);
	          plr.sendMessage(edition + ChatColor.RED + "You can not break someone else's region shop.");
	          s.update();
	        } else {
	          plr.sendMessage(edition + ChatColor.AQUA + "You broke your region shop.");
	        }
	    }
	}

	@EventHandler
	public void regionSignChange(SignChangeEvent event)
	{
	  if (event.getLine(0).equalsIgnoreCase("[region]")) {
	    event.setCancelled(true);
	    event.getPlayer().sendMessage(edition + ChatColor.RED + "You cant create a region shop that way.");
	  }
	}
	
	@EventHandler
	public void regionSignClick(PlayerInteractEvent event) {
	  Player plr = event.getPlayer();
	  if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
	    Block block = event.getClickedBlock();
	    if ((block.getTypeId() == 63) || (block.getTypeId() == 68)) {
	      Sign s = (Sign)block.getState();
	      String line0 = s.getLine(0);
	      String creator = s.getLine(1);
	      String region = s.getLine(2);
	      String line3 = s.getLine(3);
	      
	     if (line0.equalsIgnoreCase("[region]")) {
	     event.setCancelled(true);
         int price = Integer.parseInt(line3.replace("Price: ", "").trim());
         if (!creator.equalsIgnoreCase(plr.getName())) {
           if (plr.getInventory().contains(Material.GOLD_NUGGET, price)) {
        	   
        	   // take the gold from their inventory
               plr.getInventory().removeItem(new ItemStack[] { new ItemStack(Material.GOLD_NUGGET, price) });
        	   
        	   // if they have enough gold nuggets
        	   pr = rm.getRegion(region);
        	   pr.getMembers().addPlayer(plr.getName());
        	   
           } else {
             plr.sendMessage(edition + ChatColor.RED + "You do not have enough money for that.");
           }
         }
         else plr.sendMessage(edition + ChatColor.RED + "You can't use your own shop!");
       }
     }
   }
 }	
}
