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
	
	public int blockRegenDelay;
	public ArrayList<String> blockedWorlds = new ArrayList<String>();
	public ItemStack item;
	public int cooldownDelay;
	public HashMap<String, Boolean> cooldowns = new HashMap<String, Boolean>();
	
	public void onEnable(){
		
		instance = this;
		
		if(!new File(getDataFolder(), "config.yml").exists()) saveDefaultConfig();
		
		reload(true);
		
		pm.registerEvents(new PlayerListeners(), instance);
		pm.registerEvents(new UpdateListener(instance), instance);
		
		getCommand("craftextinguish").setExecutor(new CraftExtinguishCommand());
		
	}
	
	public void onDisable(){
		reload(false);
		
		getConfig().set("item", item);
		saveConfig();
		
		instance = null;
	}

	public void reload(boolean reloadItem) {
		reloadConfig();
		
		blockRegenDelay = getConfig().getInt("blockRegenDelay", 60);
		cooldownDelay = getConfig().getInt("cooldownDelay", 10);
		
		if(reloadItem) item = getConfig().getItemStack("item", new ItemStack(Material.SNOW_BALL, 1));
		
		if(getConfig().contains("blockedWorlds")){
			blockedWorlds = (ArrayList<String>) getConfig().getStringList("blockedWorlds");
			if(blockedWorlds == null) blockedWorlds = new ArrayList<String>();
		}
		
	}
	
	public static CraftExtinguisher get() {
		return instance;
	}

}
