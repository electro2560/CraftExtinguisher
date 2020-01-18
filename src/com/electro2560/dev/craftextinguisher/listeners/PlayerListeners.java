package com.electro2560.dev.craftextinguisher.listeners;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;

import com.electro2560.dev.craftextinguisher.CraftExtinguisher;
import com.electro2560.dev.craftextinguisher.utils.Perms;
import com.electro2560.dev.craftextinguisher.utils.Utils;

public class PlayerListeners implements Listener{
	
	CraftExtinguisher ce = CraftExtinguisher.get();
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void interact(PlayerInteractEvent event){
		final Player p = event.getPlayer();
		
		if(!p.hasPermission(Perms.canUse)){
			p.sendMessage(Utils.color("&cYou can't use that item."));
			return;
		}
		
		if(ce.blockedWorlds.contains(p.getWorld().getName())){
			p.sendMessage(Utils.color("&cYou can't use that in this world!"));
			return;
		}
		
		if(!ce.cooldowns.containsKey(p.getName())) ce.cooldowns.put(p.getName(), false);
		
		if(ce.cooldowns.get(p.getName())){
			p.sendMessage(Utils.color("&cYou must wait longer before you can use this again!"));
			event.setCancelled(true);
			return;
		}
		if(event.getAction() != Action.RIGHT_CLICK_AIR && event.getAction() != Action.RIGHT_CLICK_BLOCK) return;
		
		if(Utils.itemEquals(p.getItemInHand(), ce.item)){
			
			final Block b = Utils.getTargetBlock(p);
			if(b == null || b.getType() == Material.AIR) return;
			
			//Cooldown
			ce.cooldowns.put(p.getName(), true);
			new BukkitRunnable() {
				@Override
				public void run() {
					ce.cooldowns.put(p.getName(), false);
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
