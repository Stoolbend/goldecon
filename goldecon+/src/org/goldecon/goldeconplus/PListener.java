package org.goldecon.goldeconplus;

import net.kierrow.io.Save;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.goldecon.bank.GoldeconBank;

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
    GoldeconBank.playerJoin(e);
    e.getPlayer().sendMessage(edition + ChatColor.GOLD +  "This server uses goldecon as the economy!");
  }
}