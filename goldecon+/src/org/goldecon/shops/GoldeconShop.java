package org.goldecon.shops;

import java.util.HashMap;

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
	public static HashMap<String, Integer> wizMap;
	static ItemConvert ic;
	static HashMap<String, Material> itemMap;
	static HashMap<String, Integer> amntMap;
	static HashMap<String, Integer> buyMap;
	static HashMap<String, Integer> sellMap;
	
	public GoldeconShop(Goldecon goldecon){
		this.plugin = goldecon;
	}

	public void initShops() {
		wizMap.clear();
		amntMap.clear();
		buyMap.clear();
		sellMap.clear();
	}

	public boolean shopHelp(Player player){
        player.sendMessage(ChatColor.GREEN + "<- goldecon+ | Shop Commands ->");
        player.sendMessage(ChatColor.RED + "| goldecon+ by Stoolbend - Version " + ver + " |");
        player.sendMessage(ChatColor.RED + "| goldecon by boardinggamer & Kierrow |");
        player.sendMessage(ChatColor.AQUA + "COMMANDS:");
        player.sendMessage(ChatColor.LIGHT_PURPLE + "/geshophelp");
        player.sendMessage(ChatColor.GOLD + "- Opens this");
        player.sendMessage(ChatColor.LIGHT_PURPLE + "/geshop " + ChatColor.DARK_PURPLE + "create");
        player.sendMessage(ChatColor.GOLD + "- Starts the shop wizard");
        player.sendMessage(ChatColor.LIGHT_PURPLE + "/geshop " + ChatColor.DARK_PURPLE + "close");
        player.sendMessage(ChatColor.GOLD + "- Closes the shop your looking at");
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
              s.setLine(1, plr.getName());
              if (itemname.equals("1"))
                s.setLine(2, "STONE");
              else if (itemname.equals("2"))
                s.setLine(2, "GRASS");
              else if (itemname.equals("3"))
                s.setLine(2, "DIRT");
              else {
              //TODO Ensure this fix for gh-3 is working
            	Material item = Material.matchMaterial(itemname);
            	if(item == null){
                    plr.sendMessage(edition + ChatColor.RED + "Thats an invalid item name. Try entering the ID of the item or visit http://bit.ly/ge-items");
                    block.setType(Material.AIR);
                    return true;
            	}
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

	public static void shopWizard(Player plr, int stage, String string){
		String pre = ChatColor.DARK_PURPLE + "[geShops] ";
		if(stage == 0){
			// step 0 - check for sign & chest placement
			plr.sendMessage(pre + ChatColor.GOLD + "New Shop Wizard");
			plr.sendMessage(pre + ChatColor.GOLD + "--------------");
			
			// Check for a sign with only [shop] on line 1.
			Block block = plr.getTargetBlock(null, 5);
			if ((block.getTypeId() == 63) || (block.getTypeId() == 68))
			{
				Sign s = (Sign)block.getState();
		        String line0 = s.getLine(0);
		        String line1 = s.getLine(1);
		        String line2 = s.getLine(2);
		        String line3 = s.getLine(3);
		        if(line0 == "[shop]" && line1 == "" && line2 == "" && line3 == "")
		        {
		        	// blank sign with [shop] on the top line. Check for chest underneath now
		        	if (block.getLocation().add(0.0D, -1.0D, 0.0D).getBlock().getType() == Material.CHEST)
		        	{
		        		// chest under correct sign.
		    			plr.sendMessage(pre + ChatColor.LIGHT_PURPLE + "Step 1 - Enter the name of the item you want");
		    			plr.sendMessage(pre + ChatColor.LIGHT_PURPLE + "to sell. Try to replace any spaces with a _");
		    			plr.sendMessage(pre + ChatColor.LIGHT_PURPLE + "so I can find out what item you are on about.");
		    			plr.sendMessage(pre + ChatColor.GREEN + "Just type it in the chat below. Type 'quit' to leave the wizard.");
		    			wizMap.put(plr.getName(), 1);
		        	}
		        	else
		        	{
				    	plr.sendMessage(pre + ChatColor.RED + "Error: You need to place a chest underneath the sign.");
		        	}
		        }
		        else
		        {
			    	plr.sendMessage(pre + ChatColor.RED + "Error: Your sign needs to look like this...");
			    	plr.sendMessage(pre + ChatColor.YELLOW + "| [shop] |");
			    	plr.sendMessage(pre + ChatColor.YELLOW + "|       |");
			    	plr.sendMessage(pre + ChatColor.YELLOW + "|       |");
			    	plr.sendMessage(pre + ChatColor.YELLOW + "|       |");
		        }
			}	          
		    else
		    {
		    	plr.sendMessage(pre + ChatColor.RED + "Error: You need to be looking at a blank sign with [shop] on the top line.");
		    }
		}
		else if(stage == 1){
			// step 1 - check for valid item name (try to resolve if not valid)
			Material mat = ic.str2mat(string);
			if(mat != Material.AIR)
			{
				// Valid item name
				itemMap.put(plr.getName(), mat);
    			plr.sendMessage(pre + ChatColor.LIGHT_PURPLE + "Step 2 - Enter the amount of the item you want");
    			plr.sendMessage(pre + ChatColor.LIGHT_PURPLE + "to buy and sell. Eg: You want to buy & sell 32");
    			plr.sendMessage(pre + ChatColor.LIGHT_PURPLE + "blocks of STONE at a time. Just type, 32.");
    			plr.sendMessage(pre + ChatColor.GREEN + "Just type it in the chat below. Type 'quit' to leave the wizard.");
    			wizMap.put(plr.getName(), 2);
			}
			else
			{
		    	plr.sendMessage(pre + ChatColor.RED + "Error: I couldn't find a block / item by that name!");
		    	plr.sendMessage(pre + ChatColor.RED + "Try again if you want. Eg: for Lapis block, do LAPIS_BLOCK");
		    	plr.sendMessage(pre + ChatColor.RED + "There is a full list of stuff you can sell / buy at http://bit.ly/ge-items");
    			plr.sendMessage(pre + ChatColor.GREEN + "Just type it in the chat below. Type 'quit' to leave the wizard.");
			}
		}
		else if(stage == 2){
			//TODO step 2 - item amount
			
		}
		else if(stage == 3){
			//TODO step 3 - price people will buy item for
			
		}
		else if(stage == 4){
			//TODO step 4 - price you will buy the item from players
			
		}
		else if(stage == 5){
			//TODO step 5 - Confirm data.
			
		}
	}
	
	public void closeShop(Player plr) {
		Block block = plr.getTargetBlock(null, 5);
        
		if ((block.getTypeId() == 63) || (block.getTypeId() == 68)) {
	          Sign s = (Sign)block.getState();
	          String line0 = s.getLine(0);
	          String line1 = s.getLine(1);

	          if(line0.equalsIgnoreCase("[shop") && (line1.equalsIgnoreCase(plr.getName()) || Goldecon.checkPerm(plr, "goldecon.shop.admin")))
	          {
                  plr.sendMessage(edition + ChatColor.GRAY + "You have closed this shop.");
	        	  block.setType(Material.AIR);
	          }
	          else plr.sendMessage(edition + ChatColor.RED + "Wait a minute! This isn't your shop!");
	    }
	    else
	    {
	    	plr.sendMessage(edition + ChatColor.RED + "You must be looking at a sign to use this command.");
	    }
	}
}