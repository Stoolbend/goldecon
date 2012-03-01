package org.goldecon.shops;


import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.goldecon.goldeconplus.Goldecon;

public class GoldeconShop {
	
	String edition = Goldecon.edition;
	String ver = Goldecon.ver;
	
	public boolean shopCommand(CommandSender sender, Command cmd, Player plr, String[] args){
	if (cmd.getName().equalsIgnoreCase("geshophelp")) {
        if (((sender instanceof Player)) && 
          (args.length == 0)) {
        	if(!Goldecon.checkPerm(plr, "goldecon.shop.create")){
        		plr.sendMessage(edition + ChatColor.RED + "You dont have permission to do that, dave.");
        		return true;
        	}
          plr.sendMessage(ChatColor.GREEN + "<- goldecon+ | Shop Commands ->");
          plr.sendMessage(ChatColor.RED + "| goldecon+ by Stoolbend - Version " + ver + " |");
          plr.sendMessage(ChatColor.RED + "| goldecon by boardinggamer & Kierrow |");
          plr.sendMessage(ChatColor.AQUA + "COMMANDS:");
          plr.sendMessage(ChatColor.LIGHT_PURPLE + "/geshophelp");
          plr.sendMessage(ChatColor.GOLD + "- Opens this");
          plr.sendMessage(ChatColor.LIGHT_PURPLE + "/geshop " + ChatColor.DARK_PURPLE + "<item name> <amount> <buy price> <sell price>");
          plr.sendMessage(ChatColor.GOLD + "- creates a shop");
          plr.sendMessage(ChatColor.AQUA + "HOW TO USE:");
          plr.sendMessage(ChatColor.LIGHT_PURPLE + "To Buy:");
          plr.sendMessage(ChatColor.GOLD + "- Right click on the sign");
          plr.sendMessage(ChatColor.LIGHT_PURPLE + "To Sell:");
          plr.sendMessage(ChatColor.GOLD + "- Left click on the sign");
          return true;
        }
      }
    else if ((cmd.getName().equalsIgnoreCase("geshop")) && (args.length < 4)){
    	if (((sender instanceof Player)) && 
    	          (args.length == 0)) {
    	        	if(!Goldecon.checkPerm(plr, "goldecon.shop.create")){
    	        		plr.sendMessage(edition + ChatColor.RED + "You dont have permission to do that, dave.");
    	        		return true;
    	        	}
    	            plr.sendMessage(ChatColor.GREEN + "<- goldecon+ | Shop Commands ->");
    	            plr.sendMessage(ChatColor.RED + "| goldecon+ by Stoolbend - Version " + ver + " |");
    	            plr.sendMessage(ChatColor.RED + "| goldecon by boardinggamer & Kierrow |");
    	          plr.sendMessage(ChatColor.AQUA + "COMMANDS:");
    	          plr.sendMessage(ChatColor.LIGHT_PURPLE + "/geshophelp");
    	          plr.sendMessage(ChatColor.GOLD + "- Opens this");
    	          plr.sendMessage(ChatColor.LIGHT_PURPLE + "/geshop " + ChatColor.DARK_PURPLE + "<item name> <amount> <buy price> <sell price>");
    	          plr.sendMessage(ChatColor.GOLD + "- creates a shop");
    	          plr.sendMessage(ChatColor.AQUA + "HOW TO USE:");
    	          plr.sendMessage(ChatColor.LIGHT_PURPLE + "To Buy:");
    	          plr.sendMessage(ChatColor.GOLD + "- Right click on the sign");
    	          plr.sendMessage(ChatColor.LIGHT_PURPLE + "To Sell:");
    	          plr.sendMessage(ChatColor.GOLD + "- Left click on the sign");
    	          return true;
    	        }
    }
      else if ((cmd.getName().equalsIgnoreCase("geshop")) && 
        ((sender instanceof Player)) && 
        (args.length == 4)) {
    	if(!Goldecon.checkPerm(plr, "goldecon.shop.create")){
    		plr.sendMessage(edition + ChatColor.RED + "You dont have permission to do that, dave.");
    		return true;
    	}
            Block block = plr.getTargetBlock(null, 5);
            String itemname = args[0].toUpperCase();
            String amount = args[1];
            String bprice = args[2];
            String sprice = args[3];

            if ((block.getTypeId() == 63) || (block.getTypeId() == 68)) {
              Sign s = (Sign)block.getState();
              String line0 = s.getLine(0);
              String line1 = s.getLine(1);
              String line2 = s.getLine(2);
              String line3 = s.getLine(3);

              if (block.getLocation().add(0.0D, -1.0D, 0.0D).getBlock().getType() == Material.CHEST) {
                if ((line0.equalsIgnoreCase("")) && (line1.equalsIgnoreCase("")) && (line2.equalsIgnoreCase("")) && (line3.equalsIgnoreCase(""))) {
                  s.setLine(0, "[shop]");
                  s.setLine(1, plr.getDisplayName());
                  if (itemname.equals("1"))
                    s.setLine(2, "STONE");
                  else if (itemname.equals("2"))
                    s.setLine(2, "GRASS");
                  else if (itemname.equals("3"))
                    s.setLine(2, "DIRT");
                  else {
                    s.setLine(2, itemname);
                  }
                  s.setLine(3, "B " + bprice + ":S " + sprice + ":A " + amount);
                  s.update();
                  plr.sendMessage(edition + ChatColor.LIGHT_PURPLE + "You have created a shop.");
                } else {
                  plr.sendMessage(edition + ChatColor.RED + "The sign must be Blank.");
                }
              }
              else plr.sendMessage(edition + ChatColor.RED + "You need a chest under the sign to create a shop."); 
            }
            else
            {
              plr.sendMessage(edition + ChatColor.RED + "You must be looking at a sign to use this command.");
            }
	}
	Goldecon.log.info("There was an error executing a /geshop command. Here comes the debug!");
	Goldecon.log.info("Player: " + plr);
	Goldecon.log.info("args: " + args);
	return false;
}
}