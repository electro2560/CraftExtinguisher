package com.electro2560.dev.craftextinguisher.commands;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.electro2560.dev.craftextinguisher.CraftExtinguisher;
import com.electro2560.dev.craftextinguisher.utils.Perms;
import com.electro2560.dev.craftextinguisher.utils.Utils;

public class CraftExtinguishCommand implements CommandExecutor{

	private final String helpMsg = Utils.color("&c&l-- Craft Extinguisher v" + CraftExtinguisher.get().getDescription().getVersion() + " --\n"
			+ "&c/ce setitem &7- Set the extinguisher item"									 
			+ "&c/ce reload &7- Reload the config");
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(!(sender instanceof Player)){
			sender.sendMessage(Utils.color("&cYou must be a player to use this command."));
			return true;
		}
		
		if(!sender.hasPermission(Perms.admin)){
			sender.sendMessage(Utils.color("&cNo permission."));
			return true;
		}
		
		Player player = (Player) sender;
				
		if(args.length < 1){
			player.sendMessage(helpMsg);
			return true;
		}
			
					
		switch (args[0].toLowerCase()) {
		case "setitem":
			@SuppressWarnings("deprecation") ItemStack item = player.getItemInHand();

			if(item == null || item.getType() == Material.AIR){
				player.sendMessage(Utils.color("&cError: You must be holding an item!"));
				break;
			}

			CraftExtinguisher.get().item = item.clone();

			player.sendMessage(Utils.color("&cYou have set the extinguisher item."));
			break;
		case "reload":
			CraftExtinguisher.get().reload(false);
		
			player.sendMessage(Utils.color("&aSuccessfully reloaded config.yml."));
			break;
		default:
			player.sendMessage(helpMsg);
			break;
		}
		
		return true;
	}

}
