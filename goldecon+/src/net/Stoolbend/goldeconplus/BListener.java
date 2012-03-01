package net.Stoolbend.goldeconplus;

import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.SignChangeEvent;

public class BListener implements Listener
{
  Goldecon plugin;
  String edition = Goldecon.edition;

  public BListener(Goldecon goldecon)
  {
    this.plugin = goldecon;
  }

  @EventHandler
  public void onBlockBreak(BlockBreakEvent event) {
    Block block = event.getBlock();
    Player plr = event.getPlayer();

    if ((block.getTypeId() == 63) || (block.getTypeId() == 68)) {
      Sign s = (Sign)block.getState();
      String line0 = s.getLine(0);
      String line1 = s.getLine(1);

      if (line0.equalsIgnoreCase("[shop]"))
        if (!plr.getDisplayName().equalsIgnoreCase(line1)) {
          event.setCancelled(true);
          plr.sendMessage(edition + ChatColor.RED + "You can not break someone else's shop.");
          s.update();
        } else {
          plr.sendMessage(edition + ChatColor.AQUA + "You broke your shop.");
        }
    }
  }

  @EventHandler
  public void onSignChange(SignChangeEvent event)
  {
    if (event.getLine(0).equalsIgnoreCase("[shop]")) {
      event.setCancelled(true);
      event.getPlayer().sendMessage(edition + ChatColor.RED + "You cant create a shop that way.");
    }
  }
}