package org.goldecon.shops;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
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

public class GoldeconShopListener implements Listener
{
	boolean debug = false;
	Goldecon plugin;
	String edition = Goldecon.edition;
  
	public GoldeconShopListener(Goldecon goldecon)
	{
	  this.plugin = goldecon;
	}
	
	@EventHandler
	public void shopSignBreak(BlockBreakEvent event)
	{
		Block block = event.getBlock();
		Player plr = event.getPlayer();

	    if ((block.getTypeId() == 63) || (block.getTypeId() == 68))
	    {
	      Sign s = (Sign)block.getState();
	      String line0 = s.getLine(0);
	      String line1 = s.getLine(1);

	      if (line0.equalsIgnoreCase("[shop]"))
	      {
	        if (!plr.getName().equalsIgnoreCase(line1))
	        {
	          event.setCancelled(true);
	          plr.sendMessage(edition + ChatColor.RED + "You can not break someone else's shop.");
	          s.update();
	        }
	        else
	        {
	          plr.sendMessage(edition + ChatColor.AQUA + "You broke your shop.");
	        }
	      }
	    }
	}

	@EventHandler
	public void shopSignChange(SignChangeEvent event)
	{
	  if (event.getLine(0).equalsIgnoreCase("[shop]")) {
	    event.setCancelled(true);
	    event.getPlayer().sendMessage(edition + ChatColor.RED + "You cant create a shop that way.");
	  }
	}
	
	@EventHandler
	public void shopSignAction(PlayerInteractEvent event) {
	  Player plr = event.getPlayer();
	  if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
	    Block block = event.getClickedBlock();
	    Block block2 = block.getLocation().add(0.0D, -1.0D, 0.0D).getBlock();
	    if ((block.getTypeId() == 63) || (block.getTypeId() == 68)) {
	      Sign s = (Sign)block.getState();
	      String line0 = s.getLine(0);
	      String line1 = s.getLine(1);
	      String line2 = s.getLine(2);
	      String line3 = s.getLine(3);
	      
	      if (line0.equalsIgnoreCase("[shop]")) {
	        String input = line3;
	        String[] output = input.split(":");
	        String bprice = output[0];
	        String amount = output[2];
	        event.setCancelled(true);
         int itemamount = Integer.parseInt(amount.replace("A ", "").trim());
         int price = Integer.parseInt(bprice.replace("B ", "").trim());
         if ((block2.getType() == Material.CHEST) && 
           (!line1.equalsIgnoreCase(plr.getName()))) {
           if (plr.getInventory().contains(Material.GOLD_NUGGET, price)) {
             Chest chest = (Chest)block2.getState();
            //TODO Ensure this fix for gh-3 is working
          	Material item = Material.matchMaterial(line2);
          	if(item == null){
                  plr.sendMessage(edition + ChatColor.RED + "This shop isnt working at the moment. Try re-making the shop using /geshop");
                  return;
          	}
             if (chest.getInventory().contains(item, itemamount)) {
                plr.getInventory().addItem(new ItemStack[] { new ItemStack(item, itemamount) });
               plr.getInventory().removeItem(new ItemStack[] { new ItemStack(Material.GOLD_NUGGET, price) });
               chest.getInventory().addItem(new ItemStack[] { new ItemStack(Material.GOLD_NUGGET, price) });
               chest.getInventory().removeItem(new ItemStack[] { new ItemStack(item, itemamount) });
               plr.sendMessage(edition + ChatColor.GREEN + "You got " + itemamount + " " + line2.toLowerCase().replace("_", " ") + " for " + price + " gold.");
             } else {
               plr.sendMessage(edition + ChatColor.RED + "Not enough in stock.");
             }
           } else {
             plr.sendMessage(edition + ChatColor.RED + "You do not have enough money for that.");
           }
         }
         else plr.sendMessage(edition + ChatColor.RED + "You can't use your own shop!");
	  }
     }
     else if (block.getType() == Material.CHEST) {
       Block block3 = block.getLocation().add(0.0D, 1.0D, 0.0D).getBlock();
       if ((block3.getTypeId() == 63) || (block3.getTypeId() == 68)) {
         Sign s = (Sign)block3.getState();
         String line0 = s.getLine(0);
         String line1 = s.getLine(1);
          if ((line0.equalsIgnoreCase("[shop]")) && 
           (!plr.isOp()) && 
           (!line1.equalsIgnoreCase(plr.getName()))) {
           event.setCancelled(true);
           plr.sendMessage(edition + ChatColor.RED + "You can not open someone else's shop.");
          }
       }
     }
    }
   else if (event.getAction() == Action.LEFT_CLICK_BLOCK) {
     Block block = event.getClickedBlock();
     Block block2 = block.getLocation().add(0.0D, -1.0D, 0.0D).getBlock();
     if ((block.getTypeId() == 63) || (block.getTypeId() == 68)) {
       Sign s = (Sign)block.getState();
       String line0 = s.getLine(0);
       String line1 = s.getLine(1);
       String line2 = s.getLine(2);
       String line3 = s.getLine(3);
        if (line0.equalsIgnoreCase("[shop]")) {
         String input = line3;
         String[] output = input.split(":");
         String sprice = output[1];
         String amount = output[2];
         event.setCancelled(true);
          int itemamount = Integer.parseInt(amount.replace("A ", " ").trim());
         int price = Integer.parseInt(sprice.replace("S ", " ").trim());
         if ((block2.getType() == Material.CHEST) && 
           (!line1.equalsIgnoreCase(plr.getName()))) {
           Chest chest = (Chest)block2.getState();
           //TODO Ensure this fix for gh-3 is working
         	Material item = Material.matchMaterial(line2);
         	if(item == null){
                 plr.sendMessage(edition + ChatColor.RED + "This shop isnt working at the moment. Try re-making the shop using /geshop");
                 return;
         	}
           if (plr.getInventory().contains(item, itemamount)) {
             if (chest.getInventory().contains(Material.GOLD_NUGGET, price)) {
               plr.getInventory().removeItem(new ItemStack[] { new ItemStack(item, itemamount) });
               plr.getInventory().addItem(new ItemStack[] { new ItemStack(Material.GOLD_NUGGET, price) });           }
               chest.getInventory().removeItem(new ItemStack[] { new ItemStack(Material.GOLD_NUGGET, price) });
               chest.getInventory().addItem(new ItemStack[] { new ItemStack(item, itemamount) });
               plr.sendMessage(edition + ChatColor.GREEN + "You sold " + itemamount + " " + line2.toLowerCase().replace("_", " ") + " for " + price + " gold.");
             } else {
               plr.sendMessage(edition + ChatColor.RED + "Not enough gold in stock.");
             }
           }
           else plr.sendMessage(edition + ChatColor.RED + "You do not have enough for that.");
         }
         else plr.sendMessage(edition + ChatColor.RED + "You can't use your own shop!");
       }
     else if (block.getType() == Material.CHEST) {
       Block block3 = block.getLocation().add(0.0D, 1.0D, 0.0D).getBlock();
       if ((block3.getTypeId() == 63) || (block3.getTypeId() == 68)) {
         Sign s = (Sign)block3.getState();
         String line0 = s.getLine(0);
         String line1 = s.getLine(1);
          if ((line0.equalsIgnoreCase("[shop]")) && 
           (!plr.isOp()) && 
           (!line1.equalsIgnoreCase(plr.getName()))) {
           event.setCancelled(true);
           plr.sendMessage(edition + ChatColor.RED + "You can not break someone else's shop.");
         }
       }
     }
   }
 }
}