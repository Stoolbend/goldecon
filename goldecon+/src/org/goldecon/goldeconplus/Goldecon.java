package org.goldecon.goldeconplus;

import java.util.logging.Logger;
import net.kierrow.io.Save;
import net.kierrow.io.SaveManager;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.goldecon.regionmarket.GoldeconRegionListener;
import org.goldecon.regionmarket.GoldeconRegionMarket;
import org.goldecon.shops.GoldeconShop;
import org.goldecon.shops.GoldeconShopListener;

import ru.tehkode.permissions.PermissionManager;
import ru.tehkode.permissions.bukkit.PermissionsEx;

import com.nijiko.permissions.PermissionHandler;
import com.nijikokun.bukkit.Permissions.Permissions;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;

public final class Goldecon extends JavaPlugin
{
  public static Logger log;
  public static PluginDescriptionFile info;
  static Save<Integer> banks;
  public static int creeper = 32;
  public static int zombie = 32;
  public static int skeleton = 32;
  public static int ghast = 32;
  public static int enderman = 32;
  public static int cow = 32;
  public static int pig = 32;
  public static int chicken = 32;
  public static int sheep = 32;
  public static int mushroomcow = 32;
  public static int blaze = 32;
  public static int pigman = 32;
  public static int enderdragon = 32;
  public static int cavespider = 32;
  public static int spider = 32;
  public static int silverfish = 32;
  public static int snowman = 32;
  public static int slime = 32;
  public static int squid = 32;
  public static int villager = 32;
  public static int wolf = 32;
  FileConfiguration config;
  // Declare perm system variables
  public static PermissionHandler perms3;
  public static PermissionManager permsEx;
  public static int permSystem;
  // Display text thats editable
  public static String ver = Goldecon.info.getVersion();
  public static String edition = ChatColor.GOLD + "[ge+] ";
  // Set the CommandMethods class
  CommandMethods cmdMth;
  GoldeconShop geShop;
  GoldeconRegionMarket geRegion;
  // WorldGuard status variable
  public static int wgHook = 0;
  public static WorldGuardPlugin wg;

  public void onEnable()
  {
    Goldecon.log = Logger.getLogger("Minecraft");
    Goldecon.info = getDescription();

    banks = SaveManager.load(this, "banks");

    // Load permission system
    permSystem = setupPermissions();
    
    // Hook into WorldGuard
    wg = getWorldGuard();
    
    // Enable CommandMethods class
    cmdMth = new CommandMethods(this);
    geShop = new GoldeconShop(this);
    geRegion = new GoldeconRegionMarket(this, wg);
    
    // Initiate listeners
    getServer().getPluginManager().registerEvents(new EListener(this), this);
    getServer().getPluginManager().registerEvents(new GoldeconShopListener(this), this);
    getServer().getPluginManager().registerEvents(new GoldeconRegionListener(this, wg), this);
    getServer().getPluginManager().registerEvents(new PListener(this), this);

    creeper = cfgGetInt("Bad Mobs.Creeper", 5);
    zombie = cfgGetInt("Bad Mobs.Zombie", 2);
    skeleton = cfgGetInt("Bad Mobs.Skeleton", 4);
    ghast = cfgGetInt("Bad Mobs.Ghast", 10);
    enderman = cfgGetInt("Bad Mobs.Endermen", 7);
    blaze = cfgGetInt("Bad Mobs.Blaze", 4);
    pigman = cfgGetInt("Bad Mobs.Pigmen", 1);
    enderdragon = cfgGetInt("Bad Mobs.Ender Dragon", 50);
    cavespider = cfgGetInt("Bad Mobs.Cave Spider", 8);
    spider = cfgGetInt("Bad Mobs.Spider", 5);
    silverfish = cfgGetInt("Bad Mobs.Silverfish", 3);
    slime = cfgGetInt("Bad Mobs.Slime", 15);

    cow = cfgGetInt("Good Mobs.Cow", 2);
    chicken = cfgGetInt("Good Mobs.Chicken", 2);
    pig = cfgGetInt("Good Mobs.Pig", 2);
    sheep = cfgGetInt("Good Mobs.Sheep", 2);
    mushroomcow = cfgGetInt("Good Mobs.Mushroom Cow", 5);
    villager = cfgGetInt("Good Mobs.Villager", 8);
    squid = cfgGetInt("Good Mobs.Squid", 1);
    wolf = cfgGetInt("Good Mobs.Wolf", 5);
    snowman = cfgGetInt("Good Mobs.Snowman", 0);

    saveConfig();
    
    Goldecon.log.info(Goldecon.info.getName() + " has been enabled");
  }

  public void onDisable()
  {
    SaveManager.save(this, banks);

    Goldecon.log.info(Goldecon.info.getName() + " has been disabled");
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
          else if (args[0].equalsIgnoreCase("stats")) {
            if (args.length == 1) {
              if (cmdMth.getTopPlayer() != "")
                player.sendMessage(edition + ChatColor.GREEN + "The top player is " + cmdMth.getTopPlayer() + " with " + Goldecon.banks.get(cmdMth.getTopPlayer()) + " gold.");
              else {
                player.sendMessage(edition + ChatColor.RED + "There is no top player.");
                return true;
              }
              if (cmdMth.getSecondPlayer() != "") {
                player.sendMessage(edition + ChatColor.GREEN + "The number 2 player is " + cmdMth.getSecondPlayer() + " with " + Goldecon.banks.get(cmdMth.getSecondPlayer()) + " gold.");
              }
              if (cmdMth.getThirdPlayer() != "") {
                player.sendMessage(edition + ChatColor.GREEN + "The number 3 player is " + cmdMth.getThirdPlayer() + " with " + Goldecon.banks.get(cmdMth.getThirdPlayer()) + " gold.");
              }

            }
            else if (args.length == 2) {
              if (Goldecon.banks.contains(args[1])) {
                int amount1 = ((Integer)Goldecon.banks.get(args[1])).intValue();
                player.sendMessage(edition + ChatColor.GOLD + args[1] + " has " + amount1 + " gold in the bank.");
                return true;
              } else {
                player.sendMessage(edition + ChatColor.RED + "Can not find " + args[1] + ".");
                return true;
              }
            } else {
              player.sendMessage(edition + ChatColor.RED + "/ge stats [player name]");
              return true;
            }
          }
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
      else if (cmd.getName().equalsIgnoreCase("gebank"))
      {
      	if(!checkPerm(player, "goldecon.bank.use")){
      		player.sendMessage(edition + ChatColor.RED + "You dont have permission to do that, dave.");
      		return true;
      	}
        if (args.length == 0) {
          int amount = ((Integer)Goldecon.banks.get(player.getName())).intValue();
          player.sendMessage(edition + ChatColor.GOLD + "You have " + amount + " gold in your bank.");
        }
        else if (args[0].equalsIgnoreCase("deposit")) {
          if (args.length == 2) {
            int amount = Integer.parseInt(args[1]);
            if (player.getInventory().contains(Material.GOLD_NUGGET, amount)) {
              player.getInventory().removeItem(new ItemStack[] { new ItemStack(Material.GOLD_NUGGET, amount) });
              int old_amount = ((Integer)Goldecon.banks.get(player.getName())).intValue();
              Goldecon.banks.set(player.getName(), Integer.valueOf(old_amount + amount));
              player.sendMessage(edition + ChatColor.GOLD + "You now have " + (old_amount + amount) + " in your bank.");
            } else {
              player.sendMessage(edition + ChatColor.RED + "You don't have that much money with you.");
            }
          }

        }
        else if (args[0].equalsIgnoreCase("withdraw")) {
          if (args.length == 2) {
            int amount = Integer.parseInt(args[1]);
            int bankamount = ((Integer)Goldecon.banks.get(player.getName())).intValue();
            if (bankamount >= amount) {
              player.getInventory().addItem(new ItemStack[] { new ItemStack(Material.GOLD_NUGGET, amount) });
              Goldecon.banks.set(player.getName(), Integer.valueOf(bankamount - amount));
              player.sendMessage(edition + ChatColor.GOLD + "You now have " + (bankamount - amount) + " in your bank.");
            } else {
              player.sendMessage(edition + ChatColor.RED + "You don't have that much money in your bank.");
            }
          }
        }
        else
        {
          player.sendMessage(edition + ChatColor.RED + "/gebank < money | deposit <amount> | withdraw <amount> >");
        }
      }
      
      // GoldeconShop command method is activated here after the core stuff
      // TODO Move the /geshop command to goldecon.shops.GoldeconShop}
      // TODO /geshop command logic start
      else if ((cmd.getName().equalsIgnoreCase("geshop")) && (args.length < 4)){
          if (((sender instanceof Player)) &&
          (args.length == 0)) {
          Player plr = (Player)sender;
          if(!checkPerm(plr, "goldecon.shop.create")){
          plr.sendMessage(edition + ChatColor.RED + "You dont have permission to do that, dave.");
          return true;
          }
          geShop.shopHelp(plr);
          }
         }
           else if ((cmd.getName().equalsIgnoreCase("geshop")) &&
             ((sender instanceof Player)) &&
             (args.length == 4)) {
             Player plr = (Player)sender;
             if(!checkPerm(plr, "goldecon.shop.create")){
           	  	plr.sendMessage(edition + ChatColor.RED + "You dont have permission to do that, dave.");
           	  		return true;
             }  
             geShop.shopCreate(plr, args);
          }
         else{
           sender.sendMessage(edition + "This is for player use only.");
           return true;
         }
      }
      else if (cmd.getName().equalsIgnoreCase("geshophelp")) {
          if (((sender instanceof Player)) &&
            (args.length == 0)) {
            Player plr = (Player)sender;
           if(!checkPerm(plr, "goldecon.shop.create")){
           plr.sendMessage(edition + ChatColor.RED + "You dont have permission to do that, dave.");
           return true;
           }
           geShop.shopHelp(plr);
          }
      }
    // TODO /geshop command logic end
    
    // GoldeconRegionMarket command method is activated here
    // TODO Move the /geregion command to goldecon.regionmarket.GoldeconRegionMarket}
    // TODO /geregion command logic start
    else if ((cmd.getName().equalsIgnoreCase("geregion")) && (args.length < 4)){
        if (((sender instanceof Player)) &&
        (args.length == 0)) {
        Player plr = (Player)sender;
        if(!checkPerm(plr, "goldecon.region.sell")){
        plr.sendMessage(edition + ChatColor.RED + "You dont have permission to do that, dave.");
        return true;
        }
        geRegion.regionHelp(plr);
        }
       }
         else if ((cmd.getName().equalsIgnoreCase("geregion")) &&
           ((sender instanceof Player)) &&
           (args.length == 4)) {
           Player plr = (Player)sender;
           if(!checkPerm(plr, "goldecon.region.sell")){
         	  	plr.sendMessage(edition + ChatColor.RED + "You dont have permission to do that, dave.");
         	  		return true;
           }  
           geRegion.rmsCreate(plr, args);
        }
       else{
         sender.sendMessage(edition + "This is for player use only.");
         return true;
         }
    // /geregion command logic end
    return true;
}
  
  public int cfgGetInt(String path, int dflt){
	  int theNumb;
	  theNumb = getConfig().getInt(path, dflt);
	  if(theNumb == dflt){
		  this.getConfig().set(path, dflt);
		  return dflt;
	  }
	  else{
	  return theNumb;
	  }
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
  
  //Call this to load WorldGuard
	public WorldGuardPlugin getWorldGuard(){
		Plugin plugin = this.getServer().getPluginManager().getPlugin("WorldGuard");
		 
	    // WorldGuard may not be loaded
	    if (plugin == null || !(plugin instanceof WorldGuardPlugin)) {
	        Goldecon.log.info(Goldecon.info.getName() + " Couldn't hook into WorldGuard. Disabling GoldeconRegionMarket.");
	        Goldecon.wgHook = 0;
	        return null;
	    }

       Goldecon.wgHook = 1;
	    return (WorldGuardPlugin) plugin;
	}
}