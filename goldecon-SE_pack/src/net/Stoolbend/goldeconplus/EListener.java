package net.Stoolbend.goldeconplus;

import java.util.Random;
import org.bukkit.Material;
import org.bukkit.entity.Blaze;
import org.bukkit.entity.CaveSpider;
import org.bukkit.entity.Chicken;
import org.bukkit.entity.Cow;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.Enderman;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Ghast;
import org.bukkit.entity.MushroomCow;
import org.bukkit.entity.Pig;
import org.bukkit.entity.PigZombie;
import org.bukkit.entity.Sheep;
import org.bukkit.entity.Silverfish;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Slime;
import org.bukkit.entity.Snowman;
import org.bukkit.entity.Spider;
import org.bukkit.entity.Squid;
import org.bukkit.entity.Villager;
import org.bukkit.entity.Wolf;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

public class EListener implements Listener
{
  Goldecon plugin;

  public EListener(Goldecon aThis)
  {
    this.plugin = aThis;
  }

  @EventHandler
  public void onEntityDeath(EntityDeathEvent event) {
    Entity entity = event.getEntity();
    if ((entity instanceof Creeper))
    {
      Random generator = new Random();
      int amount = generator.nextInt(Goldecon.creeper + 1) + 1;
      int drop = amount - 1;

      if (drop != 0)
        event.getDrops().add(new ItemStack(Material.GOLD_NUGGET, drop));
    }
    else if ((entity instanceof Blaze))
    {
      Random generator = new Random();
      int amount = generator.nextInt(Goldecon.blaze + 1) + 1;
      int drop = amount - 1;

      if (drop != 0)
        event.getDrops().add(new ItemStack(Material.GOLD_NUGGET, drop));
    }
    else if ((entity instanceof Chicken))
    {
      Random generator = new Random();
      int amount = generator.nextInt(Goldecon.chicken + 1) + 1;
      int drop = amount - 1;

      if (drop != 0)
        event.getDrops().add(new ItemStack(Material.GOLD_NUGGET, drop));
    }
    else if ((entity instanceof Cow))
    {
      Random generator = new Random();
      int amount = generator.nextInt(Goldecon.cow + 1) + 1;
      int drop = amount - 1;

      if (drop != 0)
        event.getDrops().add(new ItemStack(Material.GOLD_NUGGET, drop));
    }
    else if ((entity instanceof EnderDragon))
    {
      Random generator = new Random();
      int amount = generator.nextInt(Goldecon.enderdragon + 1) + 1;
      int drop = amount - 1;

      if (drop != 0)
        event.getDrops().add(new ItemStack(Material.GOLD_NUGGET, drop));
    }
    else if ((entity instanceof Enderman))
    {
      Random generator = new Random();
      int amount = generator.nextInt(Goldecon.enderman + 1) + 1;
      int drop = amount - 1;

      if (drop != 0)
        event.getDrops().add(new ItemStack(Material.GOLD_NUGGET, drop));
    }
    else if ((entity instanceof Ghast))
    {
      Random generator = new Random();
      int amount = generator.nextInt(Goldecon.ghast + 1) + 1;
      int drop = amount - 1;

      if (drop != 0)
        event.getDrops().add(new ItemStack(Material.GOLD_NUGGET, drop));
    }
    else if ((entity instanceof MushroomCow))
    {
      Random generator = new Random();
      int amount = generator.nextInt(Goldecon.mushroomcow + 1) + 1;
      int drop = amount - 1;

      if (drop != 0)
        event.getDrops().add(new ItemStack(Material.GOLD_NUGGET, drop));
    }
    else if ((entity instanceof Pig))
    {
      Random generator = new Random();
      int amount = generator.nextInt(Goldecon.pig + 1) + 1;
      int drop = amount - 1;

      if (drop != 0)
        event.getDrops().add(new ItemStack(Material.GOLD_NUGGET, drop));
    }
    else if ((entity instanceof PigZombie))
    {
      Random generator = new Random();
      int amount = generator.nextInt(Goldecon.pigman + 1) + 1;
      int drop = amount - 1;

      if (drop != 0)
        event.getDrops().add(new ItemStack(Material.GOLD_NUGGET, drop));
    }
    else if ((entity instanceof Sheep))
    {
      Random generator = new Random();
      int amount = generator.nextInt(Goldecon.sheep + 1) + 1;
      int drop = amount - 1;

      if (drop != 0)
        event.getDrops().add(new ItemStack(Material.GOLD_NUGGET, drop));
    }
    else if ((entity instanceof Skeleton))
    {
      Random generator = new Random();
      int amount = generator.nextInt(Goldecon.skeleton + 1) + 1;
      int drop = amount - 1;

      if (drop != 0)
        event.getDrops().add(new ItemStack(Material.GOLD_NUGGET, drop));
    }
    else if ((entity instanceof Zombie))
    {
      Random generator = new Random();
      int amount = generator.nextInt(Goldecon.zombie + 1) + 1;
      int drop = amount - 1;

      if (drop != 0)
        event.getDrops().add(new ItemStack(Material.GOLD_NUGGET, drop));
    }
    else if ((entity instanceof CaveSpider))
    {
      Random generator = new Random();
      int amount = generator.nextInt(Goldecon.cavespider + 1) + 1;
      int drop = amount - 1;

      if (drop != 0)
        event.getDrops().add(new ItemStack(Material.GOLD_NUGGET, drop));
    }
    else if ((entity instanceof Spider))
    {
      Random generator = new Random();
      int amount = generator.nextInt(Goldecon.spider + 1) + 1;
      int drop = amount - 1;

      if (drop != 0)
        event.getDrops().add(new ItemStack(Material.GOLD_NUGGET, drop));
    }
    else if ((entity instanceof Silverfish))
    {
      Random generator = new Random();
      int amount = generator.nextInt(Goldecon.silverfish + 1) + 1;
      int drop = amount - 1;

      if (drop != 0)
        event.getDrops().add(new ItemStack(Material.GOLD_NUGGET, drop));
    }
    else if ((entity instanceof Slime))
    {
      Random generator = new Random();
      int amount = generator.nextInt(Goldecon.slime + 1) + 1;
      int drop = amount - 1;

      if (drop != 0)
        event.getDrops().add(new ItemStack(Material.GOLD_NUGGET, drop));
    }
    else if ((entity instanceof Snowman))
    {
      Random generator = new Random();
      int amount = generator.nextInt(Goldecon.snowman + 1) + 1;
      int drop = amount - 1;

      if (drop != 0)
        event.getDrops().add(new ItemStack(Material.GOLD_NUGGET, drop));
    }
    else if ((entity instanceof Squid))
    {
      Random generator = new Random();
      int amount = generator.nextInt(Goldecon.squid + 1) + 1;
      int drop = amount - 1;

      if (drop != 0)
        event.getDrops().add(new ItemStack(Material.GOLD_NUGGET, drop));
    }
    else if ((entity instanceof Villager))
    {
      Random generator = new Random();
      int amount = generator.nextInt(Goldecon.villager + 1) + 1;
      int drop = amount - 1;

      if (drop != 0)
        event.getDrops().add(new ItemStack(Material.GOLD_NUGGET, drop));
    }
    else if ((entity instanceof Wolf))
    {
      Random generator = new Random();
      int amount = generator.nextInt(Goldecon.wolf + 1) + 1;
      int drop = amount - 1;

      if (drop != 0)
        event.getDrops().add(new ItemStack(Material.GOLD_NUGGET, drop));
    }
  }
}