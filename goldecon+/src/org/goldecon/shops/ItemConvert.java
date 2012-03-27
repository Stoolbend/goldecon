package org.goldecon.shops;

import org.bukkit.Material;

public class ItemConvert {

	public Material str2mat(String in)
	{
		// Set air incase it all fails.
		Material material = Material.AIR;
		
		// in ID order - TO BE DONE
		/*
		if(in.equalsIgnoreCase("0") || in.equalsIgnoreCase("AIR")) material = Material.AIR;
		if(in.equalsIgnoreCase("1") || in.equalsIgnoreCase("STONE")) material = Material.STONE;
		if(in.equalsIgnoreCase("2") || in.equalsIgnoreCase("GRASS")) material = Material.GRASS;
		if(in.equalsIgnoreCase("3") || in.equalsIgnoreCase("DIRT")) material = Material.DIRT;
		if(in.equalsIgnoreCase("4") || in.equalsIgnoreCase("COBBLESTONE")) material = Material.COBBLESTONE;
		if(in.equalsIgnoreCase("5") || in.equalsIgnoreCase("WOOD")) material = Material.WOOD;
		if(in.equalsIgnoreCase("6") || in.equalsIgnoreCase("SAPLING")) material = Material.SAPLING;
		*/
		
		// Set as a material.
		Material mat2 = Material.matchMaterial(in);
		
		// Check if material is valid.
		if(mat2 == null)
		{
			// Check has failed. return air.
			return material;
		}
		else
		{
			// Check has outputted a valid material. return it.
			return mat2;
		}
	}
	
}
