package org.goldecon.regionmarket;

import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.goldecon.goldeconplus.Goldecon;

import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.managers.RegionManager;

public class GoldeconRegionMarket {
	
	Goldecon ge;
	WorldGuardPlugin wg;
	// Set the text for system messages
	String ver = Goldecon.ver;
	String edition = Goldecon.edition;
	
	public GoldeconRegionMarket(Goldecon goldecon, WorldGuardPlugin wgp){
		this.ge = goldecon;
		this.wg = wgp;
	}
	
	public boolean regionHelp(Player player){
		// Check if WG is hooked in
		if(!(Goldecon.wgHook == 1)){
      	  	player.sendMessage(edition + ChatColor.RED + "You can't use RegionMarkets becasue WorldGuard isn't on the server!");
			return false;
		}
        player.sendMessage(ChatColor.YELLOW + "<- goldecon+ | Region Commands ->");
        player.sendMessage(ChatColor.RED + "| goldecon+ by Stoolbend - Version " + ver + " |");
        player.sendMessage(ChatColor.RED + "| goldecon by boardinggamer & Kierrow |");
        player.sendMessage(ChatColor.AQUA + "COMMANDS:");
        player.sendMessage(ChatColor.LIGHT_PURPLE + "/geregion " + ChatColor.DARK_PURPLE + "<region name> <price>");
        player.sendMessage(ChatColor.GOLD + "- creates a shop");
        player.sendMessage(ChatColor.AQUA + "HOW TO USE A REGION SHOP:");
        player.sendMessage(ChatColor.LIGHT_PURPLE + "To Buy a region:");
        player.sendMessage(ChatColor.GOLD + "- Right click on the sign");
        return true;
	}
	
	public boolean rmsCreate(Player plr, String[] args){
		// Check if WG is hooked in
		if(!(Goldecon.wgHook == 1)){
      	  	plr.sendMessage(edition + ChatColor.RED + "You can't use RegionMarkets becasue WorldGuard isn't on the server!");
			return false;
		}
		Block block = plr.getTargetBlock(null, 5);
        String region = args[0];
        String price = args[1];

        if ((block.getTypeId() == 63) || (block.getTypeId() == 68)) {
          Sign s = (Sign)block.getState();
          String line0 = s.getLine(0);
          String line1 = s.getLine(1);
          String line2 = s.getLine(2);
          String line3 = s.getLine(3);
            if ((line0.equalsIgnoreCase("")) && (line1.equalsIgnoreCase("")) && (line2.equalsIgnoreCase("")) && (line3.equalsIgnoreCase(""))) {
              // Check if user has build ability + Create perm / Override perm / isOp()
              if(!canMakeSign(plr, region))
              {
            	  plr.sendMessage(edition + ChatColor.RED + "You don't have permission to offer this region for sale!");
            	  return true;
              }
              s.setLine(0, "[region]");
              s.setLine(1, plr.getName());
              s.setLine(2, region);
              s.setLine(3, "Price: " + price);
              s.update();
              plr.sendMessage(edition + ChatColor.LIGHT_PURPLE + "You have offered a region for sale!.");
            } else {
              plr.sendMessage(edition + ChatColor.RED + "The sign must be Blank.");
            }
          }
        else
        {
          plr.sendMessage(edition + ChatColor.RED + "You must be looking at a sign to use this command.");
        }
		return true;
	}
	
	private boolean canMakeSign(Player player, String region) {
		// Query the WG Region manager to see if player can build
		LocalPlayer localPlayer = wg.wrapPlayer(player);
		RegionManager regionManager = wg.getRegionManager(player.getWorld());
		ApplicableRegionSet set = regionManager.getApplicableRegions(regionManager.getRegion(region));
		
		// Get all the conditions for the mega if statement
        boolean playerCanBuild = set.canBuild(localPlayer);
        boolean hasCreatePerm = Goldecon.checkPerm(player, "goldecon.region.sell");
        boolean hasOverridePerm = Goldecon.checkPerm(player, "goldecon.region.admin");
        boolean playerIsOp = player.isOp();
        
        // Do the deciding!
        if((playerCanBuild && hasCreatePerm) || hasOverridePerm || playerIsOp){
        	return true;
        }
        else{
		return false;
        }
	}
}
