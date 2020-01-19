package com.electro2560.dev.craftextinguisher.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * @author Mitchell Sulkowski
 * @github https://github.com/electro2560
 * @website https://dev.electro2560.com/
 * @since Jan 18, 2020
 */
public class CraftExtinguishUseEvent extends Event implements Cancellable{

	private boolean cancelled = false;
	
	Player player;
	
	public CraftExtinguishUseEvent(Player player) {
		this.player = player;
	}
	
	public Player getPlayer() {
		return player;
	}
	
	@Override
	public boolean isCancelled() {
		return cancelled;
	}

	@Override
	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
	}

	private static final HandlerList handlers = new HandlerList();
	
	@Override
	public HandlerList getHandlers() {
		return handlers;
	}
	
	public static HandlerList getHandlerList() {
	    return handlers;
	}

}
