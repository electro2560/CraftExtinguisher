package com.electro2560.dev.craftextinguisher.utils;

import java.util.HashSet;

import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.electro2560.dev.craftextinguisher.CraftExtinguisher;

public class Utils {

	@SuppressWarnings("deprecation")
	public static Block getTargetBlock(Player p){
		//Up to 100 blocks away
		return p.getTargetBlock((HashSet<Byte>) null, 100);
	}
	
	public static boolean itemEquals(ItemStack i1, ItemStack i2){
		if(i1.getType().equals(i2.getType()) && i1.getItemMeta().equals(i2.getItemMeta()) && i1.getEnchantments().equals(i2.getEnchantments()) && i1.getData().equals(i2.getData())) return true;
		return false;
	}
	
	public static boolean isCheckForUpdates(){
		return CraftExtinguisher.get().getConfig().getBoolean("checkForUpdates", true);
	}
	
	public static String getVersion(){
		return CraftExtinguisher.get().getVersion();
	}
	
	public static String color(String str) {
		return ChatColor.translateAlternateColorCodes('&', str);
	}
	
}
