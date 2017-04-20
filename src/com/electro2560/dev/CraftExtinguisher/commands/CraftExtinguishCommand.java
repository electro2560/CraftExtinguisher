package com.electro2560.dev.CraftExtinguisher.commands;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.electro2560.dev.CraftExtinguisher.CraftExtinguisher;
import com.electro2560.dev.CraftExtinguisher.utils.Perms;

import net.md_5.bungee.api.ChatColor;

public class CraftExtinguishCommand implements CommandExecutor{

	private final String helpMsg = ChatColor.RED + "-- Craft Extinguisher v" + CraftExtinguisher.get().getDescription().getVersion() + " --\n"
												 + "/ce setitem - Sets the item of the extinguisher\n"
												 + "/ce setblockdelay - Sets the time in ticks of how long before a block regenerates\n"
												 + "/ce setcooldown - Sets how long in ticks the player must wait between uses\n"
												 + "/ce addbannedworld - Ban a world by name that the extinguisher will not work in\n"
												 + "/ce listbannedworlds - List all banned worlds\n"
												 + "/ce removebannedworld - Removed a banned world";
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		if(!sender.hasPermission(Perms.isAdmin)){
			sender.sendMessage(ChatColor.RED + "No permission.");
			return true;
		}
			
		if(!(sender instanceof Player)){
			sender.sendMessage(ChatColor.RED + "Console is not supported!");
			return true;
		}

		Player player = (Player) sender;
				
		if(args.length < 1){
			player.sendMessage(helpMsg);
			return true;
		}
			
					
		switch (args[0].toLowerCase()) {
		case "setitem":
			@SuppressWarnings("deprecation") ItemStack i = player.getItemInHand();
			
			if(i == null || i.getType() == Material.AIR){
				player.sendMessage(ChatColor.RED + "Error: You must be holding an item!");
				break;
			}
			
			CraftExtinguisher.setItem(i);
			
			player.sendMessage(ChatColor.GREEN + "You have set the extinguisher to be the item in your hand!");
			break;
		case "setblockdelay":
			if(args.length < 2){
				player.sendMessage(ChatColor.RED + "Error: You must include a delay (in ticks)! Ex: \"/ce setblockdelay 60\" sets the delay to 60 ticks or 3 seconds.");
				break;
			}
			
			String delay = args[1];
			int d;
			
			try {
				d = Integer.parseInt(delay);
			} catch (Exception e) {
				player.sendMessage(ChatColor.RED + "Error: " + delay + " is not a valid number!");
				break;
			}
			
			CraftExtinguisher.setBlockRegenDelay(d);
			
			player.sendMessage(ChatColor.GREEN + "The block regen delay has been set to " + ChatColor.AQUA + delay + ChatColor.GREEN + " ticks!");
			break;
		case "setcooldown":
			if(args.length < 2){
				player.sendMessage(ChatColor.RED + "Error: You must include a delay (in ticks)! Ex: \"/ce setcooldown 60\" sets the cool down delay to 60 ticks or 3 seconds.");
				break;
			}
			
			String delaycd = args[1];
			int dcd;
			
			try {
				dcd = Integer.parseInt(delaycd);
			} catch (Exception e) {
				player.sendMessage(ChatColor.RED + "Error: " + delaycd + " is not a valid number!");
				break;
			}
			
			CraftExtinguisher.setCoolDownDelay(dcd);
			
			player.sendMessage(ChatColor.GREEN + "The cool down delay has been set to " + ChatColor.AQUA + delaycd + ChatColor.GREEN + " ticks!");
			break;
		case "addbannedworld":
			if(args.length < 2){
				player.sendMessage(ChatColor.RED + "Error: You must include a world name.");
				break;
			}
			
			String world = args[1];
			
			if(CraftExtinguisher.getBannedWorlds().contains(world)){
				player.sendMessage(ChatColor.AQUA + world + ChatColor.GREEN + " has already been added to the banned world list!");
				break;
			}else{
				CraftExtinguisher.getBannedWorlds().add(world);
				player.sendMessage(ChatColor.GREEN + "World " + ChatColor.AQUA + world + ChatColor.GREEN + " has been added to the banned worlds list!");
			}
			
			break;
		case "listbannedworlds":
			String list = "";
			
			for(String s : CraftExtinguisher.getBannedWorlds()) list += ChatColor.GREEN + s + ChatColor.AQUA + ", ";
			
			if(list.equals("")) list = ChatColor.RED + "None";
			
			player.sendMessage(list);
			break;
		case "removebannedworld":
			if(args.length < 2){
				player.sendMessage(ChatColor.RED + "Error: You must include a world name.");
				break;
			}
			
			world = args[1];
			
			if(!CraftExtinguisher.getBannedWorlds().contains(world)){
				player.sendMessage(ChatColor.RED + "That world does not exist in the banned worlds list!");
				break;
			}
			
			CraftExtinguisher.getBannedWorlds().remove(world);
			
			player.sendMessage(ChatColor.GREEN + "World " + ChatColor.AQUA + world + ChatColor.GREEN + " has been removed from the banned worlds list!");
			break;
		default:
			player.sendMessage(helpMsg);
			break;
		}
		
		return true;
	}

}
