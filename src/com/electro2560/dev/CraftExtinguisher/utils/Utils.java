package com.electro2560.dev.CraftExtinguisher.utils;

import java.io.IOException;
import java.util.HashSet;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.mcstats.MetricsLite;

import com.electro2560.dev.CraftExtinguisher.CraftExtinguisher;
import com.electro2560.dev.CraftExtinguisher.bstats.Metrics;

public class Utils {

	public static void defaultConfig(){
		FileConfiguration c = CraftExtinguisher.get().getConfig();
		
		c.addDefault("checkForUpdates", true);
		c.addDefault("useMetrics", true);
		c.addDefault("blockRegenDelay", 60);
		c.addDefault("coolDownDelay", 10);
		c.addDefault("bannedWorlds", "");
		c.addDefault("Item", new ItemStack(Material.SNOW_BALL, 1));
		
		c.options().copyDefaults(true);
		
		CraftExtinguisher.get().saveConfig();
	}

	@SuppressWarnings("deprecation")
	public static Block getTargetBlock(Player p){
		//Up to 100 blocks away
		return p.getTargetBlock((HashSet<Byte>) null, 100);
	}
	
	public static boolean itemEquals(ItemStack i, ItemStack i2){
		if(i.getType().equals(i2.getType()) && i.getItemMeta().equals(i2.getItemMeta()) && i.getEnchantments().equals(i2.getEnchantments()) && i.getData().equals(i2.getData())) return true;
		return false;
	}
	
	public static boolean isCheckForUpdates(){
		return CraftExtinguisher.get().getConfig().getBoolean("checkForUpdates", true);
	}
	
	public static String getVersion(){
		return CraftExtinguisher.get().getVersion();
	}
	
	public static void startMetrics(){
		try {
	        MetricsLite metrics = new MetricsLite(CraftExtinguisher.get());
	        metrics.start();
	    } catch (IOException e) {}
		
		@SuppressWarnings("unused")
		Metrics bstats = new Metrics(CraftExtinguisher.get());
	}
	
}
