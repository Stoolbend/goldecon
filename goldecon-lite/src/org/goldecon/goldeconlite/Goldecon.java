package org.goldecon.goldeconlite;

import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.goldecon.goldeconlite.CommandMethods;

import ru.tehkode.permissions.PermissionManager;
import ru.tehkode.permissions.bukkit.PermissionsEx;

import com.nijiko.permissions.PermissionHandler;
import com.nijikokun.bukkit.Permissions.Permissions;

public class Goldecon extends JavaPlugin{

	static Logger log;
	static PluginDescriptionFile info;
	// Declare perm system variables
	public static PermissionHandler perms3;
	public static PermissionManager permsEx;
	public static int permSystem;
	// Set the CommandMethods class
	CommandMethods cmdMth;
	// Display text thats editable
	public static String ver = "1.5a";
	public static String edition = ChatColor.GOLD + "[ge] ";
	
	public void onEnable()
	  {
	    Goldecon.log = Logger.getLogger("Minecraft");
	    Goldecon.info = getDescription();
	    
	    saveConfig();
	    setupPermissions();
	    
	    Goldecon.log.info(Goldecon.info.getName() + " has been enabled");
	  }
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	  {
		 if ((sender instanceof Player)) {
		      Player player = (Player)sender;
		      if (cmd.getName().equalsIgnoreCase("ge"))
		      {
		    	if(!checkPerm(player, "goldecon.core.use")){
		    		player.sendMessage(edition + ChatColor.RED + "You dont have permission to do that, dave.");
		    		return true;
		    	}
		     if(args.length == 0){
		    	 cmdMth.coreHelp(player);
		      }
		      else
		      {
		          int amount;
		          if (args[0].equalsIgnoreCase("pay")) {
		            if (args.length == 3) {
		              for (Player players : getServer().getOnlinePlayers()) {
		                if (players.getDisplayName().toLowerCase().contains(args[1].toLowerCase())) {
		                  if (player.getInventory().contains(Material.GOLD_NUGGET)) {
		                    amount = Integer.parseInt(args[2]);
		                    if (player.getInventory().contains(Material.GOLD_NUGGET, amount)) {
		                      players.getInventory().addItem(new ItemStack[] { new ItemStack(Material.GOLD_NUGGET, amount) });
		                      player.getInventory().removeItem(new ItemStack[] { new ItemStack(Material.GOLD_NUGGET, amount) });
		                      player.sendMessage(ChatColor.DARK_PURPLE + "You paid " + args[1] + " " + amount + " gold.");
		                      return true;
		                    } else {
		                      player.sendMessage(edition + ChatColor.RED + "You don't have that much money with you.");
		                      return true;
		                    }
		                  } else {
		                    player.sendMessage(edition + ChatColor.RED + "You have no money with you.");
		                    return true;
		                  }
		                }
		                else player.sendMessage(edition + ChatColor.RED + "Can not find " + args[1] + ".");
		                return true;
		              }
		            }
		            else {
		              player.sendMessage(edition + ChatColor.RED + "/ge pay <player> <amount>");
		              return true;
		            }

		          }
		      /* TODO Disabled the /ge stats because the bank module isnt included by default? Maybe make logic to enable this in next version
		          else if (args[0].equalsIgnoreCase("stats")) {
		            if (args.length == 1) {
		              if (getTopPlayer() != "")
		                player.sendMessage(ChatColor.GOLD + "[ge+] " + ChatColor.GREEN + "The top player is " + getTopPlayer() + " with " + Goldecon.banks.get(getTopPlayer()) + " gold.");
		              else {
		                player.sendMessage(ChatColor.GOLD + "[ge+] " + ChatColor.RED + "There is no top player.");
		                return true;
		              }
		              if (getSecondPlayer() != "") {
		                player.sendMessage(ChatColor.GOLD + "[ge+] " + ChatColor.GREEN + "The number 2 player is " + getSecondPlayer() + " with " + Goldecon.banks.get(getSecondPlayer()) + " gold.");
		              }
		              if (getThirdPlayer() != "") {
		                player.sendMessage(ChatColor.GOLD + "[ge+] " + ChatColor.GREEN + "The number 3 player is " + getThirdPlayer() + " with " + Goldecon.banks.get(getThirdPlayer()) + " gold.");
		              }

		            }
		            else if (args.length == 2) {
		              if (Goldecon.banks.contains(args[1])) {
		                int amount1 = ((Integer)Goldecon.banks.get(args[1])).intValue();
		                player.sendMessage(ChatColor.GOLD + "[ge+] " + ChatColor.GOLD + args[1] + " has " + amount1 + " gold in the bank.");
		                return true;
		              } else {
		                player.sendMessage(ChatColor.GOLD + "[ge+] " + ChatColor.RED + "Can not find " + args[1] + ".");
		                return true;
		              }
		            } else {
		              player.sendMessage(ChatColor.GOLD + "[ge+] " + ChatColor.RED + "/ge stats [player name]");
		              return true;
		            }
		          }
		          */
		          else if (args.length == 1) {
		            if (args[0].equalsIgnoreCase("money")) {
		              int total = 0;
		              for (ItemStack stack : ((Player)sender).getInventory().getContents()) {
		                if ((stack == null) || 
		                  (stack.getType() != Material.GOLD_NUGGET)) continue;
		                total += stack.getAmount();
		              }

		              sender.sendMessage(edition + ChatColor.GOLD + "You have " + total + " gold with you.");
		              return true;
		            }
		            // Fixes: gh-1 /ge help not displaying.
		            else if(args[0].equalsIgnoreCase("help")){
		            	cmdMth.coreHelp(player);
		            }
		          }
		          else
		          {
		            player.sendMessage(edition + ChatColor.RED + "/ge < help | pay <player name> <amount> | stats [player name] >");
		            return true;
		          }
		        }
		      }
		
		else
		{
		    sender.sendMessage(edition + "This is for player use only.");
			return true;
		}
		return true;
	  }
		return true;
	  }
	  private int setupPermissions() {
		    
		    // Check for PermissionsEX - If found, use it.
		    Plugin permsEXpl = this.getServer().getPluginManager().getPlugin("PermissionsEx");
		    
		    if (!(permsEXpl == null)) {
			    permsEx = PermissionsEx.getPermissionManager();
			    Goldecon.log.info(Goldecon.info.getName() + " Found and will use plugin "+ permsEXpl.getDescription().getFullName());
		        return 1;
		    }
		    
		    // Check for Permissions 3.x - If found, use it.
		    Plugin perms3pl = this.getServer().getPluginManager().getPlugin("Permissions");
		    
		    if (!(perms3pl == null)) {
		    	perms3 = ((Permissions) perms3pl).getHandler();
			    Goldecon.log.info(Goldecon.info.getName() + " Found and will use plugin "+((Permissions)perms3pl).getDescription().getFullName());
		        return 2;
		    }
		    
		    else{
		    // Nothing there. Go to Opmode
	        Goldecon.log.info(Goldecon.info.getName() + " 3rd Party permissions system not detected, defaulting to OP / SuperPerm mode.");
	        return 0;
		    }
		}
public static boolean checkPerm(Player plyr, String node){
	if(permSystem == 1){
	  	if(permsEx.has(plyr, node) || plyr.isOp()){
	  		return true;
	  	}
	}
	else if(permSystem == 2){
	  	if(perms3.has(plyr, node) || plyr.isOp()){
	  		return true;
	  	}
	}
	else if(permSystem == 0){
	  	if(plyr.hasPermission(node) || plyr.isOp()){
	  		return true;
	  	}
	}
	else{
      log.info(info.getName() + " The permission setup went wrong, Seek help! :(");
      log.info(info.getName() + " Req Node: " + node);
      log.info(info.getName() + " Perm System: " + permSystem);
		return false;
	}
	return false;
}
}
