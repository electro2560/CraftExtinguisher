package com.electro2560.dev.CraftExtinguisher.listeners;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;

import com.electro2560.dev.CraftExtinguisher.CraftExtinguisher;
import com.electro2560.dev.CraftExtinguisher.utils.Perms;
import com.electro2560.dev.CraftExtinguisher.utils.Utils;

import net.md_5.bungee.api.ChatColor;

public class PlayerListener implements Listener{

	@SuppressWarnings("deprecation")
	@EventHandler
	public void interact(PlayerInteractEvent event){
		final Player p = event.getPlayer();
		
		if(!p.hasPermission(Perms.canUse)){
			p.sendMessage(ChatColor.RED + "No permission!");
			return;
		}
		
		if(CraftExtinguisher.getBannedWorlds().contains(p.getWorld().getName())){
			p.sendMessage(ChatColor.RED + "You can't use that in this world!");
			return;
		}
		
		if(!CraftExtinguisher.getCooldowns().containsKey(p.getName())) CraftExtinguisher.getCooldowns().put(p.getName(), false);
		
		if(CraftExtinguisher.getCooldowns().get(p.getName())){
			p.sendMessage(ChatColor.RED + "You must wait longer before you can use this again!");
			event.setCancelled(true);
			return;
		}
		if(event.getAction() != Action.RIGHT_CLICK_AIR && event.getAction() != Action.RIGHT_CLICK_BLOCK) return;
		
		if(Utils.itemEquals(p.getItemInHand(), CraftExtinguisher.getItem())){
			
			final Block b = Utils.getTargetBlock(p);
			if(b == null || b.getType() == Material.AIR) return;
			
			//Cool down
			CraftExtinguisher.getCooldowns().put(p.getName(), true);
			new BukkitRunnable() {
				@Override
				public void run() {
					CraftExtinguisher.getCooldowns().put(p.getName(), false);
				}
			}.runTaskLater(CraftExtinguisher.get(), CraftExtinguisher.getCoolDownDelay());
			
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
			}.runTaskLater(CraftExtinguisher.get(), CraftExtinguisher.getBlockRegenDelay());
		}
		
	}
}
