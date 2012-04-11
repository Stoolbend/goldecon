package org.goldecon.shops;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import org.goldecon.goldeconplus.Goldecon;

public class GoldeconShop {
	
	Goldecon plugin;
	// Set the text for system messages
	String ver = Goldecon.ver;
	String edition = Goldecon.edition;
	public static String edition2 = ChatColor.DARK_PURPLE + "[geShops] ";
	static ItemConvert ic = new ItemConvert();
	
	// Stage map banks
	public static Map<String, Integer> wizMap = new HashMap<String, Integer>();
	public static Map<String, Integer> signWiz = new HashMap<String, Integer>();
	
	// Maps for storing temp wizard information.
	static Map<String, Material> itemMap1 = new HashMap<String, Material>();
	static Map<String, Integer> amntMap1 = new HashMap<String, Integer>();
	static Map<String, Integer> buyMap1 = new HashMap<String, Integer>();
	static Map<String, Integer> sellMap1 = new HashMap<String, Integer>();
	
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
		Block block = plr.getTargetBlock(null, 5);
		if(stage == 0){
			// step 0 - check for sign & chest placement
			plr.sendMessage(edition2 + ChatColor.GOLD + "New Shop Wizard");
			plr.sendMessage(edition2 + ChatColor.GOLD + "--------------");
			
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
		    			plr.sendMessage(edition2 + ChatColor.LIGHT_PURPLE + "Step 1 - Enter the name of the item you want");
		    			plr.sendMessage(edition2 + ChatColor.LIGHT_PURPLE + "to sell. Try to replace any spaces with a _");
		    			plr.sendMessage(edition2 + ChatColor.LIGHT_PURPLE + "so I can find out what item you are on about.");
		    			plr.sendMessage(edition2 + ChatColor.GREEN + "Just type it in the chat below. Type 'quit' to leave the wizard.");
		    			wizMap.put(plr.getName(), 1);
		        	}
		        	else
		        	{
				    	plr.sendMessage(edition2 + ChatColor.RED + "Error: You need to place a chest underneath the sign.");
		        	}
		        }
		        else
		        {
			    	plr.sendMessage(edition2 + ChatColor.RED + "Error: Your sign needs to look like this...");
			    	plr.sendMessage(edition2 + ChatColor.YELLOW + "| [newshop] |");
			    	plr.sendMessage(edition2 + ChatColor.YELLOW + "|               |");
			    	plr.sendMessage(edition2 + ChatColor.YELLOW + "|               |");
			    	plr.sendMessage(edition2 + ChatColor.YELLOW + "|               |");
		        }
			}	          
		    else
		    {
		    	plr.sendMessage(edition2 + ChatColor.RED + "Error: You need to be looking at a blank sign with [newshop] on the top line.");
		    }
		}
		else if(stage == 1){
			// step 1 - check for valid item name (try to resolve if not valid)
			Material mat = ic.str2mat(string);
			if(mat != Material.AIR)
			{
				// Valid item name
				itemMap1.put(plr.getName(), mat);
    			plr.sendMessage(edition2 + ChatColor.DARK_GREEN + "Your shop so far...");
    			plr.sendMessage(edition2 + ChatColor.DARK_GREEN + "Item: " + ChatColor.GREEN + itemMap1.get(plr.getName()));
    			plr.sendMessage(edition2 + ChatColor.DARK_PURPLE + "------------------");
    			plr.sendMessage(edition2 + ChatColor.LIGHT_PURPLE + "Step 2 - Enter the amount of the item you want");
    			plr.sendMessage(edition2 + ChatColor.LIGHT_PURPLE + "to buy and sell. Eg: You want to buy & sell 32");
    			plr.sendMessage(edition2 + ChatColor.LIGHT_PURPLE + "blocks of STONE at a time. Just type, 32.");
    			plr.sendMessage(edition2 + ChatColor.GREEN + "Just type it in the chat below. Type 'quit' to leave the wizard.");
    			wizMap.put(plr.getName(), 2);
			}
			else
			{
		    	plr.sendMessage(edition2 + ChatColor.RED + "Error: I couldn't find a block / item by that name!");
		    	plr.sendMessage(edition2 + ChatColor.RED + "Try again if you want. Eg: for Lapis block, do LAPIS_BLOCK");
		    	plr.sendMessage(edition2 + ChatColor.RED + "There is a full list of stuff you can sell / buy at http://bit.ly/ge-items");
    			plr.sendMessage(edition2 + ChatColor.GREEN + "Just type it in the chat below. Type 'quit' to leave the wizard.");
			}
		}
		else if(stage == 2){
			// step 2 - item amount
			int n1 = Integer.parseInt(string);
			if(!(n1<=0))
			{
				amntMap1.put(plr.getName(), n1);
    			plr.sendMessage(edition2 + ChatColor.DARK_GREEN + "Your shop so far...");
    			plr.sendMessage(edition2 + ChatColor.DARK_GREEN + "Item: " + ChatColor.GREEN + itemMap1.get(plr.getName()));
    			plr.sendMessage(edition2 + ChatColor.DARK_GREEN + "Amount: " + ChatColor.GREEN + amntMap1.get(plr.getName()));
    			plr.sendMessage(edition2 + ChatColor.DARK_PURPLE + "------------------");
    			plr.sendMessage(edition2 + ChatColor.LIGHT_PURPLE + "Step 3 - Enter the price you want to sell your");
    			plr.sendMessage(edition2 + ChatColor.LIGHT_PURPLE + "goods for. Eg: I want to sell 32 STONE for 32 Gold");
    			plr.sendMessage(edition2 + ChatColor.LIGHT_PURPLE + "nuggets. This is what the players will pay for your items.");
    			plr.sendMessage(edition2 + ChatColor.GREEN + "Just type it in the chat below. Type 'quit' to leave the wizard.");
    			wizMap.put(plr.getName(), 3);
			}
			else
			{
		    	plr.sendMessage(edition2 + ChatColor.RED + "Error: That wasn't a valid number. Negative numbers don't");
		    	plr.sendMessage(edition2 + ChatColor.RED + "count either. Make sure there are no spaces or characters.");
    			plr.sendMessage(edition2 + ChatColor.GREEN + "Just type it in the chat below. Type 'quit' to leave the wizard.");
			}
		}
		else if(stage == 3){
			// step 3 - price people will buy item for
			int n2 = Integer.parseInt(string);
			if(!(n2<=0))
			{
				sellMap1.put(plr.getName(), n2);
    			plr.sendMessage(edition2 + ChatColor.DARK_GREEN + "Your shop so far...");
    			plr.sendMessage(edition2 + ChatColor.DARK_GREEN + "Item: " + ChatColor.GREEN + itemMap1.get(plr.getName()));
    			plr.sendMessage(edition2 + ChatColor.DARK_GREEN + "Amount: " + ChatColor.GREEN + amntMap1.get(plr.getName()));
    			plr.sendMessage(edition2 + ChatColor.DARK_GREEN + "Sell price: " + ChatColor.GREEN + sellMap1.get(plr.getName()));
    			plr.sendMessage(edition2 + ChatColor.DARK_PURPLE + "------------------");
    			plr.sendMessage(edition2 + ChatColor.LIGHT_PURPLE + "Step 4 - Enter the price that you will buy from players.");
    			plr.sendMessage(edition2 + ChatColor.LIGHT_PURPLE + "Eg: You will buy 32 STONE from players for 16 Gold nuggets.");
    			plr.sendMessage(edition2 + ChatColor.LIGHT_PURPLE + "If you dont want to buy from players, type 0.");
    			plr.sendMessage(edition2 + ChatColor.GREEN + "Just type it in the chat below. Type 'quit' to leave the wizard.");
    			wizMap.put(plr.getName(), 4);
			}
			else
			{
		    	plr.sendMessage(edition2 + ChatColor.RED + "Error: That wasn't a valid number. Negative numbers don't");
		    	plr.sendMessage(edition2 + ChatColor.RED + "count either. Make sure there are no spaces or characters.");
    			plr.sendMessage(edition2 + ChatColor.GREEN + "Just type it in the chat below. Type 'quit' to leave the wizard.");
			}
		}
		else if(stage == 4){
			// step 4 - price you will buy the item from players
			int n3 = Integer.parseInt(string);
			// If the number == 0 then make sure to handle it correctly.
			if((n3>0))
			{
				// Number is GREATER THAN 0
				buyMap1.put(plr.getName(), n3);
    			plr.sendMessage(edition2 + ChatColor.DARK_GREEN + "Your shop so far...");
    			plr.sendMessage(edition2 + ChatColor.DARK_GREEN + "Item: " + ChatColor.GREEN + itemMap1.get(plr.getName()));
    			plr.sendMessage(edition2 + ChatColor.DARK_GREEN + "Amount: " + ChatColor.GREEN + amntMap1.get(plr.getName()));
    			plr.sendMessage(edition2 + ChatColor.DARK_GREEN + "Sell price: " + ChatColor.GREEN + sellMap1.get(plr.getName()));
    			plr.sendMessage(edition2 + ChatColor.DARK_GREEN + "Buy price: " + ChatColor.GREEN + buyMap1.get(plr.getName()));
    			plr.sendMessage(edition2 + ChatColor.DARK_PURPLE + "------------------");
    			plr.sendMessage(edition2 + ChatColor.LIGHT_PURPLE + "Final Stage - Just to confirm everything now. If its wrong,");
    			plr.sendMessage(edition2 + ChatColor.LIGHT_PURPLE + "type 'quit' in the chat below to start again. Here we go...");
    			plr.sendMessage(edition2 + ChatColor.AQUA + "You are buying/selling " + ChatColor.GREEN + amntMap1.get(plr.getName()) + ChatColor.AQUA + " of " + ChatColor.GREEN + itemMap1.get(plr.getName()) + ChatColor.AQUA + ".");
    			plr.sendMessage(edition2 + ChatColor.AQUA + "People will pay " + ChatColor.GREEN + sellMap1.get(plr.getName()) + ChatColor.AQUA + " gold for " + ChatColor.GREEN + amntMap1.get(plr.getName()) + " " + itemMap1.get(plr.getName()) + ChatColor.AQUA + ".");
    			plr.sendMessage(edition2 + ChatColor.AQUA + "You will pay " + ChatColor.GREEN + buyMap1.get(plr.getName()) + ChatColor.AQUA + " gold for " + ChatColor.GREEN + amntMap1.get(plr.getName()) + " " + itemMap1.get(plr.getName()) + ChatColor.AQUA + ".");
    			plr.sendMessage(edition2 + ChatColor.LIGHT_PURPLE + "If thats all correct, just type 'ok' in the chat. If its not, type 'quit' to have another go.");
    			plr.sendMessage(edition2 + ChatColor.GREEN + "Just type it in the chat below. Type 'quit' to leave the wizard.");
    			wizMap.put(plr.getName(), 5);
			}
			else if((n3==0))
			{
				// Number is EQUAL TO 0
				buyMap1.put(plr.getName(), n3);
    			plr.sendMessage(edition2 + ChatColor.DARK_GREEN + "Your shop so far...");
    			plr.sendMessage(edition2 + ChatColor.DARK_GREEN + "Item: " + ChatColor.GREEN + itemMap1.get(plr.getName()));
    			plr.sendMessage(edition2 + ChatColor.DARK_GREEN + "Amount: " + ChatColor.GREEN + amntMap1.get(plr.getName()));
    			plr.sendMessage(edition2 + ChatColor.DARK_GREEN + "Sell price: " + ChatColor.GREEN + sellMap1.get(plr.getName()));
    			plr.sendMessage(edition2 + ChatColor.DARK_GREEN + "Buy price: " + ChatColor.GREEN + "[Not buying from players]");
    			plr.sendMessage(edition2 + ChatColor.DARK_PURPLE + "------------------");
    			plr.sendMessage(edition2 + ChatColor.LIGHT_PURPLE + "Final Stage - Just to confirm everything now. If its wrong,");
    			plr.sendMessage(edition2 + ChatColor.LIGHT_PURPLE + "type 'quit' in the chat below to start again. Here we go...");
    			plr.sendMessage(edition2 + ChatColor.AQUA + "You are buying/selling " + ChatColor.GREEN + amntMap1.get(plr.getName()) + ChatColor.AQUA + " of " + ChatColor.GREEN + itemMap1.get(plr.getName()) + ChatColor.AQUA + ".");
    			plr.sendMessage(edition2 + ChatColor.AQUA + "People will pay " + ChatColor.GREEN + sellMap1.get(plr.getName()) + ChatColor.AQUA + " gold for " + ChatColor.GREEN + amntMap1.get(plr.getName()) + " " + itemMap1.get(plr.getName()) + ChatColor.AQUA + ".");
    			plr.sendMessage(edition2 + ChatColor.AQUA + "You arn't buying items from players.");
    			plr.sendMessage(edition2 + ChatColor.LIGHT_PURPLE + "If thats all correct, just type 'ok' in the chat. If its not, type 'quit' to have another go.");
    			plr.sendMessage(edition2 + ChatColor.GREEN + "Just type it in the chat below. Type 'quit' to leave the wizard.");
    			wizMap.put(plr.getName(), 5);
			}
			else
			{
		    	plr.sendMessage(edition2 + ChatColor.RED + "Error: That wasn't a valid number. Negative numbers don't");
		    	plr.sendMessage(edition2 + ChatColor.RED + "count either. Make sure there are no spaces or characters.");
    			plr.sendMessage(edition2 + ChatColor.GREEN + "Just type it in the chat below. Type 'quit' to leave the wizard.");
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
			        		s.setLine(2, String.valueOf(itemMap1.get(plr.getName())));
			                s.setLine(3, "B " + sellMap1.get(plr.getName()) + ":S " + buyMap1.get(plr.getName()) + ":A " + amntMap1.get(plr.getName()));
			                s.update();
			    			plr.sendMessage(edition2 + ChatColor.DARK_GREEN + "Completed!");
			    			plr.sendMessage(edition2 + ChatColor.DARK_PURPLE + "------------------");
			    			plr.sendMessage(edition2 + ChatColor.LIGHT_PURPLE + "Your shop is complete. Now you just need players");
			    			plr.sendMessage(edition2 + ChatColor.LIGHT_PURPLE + "to buy or sell to your shop.");
			    			plr.sendMessage(edition2 + ChatColor.GREEN + "Happy selling!");
			    			wizMap.remove(plr.getName());
			        	}
			        	else
			        	{
					    	plr.sendMessage(edition2 + ChatColor.RED + "Error: Your not looking at your sign! Type OK when you are.");
			    			plr.sendMessage(edition2 + ChatColor.GREEN + "Just type it in the chat below. Type 'quit' to leave the wizard.");
			        	}
			        }
			        else
			        {
				    	plr.sendMessage(edition2 + ChatColor.RED + "Error: Your sign needs to look like this...");
				    	plr.sendMessage(edition2 + ChatColor.YELLOW + "| [newshop] |");
				    	plr.sendMessage(edition2 + ChatColor.YELLOW + "|          |");
				    	plr.sendMessage(edition2 + ChatColor.YELLOW + "|          |");
				    	plr.sendMessage(edition2 + ChatColor.YELLOW + "|          |");
		    			plr.sendMessage(edition2 + ChatColor.GREEN + "Just 'ok' when your looking at your sign again. Type 'quit' to leave the wizard.");
			        }
				}	          
			    else
			    {
			    	plr.sendMessage(edition2 + ChatColor.RED + "Error: Your not looking at your sign! Type OK when you are.");
	    			plr.sendMessage(edition2 + ChatColor.GREEN + "Just type it in the chat below. Type 'quit' to leave the wizard.");
			    }
			}
			else
			{
		    	plr.sendMessage(edition2 + ChatColor.RED + "Error: That wasn't what I was looking for. I was looking for an");
		    	plr.sendMessage(edition2 + ChatColor.RED + "'ok' or a 'quit'. Have another go :)");
    			plr.sendMessage(edition2 + ChatColor.GREEN + "Just type it in the chat below. Type 'quit' to leave the wizard.");
			}
		}
	}

	public static void signWizard(Player plr, int stage, String string) {
		// wizard for buying/selling from a sign shop.
		Block block = plr.getTargetBlock(null, 5);
		if(stage == 0)
		{
			// Check for a sign with [shop] on the top.
			if ((block.getTypeId() == 63) || (block.getTypeId() == 68))
			{
				Sign s = (Sign)block.getState();
		        String line0 = s.getLine(0);
		        if(line0.equalsIgnoreCase("[shop]"))
		        {
		        	// its a sign - check for chest
		        	if (block.getLocation().add(0.0D, -1.0D, 0.0D).getBlock().getType() == Material.CHEST)
		        	{
		        		
		        		// Set strings and whatnot.
				        String line1 = s.getLine(1);
				        String line2 = s.getLine(2);
				        String line3 = s.getLine(3);
				        String input = line3;
				        String[] output = input.split(":");
				        String bprice = output[0];
				        String sprice = output[1];
				        String amnt = output[2];
				        int itemamount = Integer.parseInt(amnt.replace("A ", " ").trim());
				        int buyprice = Integer.parseInt(sprice.replace("S ", " ").trim());
				        int sellprice = Integer.parseInt(bprice.replace("B ", "").trim());
				        
				        // Perform checks to determine output string.
				        // 1. Check if theres enough space in chest inv or pl;ayer inv for item amounds.
				        // int statuscode = 0;
				        // boolean check1 = plrCanFitInInv(plr, item, itemamount);
				        // boolean check2 = chestCanFitInInv(chest, item, itemamount);
				        // statuscode = createStatusCode1(check1, check2, plr, chest);
				        
		        		// Output informations
				        if(buyprice == 0)
				        {
				        	plr.sendMessage(edition2 + ChatColor.GOLD + "Welcome to " + ChatColor.YELLOW + line1 + "'s " + ChatColor.GOLD + " shop");
			    			plr.sendMessage(edition2 + ChatColor.GOLD + "-----------------------");
			    			plr.sendMessage(edition2 + ChatColor.AQUA + "You can buy " + ChatColor.AQUA + itemamount + " " + line2 + ChatColor.AQUA + " for " + ChatColor.GREEN + sellprice + ChatColor.AQUA + " Gold nuggets.");
			    			plr.sendMessage(edition2 + ChatColor.AQUA + "You can't sell to this shop because the owner isnt buying items!");
			    			plr.sendMessage(edition2 + ChatColor.LIGHT_PURPLE + "Type 'buy' to do just that. Or 'quit' to leave.");
			    			plr.sendMessage(edition2 + ChatColor.GREEN + "Just type it in the chat below. Type 'quit' to leave the shop.");
			    			signWiz.put(plr.getName(), 1);
				        }
				        else if(buyprice > 0)
				        {
			    			plr.sendMessage(edition2 + ChatColor.GOLD + "Welcome to " + ChatColor.YELLOW + line1 + "'s " + ChatColor.GOLD + " shop");
			    			plr.sendMessage(edition2 + ChatColor.GOLD + "-----------------------");
			    			plr.sendMessage(edition2 + ChatColor.AQUA + "You can buy " + ChatColor.GREEN + itemamount + " " + line2 + ChatColor.AQUA + " for " + ChatColor.GREEN + sellprice + ChatColor.AQUA + " Gold nuggets.");
			    			plr.sendMessage(edition2 + ChatColor.AQUA + "You can sell " + ChatColor.GREEN + itemamount + " " + line2 + ChatColor.AQUA + " for " + ChatColor.GREEN + buyprice + ChatColor.AQUA + " Gold nuggets.");
			    			plr.sendMessage(edition2 + ChatColor.LIGHT_PURPLE + "Type 'buy' or 'sell' to do just that. Or 'quit' to leave.");
			    			plr.sendMessage(edition2 + ChatColor.GREEN + "Just type it in the chat below. Type 'quit' to leave the shop.");
			    			signWiz.put(plr.getName(), 1);
				        }
		        	}
		        	else
		        	{
				    	plr.sendMessage(edition2 + ChatColor.RED + "Error: This isnt a valid shop. The chest has gone?");
		    			signWiz.remove(plr.getName());
		        	}
		        }
		        else
		        {
		        	// If not a shop sign.
			    	plr.sendMessage(edition2 + ChatColor.RED + "Error: You need to be looking at a sign with [shop] on the top.");
	    			signWiz.remove(plr.getName());
		        }
			}	          
		    else
		    {
		    	// Should never happen as they start this function from looking at a sign.
		    	plr.sendMessage(edition2 + ChatColor.RED + "Error: You need to be looking at a sign with [shop] on the top.");
    			signWiz.remove(plr.getName());
		    }
		}
		else if(stage == 1)
		{
			// Check for a sign with [shop] on the top.
			if ((block.getTypeId() == 63) || (block.getTypeId() == 68))
			{
				Sign s = (Sign)block.getState();
		        String line0 = s.getLine(0);
		        if(line0.equalsIgnoreCase("[shop]"))
		        {
		        	// its a sign - check for chest
		        	if (block.getLocation().add(0.0D, -1.0D, 0.0D).getBlock().getType() == Material.CHEST)
		        	{
		        		// chest under a shop sign.
		        		Block block2 = block.getLocation().add(0.0D, -1.0D, 0.0D).getBlock();
		        		Chest chest = (Chest)block2.getState();
		        		
		        		// Set strings and whatnot.
				        String line1 = s.getLine(1);
				        String line2 = s.getLine(2);
				        String line3 = s.getLine(3);
				        String input = line3;
				        String[] output = input.split(":");
				        String bprice = output[0];
				        String sprice = output[1];
				        String amnt = output[2];
				        Material item = Material.getMaterial(line2);
				        int itemamount = Integer.parseInt(amnt.replace("A ", " ").trim());
				        int buyprice = Integer.parseInt(sprice.replace("S ", " ").trim());
				        int sellprice = Integer.parseInt(bprice.replace("B ", "").trim());
				        
		        		// Output informations
				        if(string.equalsIgnoreCase("buy"))
				        {
				        	if (!line1.equalsIgnoreCase(plr.getName()))
				        	{
				        		if (plr.getInventory().contains(Material.GOLD_NUGGET, buyprice))
				        		{
				        			if(item == null)
				        			{
				        				plr.sendMessage(edition2 + ChatColor.RED + "This shop isnt working at the moment. Try re-making the shop using /geshop");
				        				return;
				        			}
				        			if (chest.getInventory().contains(item, itemamount))
				        			{
				        				// Check if player can fit the item in their inv.
				        				if (plrCanFitInInv(plr, item, itemamount))
				        				{
				        					plr.getInventory().addItem(new ItemStack[] { new ItemStack(item, itemamount) });
				        					plr.getInventory().removeItem(new ItemStack[] { new ItemStack(Material.GOLD_NUGGET, buyprice) });
				        					chest.getInventory().addItem(new ItemStack[] { new ItemStack(Material.GOLD_NUGGET, buyprice) });
				        					chest.getInventory().removeItem(new ItemStack[] { new ItemStack(item, itemamount) });
				        					plr.sendMessage(edition2 + ChatColor.GREEN + "You got " + itemamount + " " + line2.toLowerCase().replace("_", " ") + " for " + buyprice + " gold.");
							    			plr.sendMessage(edition2 + ChatColor.GOLD + "Thanks for shopping at " + ChatColor.YELLOW + line1 + "'s " + ChatColor.GOLD + " shop");
							    			signWiz.remove(plr.getName());
				        				}
					        			else
					        			{
					        				plr.sendMessage(edition2 + ChatColor.RED + "Error: You dont have enough space in your inventory.");
							    			signWiz.remove(plr.getName());
					        			}
				        			}
				        			else
				        			{
				        				plr.sendMessage(edition2 + ChatColor.RED + "Error: Not enough in stock.");
						    			signWiz.remove(plr.getName());
				        			}
				        		}
				        		else
				        		{
				        			plr.sendMessage(edition2 + ChatColor.RED + "Error: You do not have enough money for that.");
					    			signWiz.remove(plr.getName());
				        		}
				        	}
				        	else
				        	{
				        		plr.sendMessage(edition2 + ChatColor.RED + "Error: You can't use your own shop!");
				    			signWiz.remove(plr.getName());
				        	}
				        }
				        else if(string.equalsIgnoreCase("sell"))
				        {
				        	if (!line1.equalsIgnoreCase(plr.getName()))
				        	{
				        		if (chest.getInventory().contains(Material.GOLD_NUGGET, buyprice))
				        		{
				        			if(item == null)
				        			{
				        				plr.sendMessage(edition2 + ChatColor.RED + "This shop isnt working at the moment. Try re-making the shop using /geshop");
				        				return;
				        			}
				        			if (plr.getInventory().contains(item, itemamount))
				        			{
				        				// Check if player can fit the item in their inv.
				        				if (chestCanFitInInv(chest, item, itemamount))
				        				{
				        					plr.getInventory().removeItem(new ItemStack[] { new ItemStack(item, itemamount) });
				        		            plr.getInventory().addItem(new ItemStack[] { new ItemStack(Material.GOLD_NUGGET, sellprice) });           }
				        		            chest.getInventory().removeItem(new ItemStack[] { new ItemStack(Material.GOLD_NUGGET, sellprice) });
				        		            chest.getInventory().addItem(new ItemStack[] { new ItemStack(item, itemamount) });
				        		            plr.sendMessage(edition2 + ChatColor.GREEN + "You sold " + itemamount + " " + line2.toLowerCase().replace("_", " ") + " for " + sellprice + " gold.");
				        		            plr.sendMessage(edition2 + ChatColor.GOLD + "Thanks for shopping at " + ChatColor.YELLOW + line1 + "'s " + ChatColor.GOLD + " shop");
				        					signWiz.remove(plr.getName());
				        				}
				        				else
				        				{
				        					plr.sendMessage(edition2 + ChatColor.RED + "Error: The chest is currently full!");
				        					signWiz.remove(plr.getName());
				        				}
				        			}
				        			else
				        			{
				        				plr.sendMessage(edition2 + ChatColor.RED + "Error: You dont have enough to sell.");
				        				signWiz.remove(plr.getName());
				        			}
				        		}
				        		else
				        		{
				        			plr.sendMessage(edition2 + ChatColor.RED + "Error: The chest doesnt have enough gold to buy from you.");
				        			signWiz.remove(plr.getName());
				        		}
				        	}
				        	else
				        	{
				        		plr.sendMessage(edition2 + ChatColor.RED + "Error: You can't use your own shop!");
				        		signWiz.remove(plr.getName());
				        	}
				        }
				        else
				        {
					    	plr.sendMessage(edition2 + ChatColor.RED + "Error: That wasn't what I was looking for. I was looking for");
					    	plr.sendMessage(edition2 + ChatColor.RED + "'buy' or 'sell'. Have another go :)");
			    			plr.sendMessage(edition2 + ChatColor.GREEN + "Just type it in the chat below. Type 'quit' to leave the wizard.");
				        }
		        	}
		        	else
		        	{
				    	plr.sendMessage(edition2 + ChatColor.RED + "Error: This isnt a valid shop. Chest is missing.");
		        	}
		        }
		        else
		        {
		        	// If not a shop sign.
			    	plr.sendMessage(edition2 + ChatColor.RED + "Error: You need to be looking at a sign with [shop] on the top.*");
		        }
			}	          
		    else
		    {
		    	// Should never happen as they start this function from looking at a sign.
		    	plr.sendMessage(edition2 + ChatColor.RED + "Error: You need to be looking at a sign with [shop] on the top.");
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
	
	private boolean plrCanAfford(Player plr, int amount)
	{
		return plr.getInventory().contains(Material.GOLD_NUGGET, amount);
	}
	
	private boolean chestCanAfford(Chest chst, int amount)
	{
		return chst.getInventory().contains(Material.GOLD_NUGGET, amount);
	}
	
	private static boolean plrCanFitInInv(Player plr, Material item, int amount)
	{
		return true;
	}
	
	private static boolean chestCanFitInInv(Chest chest, Material item, int amount)
	{
		return true;
	}
	
	private static int createStatusCode1(boolean plrInvCheck, boolean chestInvCheck, Player plr, Chest chest)
	{
		if(plrInvCheck == true)
		{
			
		}
		return 0;
	}
}