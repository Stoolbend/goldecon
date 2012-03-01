package org.goldecon.goldeconplus;

import net.kierrow.io.Save;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PListener implements Listener
{
  Goldecon plugin;
  Save<Integer> bank2 = Goldecon.banks;
  String edition = Goldecon.edition;
  
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

    e.getPlayer().sendMessage(edition + ChatColor.GOLD +  "This server uses goldecon as the economy!");
  }
}