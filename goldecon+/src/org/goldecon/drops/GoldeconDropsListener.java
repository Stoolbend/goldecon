package org.goldecon.drops;

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

public class GoldeconDropsListener implements Listener{
	
	@EventHandler
	  public void onEntityDeath(EntityDeathEvent event) {
	    Entity entity = event.getEntity();
	    if ((entity instanceof Creeper))
	    {
	      Random generator = new Random();
	      int amount = generator.nextInt(GoldeconDrops.creeper + 1) + 1;
	      int drop = amount - 1;

	      if (drop != 0)
	        event.getDrops().add(new ItemStack(Material.GOLD_NUGGET, drop));
	    }
	    else if ((entity instanceof Blaze))
	    {
	      Random generator = new Random();
	      int amount = generator.nextInt(GoldeconDrops.blaze + 1) + 1;
	      int drop = amount - 1;

	      if (drop != 0)
	        event.getDrops().add(new ItemStack(Material.GOLD_NUGGET, drop));
	    }
	    else if ((entity instanceof Chicken))
	    {
	      Random generator = new Random();
	      int amount = generator.nextInt(GoldeconDrops.chicken + 1) + 1;
	      int drop = amount - 1;

	      if (drop != 0)
	        event.getDrops().add(new ItemStack(Material.GOLD_NUGGET, drop));
	    }
	    else if ((entity instanceof Cow))
	    {
	      Random generator = new Random();
	      int amount = generator.nextInt(GoldeconDrops.cow + 1) + 1;
	      int drop = amount - 1;

	      if (drop != 0)
	        event.getDrops().add(new ItemStack(Material.GOLD_NUGGET, drop));
	    }
	    else if ((entity instanceof EnderDragon))
	    {
	      Random generator = new Random();
	      int amount = generator.nextInt(GoldeconDrops.enderdragon + 1) + 1;
	      int drop = amount - 1;

	      if (drop != 0)
	        event.getDrops().add(new ItemStack(Material.GOLD_NUGGET, drop));
	    }
	    else if ((entity instanceof Enderman))
	    {
	      Random generator = new Random();
	      int amount = generator.nextInt(GoldeconDrops.enderman + 1) + 1;
	      int drop = amount - 1;

	      if (drop != 0)
	        event.getDrops().add(new ItemStack(Material.GOLD_NUGGET, drop));
	    }
	    else if ((entity instanceof Ghast))
	    {
	      Random generator = new Random();
	      int amount = generator.nextInt(GoldeconDrops.ghast + 1) + 1;
	      int drop = amount - 1;

	      if (drop != 0)
	        event.getDrops().add(new ItemStack(Material.GOLD_NUGGET, drop));
	    }
	    else if ((entity instanceof MushroomCow))
	    {
	      Random generator = new Random();
	      int amount = generator.nextInt(GoldeconDrops.mushroomcow + 1) + 1;
	      int drop = amount - 1;

	      if (drop != 0)
	        event.getDrops().add(new ItemStack(Material.GOLD_NUGGET, drop));
	    }
	    else if ((entity instanceof Pig))
	    {
	      Random generator = new Random();
	      int amount = generator.nextInt(GoldeconDrops.pig + 1) + 1;
	      int drop = amount - 1;

	      if (drop != 0)
	        event.getDrops().add(new ItemStack(Material.GOLD_NUGGET, drop));
	    }
	    else if ((entity instanceof PigZombie))
	    {
	      Random generator = new Random();
	      int amount = generator.nextInt(GoldeconDrops.pigman + 1) + 1;
	      int drop = amount - 1;

	      if (drop != 0)
	        event.getDrops().add(new ItemStack(Material.GOLD_NUGGET, drop));
	    }
	    else if ((entity instanceof Sheep))
	    {
	      Random generator = new Random();
	      int amount = generator.nextInt(GoldeconDrops.sheep + 1) + 1;
	      int drop = amount - 1;

	      if (drop != 0)
	        event.getDrops().add(new ItemStack(Material.GOLD_NUGGET, drop));
	    }
	    else if ((entity instanceof Skeleton))
	    {
	      Random generator = new Random();
	      int amount = generator.nextInt(GoldeconDrops.skeleton + 1) + 1;
	      int drop = amount - 1;

	      if (drop != 0)
	        event.getDrops().add(new ItemStack(Material.GOLD_NUGGET, drop));
	    }
	    else if ((entity instanceof Zombie))
	    {
	      Random generator = new Random();
	      int amount = generator.nextInt(GoldeconDrops.zombie + 1) + 1;
	      int drop = amount - 1;

	      if (drop != 0)
	        event.getDrops().add(new ItemStack(Material.GOLD_NUGGET, drop));
	    }
	    else if ((entity instanceof CaveSpider))
	    {
	      Random generator = new Random();
	      int amount = generator.nextInt(GoldeconDrops.cavespider + 1) + 1;
	      int drop = amount - 1;

	      if (drop != 0)
	        event.getDrops().add(new ItemStack(Material.GOLD_NUGGET, drop));
	    }
	    else if ((entity instanceof Spider))
	    {
	      Random generator = new Random();
	      int amount = generator.nextInt(GoldeconDrops.spider + 1) + 1;
	      int drop = amount - 1;

	      if (drop != 0)
	        event.getDrops().add(new ItemStack(Material.GOLD_NUGGET, drop));
	    }
	    else if ((entity instanceof Silverfish))
	    {
	      Random generator = new Random();
	      int amount = generator.nextInt(GoldeconDrops.silverfish + 1) + 1;
	      int drop = amount - 1;

	      if (drop != 0)
	        event.getDrops().add(new ItemStack(Material.GOLD_NUGGET, drop));
	    }
	    else if ((entity instanceof Slime))
	    {
	      Random generator = new Random();
	      int amount = generator.nextInt(GoldeconDrops.slime + 1) + 1;
	      int drop = amount - 1;

	      if (drop != 0)
	        event.getDrops().add(new ItemStack(Material.GOLD_NUGGET, drop));
	    }
	    else if ((entity instanceof Snowman))
	    {
	      Random generator = new Random();
	      int amount = generator.nextInt(GoldeconDrops.snowman + 1) + 1;
	      int drop = amount - 1;

	      if (drop != 0)
	        event.getDrops().add(new ItemStack(Material.GOLD_NUGGET, drop));
	    }
	    else if ((entity instanceof Squid))
	    {
	      Random generator = new Random();
	      int amount = generator.nextInt(GoldeconDrops.squid + 1) + 1;
	      int drop = amount - 1;

	      if (drop != 0)
	        event.getDrops().add(new ItemStack(Material.GOLD_NUGGET, drop));
	    }
	    else if ((entity instanceof Villager))
	    {
	      Random generator = new Random();
	      int amount = generator.nextInt(GoldeconDrops.villager + 1) + 1;
	      int drop = amount - 1;

	      if (drop != 0)
	        event.getDrops().add(new ItemStack(Material.GOLD_NUGGET, drop));
	    }
	    else if ((entity instanceof Wolf))
	    {
	      Random generator = new Random();
	      int amount = generator.nextInt(GoldeconDrops.wolf + 1) + 1;
	      int drop = amount - 1;

	      if (drop != 0)
	        event.getDrops().add(new ItemStack(Material.GOLD_NUGGET, drop));
	    }
	  }
}
