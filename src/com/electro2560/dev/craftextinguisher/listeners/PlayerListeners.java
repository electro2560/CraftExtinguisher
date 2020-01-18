package com.electro2560.dev.craftextinguisher.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;

import com.electro2560.dev.craftextinguisher.CraftExtinguisher;
import com.electro2560.dev.craftextinguisher.events.CraftExtinguishUseEvent;
import com.electro2560.dev.craftextinguisher.utils.Perms;
import com.electro2560.dev.craftextinguisher.utils.Utils;

public class PlayerListeners implements Listener{
	
	private CraftExtinguisher ce = CraftExtinguisher.get();
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void interact(PlayerInteractEvent event){
		final Player player = event.getPlayer();
		
		if(!player.hasPermission(Perms.canUse)){
			player.sendMessage(Utils.color("&cYou can't use that item."));
			return;
		}
		
		if(ce.blockedWorlds.contains(player.getWorld().getName())){
			player.sendMessage(Utils.color("&cYou can't use that in this world!"));
			return;
		}
		
		if(!ce.cooldowns.containsKey(player.getName())) ce.cooldowns.put(player.getName(), false);
		
		if(ce.cooldowns.get(player.getName())){
			player.sendMessage(Utils.color("&cYou must wait longer before you can use this again!"));
			event.setCancelled(true);
			return;
		}
		
		if(event.getAction() != Action.RIGHT_CLICK_AIR && event.getAction() != Action.RIGHT_CLICK_BLOCK) return;
		
		if(Utils.itemEquals(player.getItemInHand(), ce.item)){
			
			CraftExtinguishUseEvent useEvent = new CraftExtinguishUseEvent(player);
			Bukkit.getPluginManager().callEvent(useEvent);
			if(useEvent.isCancelled()) return;
			
			final Block b = Utils.getTargetBlock(player);
			if(b == null || b.getType() == Material.AIR) return;
			
			//Cooldown
			ce.cooldowns.put(player.getName(), true);
			new BukkitRunnable() {
				@Override
				public void run() {
					ce.cooldowns.put(player.getName(), false);
				}
			}.runTaskLater(CraftExtinguisher.get(), ce.cooldownDelay);
			
			//Set target block to water
			final Material type = b.getType();
			final byte data = b.getData();
			b.setType(Material.WATER);
			
			new BukkitRunnable() {
				@Override
				public void run() {
					b.setType(type);
					b.setData(data);
				}
			}.runTaskLater(CraftExtinguisher.get(), ce.blockRegenDelay);
		}
		
	}
}
