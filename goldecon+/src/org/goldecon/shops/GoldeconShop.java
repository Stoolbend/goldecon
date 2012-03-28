package org.goldecon.shops;

import java.util.HashMap;
import java.util.Map;

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
	static ItemConvert ic = new ItemConvert();
	public static Map<String, Integer> wizMap = new HashMap<String, Integer>();
	static Map<String, Material> itemMap = new HashMap<String, Material>();
	static Map<String, Integer> amntMap = new HashMap<String, Integer>();
	static Map<String, Integer> buyMap = new HashMap<String, Integer>();
	static Map<String, Integer> sellMap = new HashMap<String, Integer>();
	
	public GoldeconShop(Goldecon goldecon){
		this.plugin = goldecon;
	}

	public void initShops() {
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

	public static void shopWizard(Player plr, int stage, String string){
		String pre = ChatColor.DARK_PURPLE + "[geShops] ";
		Block block = plr.getTargetBlock(null, 5);
		if(stage == 0){
			// step 0 - check for sign & chest placement
			plr.sendMessage(pre + ChatColor.GOLD + "New Shop Wizard");
			plr.sendMessage(pre + ChatColor.GOLD + "--------------");
			
			// Check for a sign with only [shop] on line 1.
			if ((block.getTypeId() == 63) || (block.getTypeId() == 68))
			{
				Sign s = (Sign)block.getState();
		        String line0 = s.getLine(0);
		        if(line0.equalsIgnoreCase("[newshop]"))
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
			    	plr.sendMessage(pre + ChatColor.YELLOW + "| [newshop] |");
			    	plr.sendMessage(pre + ChatColor.YELLOW + "|               |");
			    	plr.sendMessage(pre + ChatColor.YELLOW + "|               |");
			    	plr.sendMessage(pre + ChatColor.YELLOW + "|               |");
		        }
			}	          
		    else
		    {
		    	plr.sendMessage(pre + ChatColor.RED + "Error: You need to be looking at a blank sign with [newshop] on the top line.");
		    }
		}
		else if(stage == 1){
			// step 1 - check for valid item name (try to resolve if not valid)
			Material mat = ic.str2mat(string);
			if(mat != Material.AIR)
			{
				// Valid item name
				itemMap.put(plr.getName(), mat);
    			plr.sendMessage(pre + ChatColor.DARK_GREEN + "Your shop so far...");
    			plr.sendMessage(pre + ChatColor.DARK_GREEN + "Item: " + ChatColor.GREEN + itemMap.get(plr.getName()));
    			plr.sendMessage(pre + ChatColor.DARK_PURPLE + "------------------");
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
			// step 2 - item amount
			int n1 = Integer.parseInt(string);
			if(!(n1<=0))
			{
				amntMap.put(plr.getName(), n1);
    			plr.sendMessage(pre + ChatColor.DARK_GREEN + "Your shop so far...");
    			plr.sendMessage(pre + ChatColor.DARK_GREEN + "Item: " + ChatColor.GREEN + itemMap.get(plr.getName()));
    			plr.sendMessage(pre + ChatColor.DARK_GREEN + "Amount: " + ChatColor.GREEN + amntMap.get(plr.getName()));
    			plr.sendMessage(pre + ChatColor.DARK_PURPLE + "------------------");
    			plr.sendMessage(pre + ChatColor.LIGHT_PURPLE + "Step 3 - Enter the price you want to sell your");
    			plr.sendMessage(pre + ChatColor.LIGHT_PURPLE + "goods for. Eg: I want to sell 32 STONE for 32 Gold");
    			plr.sendMessage(pre + ChatColor.LIGHT_PURPLE + "nuggets. This is what the players will pay for your items.");
    			plr.sendMessage(pre + ChatColor.GREEN + "Just type it in the chat below. Type 'quit' to leave the wizard.");
    			wizMap.put(plr.getName(), 3);
			}
			else
			{
		    	plr.sendMessage(pre + ChatColor.RED + "Error: That wasn't a valid number. Negative numbers don't");
		    	plr.sendMessage(pre + ChatColor.RED + "count either. Make sure there are no spaces or characters.");
    			plr.sendMessage(pre + ChatColor.GREEN + "Just type it in the chat below. Type 'quit' to leave the wizard.");
			}
		}
		else if(stage == 3){
			// step 3 - price people will buy item for
			int n2 = Integer.parseInt(string);
			if(!(n2<=0))
			{
				sellMap.put(plr.getName(), n2);
    			plr.sendMessage(pre + ChatColor.DARK_GREEN + "Your shop so far...");
    			plr.sendMessage(pre + ChatColor.DARK_GREEN + "Item: " + ChatColor.GREEN + itemMap.get(plr.getName()));
    			plr.sendMessage(pre + ChatColor.DARK_GREEN + "Amount: " + ChatColor.GREEN + amntMap.get(plr.getName()));
    			plr.sendMessage(pre + ChatColor.DARK_GREEN + "Sell price: " + ChatColor.GREEN + sellMap.get(plr.getName()));
    			plr.sendMessage(pre + ChatColor.DARK_PURPLE + "------------------");
    			plr.sendMessage(pre + ChatColor.LIGHT_PURPLE + "Step 4 - Enter the price that you will buy from players.");
    			plr.sendMessage(pre + ChatColor.LIGHT_PURPLE + "Eg: You will buy 32 STONE from players for 16 Gold nuggets.");
    			plr.sendMessage(pre + ChatColor.LIGHT_PURPLE + "If you dont want to buy from players, type 0.");
    			plr.sendMessage(pre + ChatColor.GREEN + "Just type it in the chat below. Type 'quit' to leave the wizard.");
    			wizMap.put(plr.getName(), 4);
			}
			else
			{
		    	plr.sendMessage(pre + ChatColor.RED + "Error: That wasn't a valid number. Negative numbers don't");
		    	plr.sendMessage(pre + ChatColor.RED + "count either. Make sure there are no spaces or characters.");
    			plr.sendMessage(pre + ChatColor.GREEN + "Just type it in the chat below. Type 'quit' to leave the wizard.");
			}
		}
		else if(stage == 4){
			// step 4 - price you will buy the item from players
			int n3 = Integer.parseInt(string);
			// If the number == 0 then make sure to handle it correctly.
			if((n3>0))
			{
				// Number is GREATER THAN 0
				buyMap.put(plr.getName(), n3);
    			plr.sendMessage(pre + ChatColor.DARK_GREEN + "Your shop so far...");
    			plr.sendMessage(pre + ChatColor.DARK_GREEN + "Item: " + ChatColor.GREEN + itemMap.get(plr.getName()));
    			plr.sendMessage(pre + ChatColor.DARK_GREEN + "Amount: " + ChatColor.GREEN + amntMap.get(plr.getName()));
    			plr.sendMessage(pre + ChatColor.DARK_GREEN + "Sell price: " + ChatColor.GREEN + sellMap.get(plr.getName()));
    			plr.sendMessage(pre + ChatColor.DARK_GREEN + "Buy price: " + ChatColor.GREEN + buyMap.get(plr.getName()));
    			plr.sendMessage(pre + ChatColor.DARK_PURPLE + "------------------");
    			plr.sendMessage(pre + ChatColor.LIGHT_PURPLE + "Final Stage - Just to confirm everything now. If its wrong,");
    			plr.sendMessage(pre + ChatColor.LIGHT_PURPLE + "type 'quit' in the chat below to start again. Here we go...");
    			plr.sendMessage(pre + ChatColor.AQUA + "You are buying/selling " + ChatColor.GREEN + amntMap.get(plr.getName()) + ChatColor.AQUA + " of " + ChatColor.GREEN + itemMap.get(plr.getName()) + ChatColor.AQUA + ".");
    			plr.sendMessage(pre + ChatColor.AQUA + "People will pay " + ChatColor.GREEN + sellMap.get(plr.getName()) + ChatColor.AQUA + " gold for " + ChatColor.GREEN + amntMap.get(plr.getName()) + " " + itemMap.get(plr.getName()) + ChatColor.AQUA + ".");
    			plr.sendMessage(pre + ChatColor.AQUA + "You will pay " + ChatColor.GREEN + buyMap.get(plr.getName()) + ChatColor.AQUA + " gold for " + ChatColor.GREEN + amntMap.get(plr.getName()) + " " + itemMap.get(plr.getName()) + ChatColor.AQUA + ".");
    			plr.sendMessage(pre + ChatColor.LIGHT_PURPLE + "If thats all correct, just type 'ok' in the chat. If its not, type 'quit' to have another go.");
    			plr.sendMessage(pre + ChatColor.GREEN + "Just type it in the chat below. Type 'quit' to leave the wizard.");
    			wizMap.put(plr.getName(), 5);
			}
			else if((n3==0))
			{
				// Number is EQUAL TO 0
				buyMap.put(plr.getName(), n3);
    			plr.sendMessage(pre + ChatColor.DARK_GREEN + "Your shop so far...");
    			plr.sendMessage(pre + ChatColor.DARK_GREEN + "Item: " + ChatColor.GREEN + itemMap.get(plr.getName()));
    			plr.sendMessage(pre + ChatColor.DARK_GREEN + "Amount: " + ChatColor.GREEN + amntMap.get(plr.getName()));
    			plr.sendMessage(pre + ChatColor.DARK_GREEN + "Sell price: " + ChatColor.GREEN + sellMap.get(plr.getName()));
    			plr.sendMessage(pre + ChatColor.DARK_GREEN + "Buy price: " + ChatColor.GREEN + "[Not buying from players]");
    			plr.sendMessage(pre + ChatColor.DARK_PURPLE + "------------------");
    			plr.sendMessage(pre + ChatColor.LIGHT_PURPLE + "Final Stage - Just to confirm everything now. If its wrong,");
    			plr.sendMessage(pre + ChatColor.LIGHT_PURPLE + "type 'quit' in the chat below to start again. Here we go...");
    			plr.sendMessage(pre + ChatColor.AQUA + "You are buying/selling " + ChatColor.GREEN + amntMap.get(plr.getName()) + ChatColor.AQUA + " of " + ChatColor.GREEN + itemMap.get(plr.getName()) + ChatColor.AQUA + ".");
    			plr.sendMessage(pre + ChatColor.AQUA + "People will pay " + ChatColor.GREEN + sellMap.get(plr.getName()) + ChatColor.AQUA + " gold for " + ChatColor.GREEN + amntMap.get(plr.getName()) + " " + itemMap.get(plr.getName()) + ChatColor.AQUA + ".");
    			plr.sendMessage(pre + ChatColor.AQUA + "You arn't buying items from players.");
    			plr.sendMessage(pre + ChatColor.LIGHT_PURPLE + "If thats all correct, just type 'ok' in the chat. If its not, type 'quit' to have another go.");
    			plr.sendMessage(pre + ChatColor.GREEN + "Just type it in the chat below. Type 'quit' to leave the wizard.");
    			wizMap.put(plr.getName(), 5);
			}
			else
			{
		    	plr.sendMessage(pre + ChatColor.RED + "Error: That wasn't a valid number. Negative numbers don't");
		    	plr.sendMessage(pre + ChatColor.RED + "count either. Make sure there are no spaces or characters.");
    			plr.sendMessage(pre + ChatColor.GREEN + "Just type it in the chat below. Type 'quit' to leave the wizard.");
			}
		}
		else if(stage == 5){
			// step 5 - Confirm data.
			if(string.equalsIgnoreCase("ok"))
			{
				// set the sign up

				if ((block.getTypeId() == 63) || (block.getTypeId() == 68))
				{
					Sign s = (Sign)block.getState();
			        String line0 = s.getLine(0);
			        if(line0.equalsIgnoreCase("[newshop]"))
			        {
			        	// blank sign with [shop] on the top line. Check for chest underneath now
			        	if (block.getLocation().add(0.0D, -1.0D, 0.0D).getBlock().getType() == Material.CHEST)
			        	{
			        		//set sign
			        		s.setLine(0, "[shop]");
			        		s.setLine(1, plr.getName());
			        		s.setLine(2, String.valueOf(itemMap.get(plr.getName())));
			                s.setLine(3, "B " + sellMap.get(plr.getName()) + ":S " + buyMap.get(plr.getName()) + ":A " + amntMap.get(plr.getName()));
			                s.update();
			    			plr.sendMessage(pre + ChatColor.DARK_GREEN + "Completed!");
			    			plr.sendMessage(pre + ChatColor.DARK_PURPLE + "------------------");
			    			plr.sendMessage(pre + ChatColor.LIGHT_PURPLE + "Your shop is complete. Now you just need players");
			    			plr.sendMessage(pre + ChatColor.LIGHT_PURPLE + "to buy or sell to your shop.");
			    			plr.sendMessage(pre + ChatColor.GREEN + "Happy selling!");
			    			wizMap.remove(plr.getName());
			        	}
			        	else
			        	{
					    	plr.sendMessage(pre + ChatColor.RED + "Error: Your not looking at your sign! Type OK when you are.");
			    			plr.sendMessage(pre + ChatColor.GREEN + "Just type it in the chat below. Type 'quit' to leave the wizard.");
			        	}
			        }
			        else
			        {
				    	plr.sendMessage(pre + ChatColor.RED + "Error: Your sign needs to look like this...");
				    	plr.sendMessage(pre + ChatColor.YELLOW + "| [shop] |");
				    	plr.sendMessage(pre + ChatColor.YELLOW + "|       |");
				    	plr.sendMessage(pre + ChatColor.YELLOW + "|       |");
				    	plr.sendMessage(pre + ChatColor.YELLOW + "|       |");
		    			plr.sendMessage(pre + ChatColor.GREEN + "Just type it in the chat below. Type 'quit' to leave the wizard.");
			        }
				}	          
			    else
			    {
			    	plr.sendMessage(pre + ChatColor.RED + "Error: Your not looking at your sign! Type OK when you are.");
	    			plr.sendMessage(pre + ChatColor.GREEN + "Just type it in the chat below. Type 'quit' to leave the wizard.");
			    }
			}
			else
			{
		    	plr.sendMessage(pre + ChatColor.RED + "Error: That wasn't what I was looking for. I was looking for an");
		    	plr.sendMessage(pre + ChatColor.RED + "'ok' or a 'quit'. Have another go :)");
    			plr.sendMessage(pre + ChatColor.GREEN + "Just type it in the chat below. Type 'quit' to leave the wizard.");
			}
		}
	}
	
	public void closeShop(Player plr) {
		Block block = plr.getTargetBlock(null, 5);
        
		if ((block.getTypeId() == 63) || (block.getTypeId() == 68)) {
	          Sign s = (Sign)block.getState();
	          String line0 = s.getLine(0);
	          String line1 = s.getLine(1);

	          if(line0.equalsIgnoreCase("[shop]") && (line1.equalsIgnoreCase(plr.getName()) || Goldecon.checkPerm(plr, "goldecon.shop.admin")))
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