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
  String ver = Goldecon.ver;
  
  public PListener(Goldecon aThis)
  {
    this.plugin = aThis;
  }

  @EventHandler
  public void onPlayerJoin(PlayerJoinEvent e)
  {
    //e.getPlayer().sendMessage(edition + ChatColor.GOLD +  "This server uses goldecon as the economy!");
    if(ver.contains("alpha") || ver.contains("beta") || ver.contains("dev")){
        e.getPlayer().sendMessage(edition + ChatColor.GREEN + "This is a development version! Please report any problems");
        e.getPlayer().sendMessage(edition + ChatColor.GREEN + "at http://github.com/Stoolbend/goldecon/issues. Thank you!");
    }
    e.getPlayer().sendMessage(edition + ChatColor.YELLOW +  "Version " + ver + " | http://bit.ly/goldecon");
    GoldeconBank.playerJoin(e);
  }
}