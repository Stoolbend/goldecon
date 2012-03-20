package org.goldecon.drops;

import org.goldecon.goldeconplus.Goldecon;

public class GoldeconDrops {
	
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
	
	private Goldecon ge;
	
	public GoldeconDrops(Goldecon goldecon){
		this.ge = goldecon;
	}
	
	public void initDrops(){
	    // Get drops values from config.
	    creeper = ge.cfgGetInt("Bad Mobs.Creeper", 5);
	    zombie = ge.cfgGetInt("Bad Mobs.Zombie", 2);
	    skeleton = ge.cfgGetInt("Bad Mobs.Skeleton", 4);
	    ghast = ge.cfgGetInt("Bad Mobs.Ghast", 10);
	    enderman = ge.cfgGetInt("Bad Mobs.Endermen", 7);
	    blaze = ge.cfgGetInt("Bad Mobs.Blaze", 4);
	    pigman = ge.cfgGetInt("Bad Mobs.Pigmen", 1);
	    enderdragon = ge.cfgGetInt("Bad Mobs.Ender Dragon", 50);
	    cavespider = ge.cfgGetInt("Bad Mobs.Cave Spider", 8);
	    spider = ge.cfgGetInt("Bad Mobs.Spider", 5);
	    silverfish = ge.cfgGetInt("Bad Mobs.Silverfish", 3);
	    slime = ge.cfgGetInt("Bad Mobs.Slime", 15);
	    cow = ge.cfgGetInt("Good Mobs.Cow", 2);
	    chicken = ge.cfgGetInt("Good Mobs.Chicken", 2);
	    pig = ge.cfgGetInt("Good Mobs.Pig", 2);
	    sheep = ge.cfgGetInt("Good Mobs.Sheep", 2);
	    mushroomcow = ge.cfgGetInt("Good Mobs.Mushroom Cow", 5);
	    villager = ge.cfgGetInt("Good Mobs.Villager", 8);
	    squid = ge.cfgGetInt("Good Mobs.Squid", 1);
	    wolf = ge.cfgGetInt("Good Mobs.Wolf", 5);
	    snowman = ge.cfgGetInt("Good Mobs.Snowman", 0);
		ge.saveConfig();
	}
}
