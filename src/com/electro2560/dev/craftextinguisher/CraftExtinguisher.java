package com.electro2560.dev.craftextinguisher;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.electro2560.dev.craftextinguisher.commands.CraftExtinguishCommand;
import com.electro2560.dev.craftextinguisher.listeners.PlayerListeners;
import com.electro2560.dev.craftextinguisher.updater.UpdateListener;

public class CraftExtinguisher extends JavaPlugin{

	PluginManager pm = Bukkit.getServer().getPluginManager();
	
	private static CraftExtinguisher instance;
	
	static int blockRegenDelay;
	static ArrayList<String> blockedWorlds = new ArrayList<String>();
	static ItemStack item;
	static int cooldownDelay;
	static HashMap<String, Boolean> cooldowns = new HashMap<String, Boolean>();
	
	public void onEnable(){
		instance = this;
		
		if(!new File(getDataFolder(), "config.yml").exists()) saveDefaultConfig();
		
		blockRegenDelay = getConfig().getInt("blockRegenDelay", 60);
		cooldownDelay = getConfig().getInt("cooldownDelay", 10);
		
		item = getConfig().getItemStack("Item", new ItemStack(Material.SNOW_BALL, 1));
		
		if(getConfig().contains("blockedWorlds")){
			blockedWorlds = (ArrayList<String>) getConfig().getStringList("blockedWorlds");
			if(blockedWorlds == null) blockedWorlds = new ArrayList<String>();
		}
		
		pm.registerEvents(new PlayerListeners(), instance);
		pm.registerEvents(new UpdateListener(instance), instance);
		
		getCommand("craftextinguish").setExecutor(new CraftExtinguishCommand());
		
	}
	
	public void onDisable(){
		getConfig().set("blockRegenDelay", blockRegenDelay);
		getConfig().set("Item", item);
		getConfig().set("blockedWorlds", blockedWorlds);
		
		saveConfig();
		
		instance = null;
	}

	public static CraftExtinguisher get() {
		return instance;
	}

	public String getVersion() {
		return this.getDescription().getVersion();
	}
	
	public static int getBlockRegenDelay() {
		return blockRegenDelay;
	}

	public static void setBlockRegenDelay(int blockRegenDelay) {
		CraftExtinguisher.blockRegenDelay = blockRegenDelay;
	}

	public static ArrayList<String> getBannedWorlds() {
		return blockedWorlds;
	}

	public static ItemStack getItem() {
		return item;
	}

	public static void setItem(ItemStack item) {
		CraftExtinguisher.item = item;
	}

	public static int getCoolDownDelay() {
		return cooldownDelay;
	}

	public static void setCoolDownDelay(int coolDownDelay) {
		CraftExtinguisher.cooldownDelay = coolDownDelay;
	}

	public static HashMap<String, Boolean> getCooldowns() {
		return cooldowns;
	}

	public static void setCooldowns(HashMap<String, Boolean> cooldowns) {
		CraftExtinguisher.cooldowns = cooldowns;
	}

}
