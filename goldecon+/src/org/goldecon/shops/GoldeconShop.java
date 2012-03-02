package org.goldecon.shops;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.goldecon.goldeconplus.Goldecon;

public class GoldeconShop {
	
	Goldecon plugin;
	// Set the text for system messages
	String ver = Goldecon.ver;
	String edition = Goldecon.edition;
	
	public GoldeconShop(Goldecon goldecon){
		this.plugin = goldecon;
	}

	public boolean shopHelp(Player player){
        player.sendMessage(ChatColor.GREEN + "<- goldecon+ | Shop Commands ->");
        player.sendMessage(ChatColor.RED + "| goldecon+ by Stoolbend - Version " + ver + " |");
        player.sendMessage(ChatColor.RED + "| goldecon by boardinggamer & Kierrow |");
        player.sendMessage(ChatColor.AQUA + "COMMANDS:");
        player.sendMessage(ChatColor.LIGHT_PURPLE + "/geshophelp");
        player.sendMessage(ChatColor.GOLD + "- Opens this");
        player.sendMessage(ChatColor.LIGHT_PURPLE + "/geshop " + ChatColor.DARK_PURPLE + "<item name> <amount> <buy price> <sell price>");
        player.sendMessage(ChatColor.GOLD + "- creates a shop");
        player.sendMessage(ChatColor.AQUA + "HOW TO USE:");
        player.sendMessage(ChatColor.LIGHT_PURPLE + "To Buy:");
        player.sendMessage(ChatColor.GOLD + "- Right click on the sign");
        player.sendMessage(ChatColor.LIGHT_PURPLE + "To Sell:");
        player.sendMessage(ChatColor.GOLD + "- Left click on the sign");
        return true;
	}
	
	public boolean shopCreate(Player plr, String[] args){
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
		return true;
	}
}