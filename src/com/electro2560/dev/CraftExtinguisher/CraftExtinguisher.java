package com.electro2560.dev.CraftExtinguisher;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.electro2560.dev.CraftExtinguisher.commands.CraftExtinguishCommand;
import com.electro2560.dev.CraftExtinguisher.listeners.PlayerListener;
import com.electro2560.dev.CraftExtinguisher.updater.UpdateListener;
import com.electro2560.dev.CraftExtinguisher.utils.Utils;

public class CraftExtinguisher extends JavaPlugin{

	private PluginManager pm = Bukkit.getServer().getPluginManager();
	private static CraftExtinguisher instance;
	
	private static int blockRegenDelay;
	private static ArrayList<String> bannedWorlds = new ArrayList<String>();
	private static ItemStack item;
	private static int coolDownDelay;
	private static HashMap<String, Boolean> cooldowns = new HashMap<String, Boolean>();
	
	public void onEnable(){
		instance = this;
		
		Utils.defaultConfig();
		
		blockRegenDelay = getConfig().getInt("blockRegenDelay", 60);
		coolDownDelay = getConfig().getInt("coolDownDelay", 10);
		
		item = getConfig().getItemStack("Item", new ItemStack(Material.SNOW_BALL, 1));
		
		if(getConfig().contains("bannedWorlds")){
			bannedWorlds = (ArrayList<String>) getConfig().getStringList("bannedWorlds");
			if(bannedWorlds == null) bannedWorlds = new ArrayList<String>();
		}
		
		pm.registerEvents(new PlayerListener(), instance);
		pm.registerEvents(new UpdateListener(instance), instance);
		
		getCommand("craftextinguish").setExecutor(new CraftExtinguishCommand());
		
		if(getConfig().getBoolean("useMetrics", true)) Utils.startMetrics();
		
	}
	
	public void onDisable(){
		getConfig().set("blockRegenDelay", blockRegenDelay);
		getConfig().set("Item", item);
		getConfig().set("bannedWorlds", bannedWorlds);
		
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
		return bannedWorlds;
	}

	public static ItemStack getItem() {
		return item;
	}

	public static void setItem(ItemStack item) {
		CraftExtinguisher.item = item;
	}

	public static int getCoolDownDelay() {
		return coolDownDelay;
	}

	public static void setCoolDownDelay(int coolDownDelay) {
		CraftExtinguisher.coolDownDelay = coolDownDelay;
	}

	public static HashMap<String, Boolean> getCooldowns() {
		return cooldowns;
	}

	public static void setCooldowns(HashMap<String, Boolean> cooldowns) {
		CraftExtinguisher.cooldowns = cooldowns;
	}

}
