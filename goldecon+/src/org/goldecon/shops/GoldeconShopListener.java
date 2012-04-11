package org.goldecon.shops;

import java.util.Map;

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
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.goldecon.goldeconplus.Goldecon;

public class GoldeconShopListener implements Listener
{
	boolean debug = false;
	Goldecon plugin;
	String edition = Goldecon.edition;
	String edition2 = GoldeconShop.edition2;
	Map<String, Integer> wizMap = GoldeconShop.wizMap;
	Map<String, Integer> signWiz = GoldeconShop.signWiz;
  
	public GoldeconShopListener(Goldecon goldecon)
	{
	  this.plugin = goldecon;
	}
	
	@EventHandler
	public void shopChatListener(PlayerChatEvent e){
		Player player = e.getPlayer();
		// Deal with shop creation wizard. (wizMap)
		if(wizMap.containsKey(player.getName()))
		{
			int stage;
			if(e.getMessage().equalsIgnoreCase("quit"))
			{
				wizMap.remove(player.getName());
				e.setCancelled(true);
				player.sendMessage(edition2 + ChatColor.GOLD + "You have quit the wizard.");
			}
			else
			{
				stage = wizMap.get(player.getName());
				if(stage>=0)
				{
					String tempmsg = e.getMessage();
					e.setCancelled(true);
					GoldeconShop.shopWizard(player, stage, tempmsg);
				}
			}
		}
		// Deal with sign shop wizard (signWiz)
		else if(signWiz.containsKey(player.getName()))
		{
			int stage;
			if(e.getMessage().equalsIgnoreCase("quit"))
			{
				signWiz.remove(player.getName());
				e.setCancelled(true);
				player.sendMessage(edition2 + ChatColor.GOLD + "You have quit the shop.");
			}
			else
			{
				stage = signWiz.get(player.getName());
				if(stage>=0)
				{
					String tempmsg = e.getMessage();
					e.setCancelled(true);
					GoldeconShop.signWizard(player, stage, tempmsg);
				}
			}
		}
	}
	
	@EventHandler
	public void shopSignBreak(BlockBreakEvent event)
	{
		Block block = event.getBlock();
		Player plr = event.getPlayer();

	    if ((block.getTypeId() == 63) || (block.getTypeId() == 68))
	    {
	      Sign s = (Sign)block.getState();

	      if (s.getLine(0).equalsIgnoreCase("[shop]"))
	      {
	        if (!s.getLine(1).equalsIgnoreCase(plr.getName()))
	        {
	          event.setCancelled(true);
	          plr.sendMessage(edition2 + ChatColor.RED + "You can not break someone else's shop.");
	          s.update();
	        }
	        else
	        {
	          plr.sendMessage(edition2 + ChatColor.AQUA + "You broke your shop.");
	        }
	      }
	    }
	}

	@EventHandler
	public void shopSignChange(SignChangeEvent event)
	{
	  if (event.getLine(0).equalsIgnoreCase("[newshop]")) {
	    event.getPlayer().sendMessage(edition2 + ChatColor.DARK_PURPLE + "Now run /geshop create to make it a working shop!");
	  }
	  if (event.getLine(0).equalsIgnoreCase("[shop]")) {
		    event.getPlayer().sendMessage(edition2 + ChatColor.RED + "You dont make a shop this way. Make a sign with [newshop] on the top line.");
		    event.setCancelled(true);
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
	      
	   if ((line0.equalsIgnoreCase("[shop]")) && (block2.getType() == Material.CHEST))
	   {
	       if ((!line1.equalsIgnoreCase(plr.getName())))
	       {
	    	   if(signWiz.containsKey(plr.getName()))
	    	   {
		    	   plr.sendMessage(edition2 + ChatColor.RED + "You are already using a shop.");
	    	   }
	    	   else
	    	   {
	    	   // do the shop wizard.
	    	   GoldeconShop.signWizard(plr, 0, "start_from_event");
	    	   }
	       }
	       else
	       {
	    	   plr.sendMessage(edition2 + ChatColor.RED + "You can't use your own shop!");
	       }
	   	}
     }
     else if (block.getType() == Material.CHEST)
     {
    	 Block block3 = block.getLocation().add(0.0D, 1.0D, 0.0D).getBlock();
    	 if ((block3.getTypeId() == 63) || (block3.getTypeId() == 68))
    	 {
    		 Sign s = (Sign)block3.getState();
    		 String line0 = s.getLine(0);
    		 String line1 = s.getLine(1);
    		 if ((line0.equalsIgnoreCase("[shop]")) && (!plr.isOp()) && (!line1.equalsIgnoreCase(plr.getName())))
    		 {
    			 event.setCancelled(true);
    			 plr.sendMessage(edition2 + ChatColor.RED + "You can not open someone else's shop.");
    		 }
    	 }
     }
   }
   else if (event.getAction() == Action.LEFT_CLICK_BLOCK)
   {
     Block block = event.getClickedBlock();
     if ((block.getTypeId() == 63) || (block.getTypeId() == 68))
     {
       Sign s = (Sign)block.getState();
       String line0 = s.getLine(0);
       if (line0.equalsIgnoreCase("[shop]"))
       {
           plr.sendMessage(edition2 + ChatColor.RED + "Right click the sign to perform shop actions.");
       }
     }
     else if (block.getType() == Material.CHEST) {
       Block block3 = block.getLocation().add(0.0D, 1.0D, 0.0D).getBlock();
       if ((block3.getTypeId() == 63) || (block3.getTypeId() == 68)) {
         Sign s = (Sign)block3.getState();
         String line0 = s.getLine(0);
         String line1 = s.getLine(1);
          if ((line0.equalsIgnoreCase("[shop]")) && (!plr.isOp()) && (!line1.equalsIgnoreCase(plr.getName())))
          {
        	  event.setCancelled(true);
        	  plr.sendMessage(edition2 + ChatColor.RED + "You can not break someone else's shop.");
          }
       }
     }
   }
 }
}