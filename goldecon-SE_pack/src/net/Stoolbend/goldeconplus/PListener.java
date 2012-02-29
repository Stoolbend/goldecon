package net.Stoolbend.goldeconplus;

import net.kierrow.io.Save;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;

public class PListener implements Listener
{
  Goldecon plugin;
  Save<Integer> bank2 = Goldecon.banks;
  //TODO Debug is enabled! Remember to disable
  boolean debug = false;
  
  public PListener(Goldecon aThis)
  {
    this.plugin = aThis;
  }

  @EventHandler
  public void onPlayerJoin(PlayerJoinEvent e)
  {
    if (!this.bank2.contains(e.getPlayer().getName())) {
      this.bank2.set(e.getPlayer().getName(), Integer.valueOf(0));
    }

    e.getPlayer().sendMessage(ChatColor.GOLD + "[ge+] " + ChatColor.GOLD +  "This server uses goldecon-SE as the economy!");
  }
  
  @EventHandler
  public void onPlayerInteract(PlayerInteractEvent event) {
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
            (!line1.equalsIgnoreCase(plr.getDisplayName()))) {
            if (plr.getInventory().contains(Material.GOLD_NUGGET, price)) {
              Chest chest = (Chest)block2.getState();
              if (chest.getInventory().contains(Material.valueOf(line2), itemamount)) {
                  if(debug == true){
                      // Debug stuff
                      plr.sendMessage("Transaction: Selling");
                      plr.sendMessage("itemamount = " + itemamount);
                      plr.sendMessage("price = " + price);
                      plr.sendMessage("Gold in chest = " + chest.getInventory().all(Material.GOLD_NUGGET));
                      plr.sendMessage("Block in chest = " + chest.getInventory().all(Material.valueOf(line2)));
                      plr.sendMessage("Gold in inv = " + plr.getInventory().all(Material.GOLD_NUGGET));
                      plr.sendMessage("Block in inv = " + plr.getInventory().all(Material.valueOf(line2)));
                    }
                plr.getInventory().addItem(new ItemStack[] { new ItemStack(Material.valueOf(line2), itemamount) });
                if(debug == true){
                	  plr.sendMessage("Gold in chest = " + chest.getInventory().all(Material.GOLD_NUGGET));
                	  plr.sendMessage("Block in chest = " + chest.getInventory().all(Material.valueOf(line2)));
                	  plr.sendMessage("Gold in inv = " + plr.getInventory().all(Material.GOLD_NUGGET));
                	  plr.sendMessage("Block in inv = " + plr.getInventory().all(Material.valueOf(line2)));
                  }
                plr.getInventory().removeItem(new ItemStack[] { new ItemStack(Material.GOLD_NUGGET, price) });
                if(debug == true){
                	  plr.sendMessage("Gold in chest = " + chest.getInventory().all(Material.GOLD_NUGGET));
                	  plr.sendMessage("Block in chest = " + chest.getInventory().all(Material.valueOf(line2)));
                	  plr.sendMessage("Gold in inv = " + plr.getInventory().all(Material.GOLD_NUGGET));
                	  plr.sendMessage("Block in inv = " + plr.getInventory().all(Material.valueOf(line2)));
                  }
                chest.getInventory().addItem(new ItemStack[] { new ItemStack(Material.GOLD_NUGGET, price) });
                if(debug == true){
                	  plr.sendMessage("Gold in chest = " + chest.getInventory().all(Material.GOLD_NUGGET));
                	  plr.sendMessage("Block in chest = " + chest.getInventory().all(Material.valueOf(line2)));
                	  plr.sendMessage("Gold in inv = " + plr.getInventory().all(Material.GOLD_NUGGET));
                	  plr.sendMessage("Block in inv = " + plr.getInventory().all(Material.valueOf(line2)));
                  }
                chest.getInventory().removeItem(new ItemStack[] { new ItemStack(Material.valueOf(line2), itemamount) });
                if(debug == true){
                	  plr.sendMessage("Gold in chest = " + chest.getInventory().all(Material.GOLD_NUGGET));
                	  plr.sendMessage("Block in chest = " + chest.getInventory().all(Material.valueOf(line2)));
                	  plr.sendMessage("Gold in inv = " + plr.getInventory().all(Material.GOLD_NUGGET));
                	  plr.sendMessage("Block in inv = " + plr.getInventory().all(Material.valueOf(line2)));
                  }
                plr.sendMessage(ChatColor.GOLD + "[ge+] " + ChatColor.GREEN + "You got " + itemamount + " " + line2.toLowerCase().replace("_", " ") + " for " + price + " gold.");
              } else {
                plr.sendMessage(ChatColor.GOLD + "[ge+] " + ChatColor.RED + "Not enough in stock.");
              }
            } else {
              plr.sendMessage(ChatColor.GOLD + "[ge+] " + ChatColor.RED + "You do not have enough money for that.");
            }
          }
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
            (!line1.equalsIgnoreCase(plr.getDisplayName()))) {
            event.setCancelled(true);
            plr.sendMessage(ChatColor.GOLD + "[ge+] " + ChatColor.RED + "You can not open someone else's shop.");
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
            (!line1.equalsIgnoreCase(plr.getDisplayName()))) {
            Chest chest = (Chest)block2.getState();
            if (plr.getInventory().contains(Material.valueOf(line2), itemamount)) {
              if (chest.getInventory().contains(Material.GOLD_NUGGET, price)) {
            if(debug == true){
              // Debug stuff
              plr.sendMessage("Transaction: Selling");
              plr.sendMessage("itemamount = " + itemamount);
              plr.sendMessage("price = " + price);
              plr.sendMessage("Gold in chest = " + chest.getInventory().all(Material.GOLD_NUGGET));
              plr.sendMessage("Block in chest = " + chest.getInventory().all(Material.valueOf(line2)));
              plr.sendMessage("Gold in inv = " + plr.getInventory().all(Material.GOLD_NUGGET));
              plr.sendMessage("Block in inv = " + plr.getInventory().all(Material.valueOf(line2)));
            }
                plr.getInventory().removeItem(new ItemStack[] { new ItemStack(Material.valueOf(line2), itemamount) });
            if(debug == true){
          	  plr.sendMessage("Gold in chest = " + chest.getInventory().all(Material.GOLD_NUGGET));
          	  plr.sendMessage("Block in chest = " + chest.getInventory().all(Material.valueOf(line2)));
          	  plr.sendMessage("Gold in inv = " + plr.getInventory().all(Material.GOLD_NUGGET));
          	  plr.sendMessage("Block in inv = " + plr.getInventory().all(Material.valueOf(line2)));
            }
                plr.getInventory().addItem(new ItemStack[] { new ItemStack(Material.GOLD_NUGGET, price) });
            if(debug == true){
          	  plr.sendMessage("Gold in chest = " + chest.getInventory().all(Material.GOLD_NUGGET));
          	  plr.sendMessage("Block in chest = " + chest.getInventory().all(Material.valueOf(line2)));
          	  plr.sendMessage("Gold in inv = " + plr.getInventory().all(Material.GOLD_NUGGET));
          	  plr.sendMessage("Block in inv = " + plr.getInventory().all(Material.valueOf(line2)));
            }
                chest.getInventory().removeItem(new ItemStack[] { new ItemStack(Material.GOLD_NUGGET, price) });
            if(debug == true){
          	  plr.sendMessage("Gold in chest = " + chest.getInventory().all(Material.GOLD_NUGGET));
          	  plr.sendMessage("Block in chest = " + chest.getInventory().all(Material.valueOf(line2)));
          	  plr.sendMessage("Gold in inv = " + plr.getInventory().all(Material.GOLD_NUGGET));
          	  plr.sendMessage("Block in inv = " + plr.getInventory().all(Material.valueOf(line2)));
            }
                chest.getInventory().addItem(new ItemStack[] { new ItemStack(Material.valueOf(line2), itemamount) });
            if(debug == true){
          	  plr.sendMessage("Gold in chest = " + chest.getInventory().all(Material.GOLD_NUGGET));
          	  plr.sendMessage("Block in chest = " + chest.getInventory().all(Material.valueOf(line2)));
          	  plr.sendMessage("Gold in inv = " + plr.getInventory().all(Material.GOLD_NUGGET));
          	  plr.sendMessage("Block in inv = " + plr.getInventory().all(Material.valueOf(line2)));
            }
                plr.sendMessage(ChatColor.GOLD + "[ge+] " + ChatColor.GREEN + "You sold " + itemamount + " " + line2.toLowerCase().replace("_", " ") + " for " + price + " gold.");
              } else {
                plr.sendMessage(ChatColor.GOLD + "[ge+] " + ChatColor.RED + "Not enough gold in stock.");
              }
            }
            else plr.sendMessage(ChatColor.GOLD + "[ge+] " + ChatColor.RED + "You do not have enough for that.");
          }
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
            (!line1.equalsIgnoreCase(plr.getDisplayName()))) {
            event.setCancelled(true);
            plr.sendMessage(ChatColor.GOLD + "[ge+] " + ChatColor.RED + "You can not break someone else's shop.");
          }
        }
      }
    }
  }
}