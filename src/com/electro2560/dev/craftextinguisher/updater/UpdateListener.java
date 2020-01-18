package com.electro2560.dev.craftextinguisher.updater;

import java.beans.ConstructorProperties;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import com.electro2560.dev.craftextinguisher.CraftExtinguisher;
import com.electro2560.dev.craftextinguisher.utils.Perms;
import com.electro2560.dev.craftextinguisher.utils.Utils;

public class UpdateListener implements Listener {
	private final CraftExtinguisher plugin;

	@ConstructorProperties({ "plugin" })
	public UpdateListener(CraftExtinguisher plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		if (e.getPlayer().hasPermission(Perms.canCheckForUpdates) && Utils.isCheckForUpdates()) {
			UpdateUtil.sendUpdateMessage(e.getPlayer(), plugin);
		}
	}
}
