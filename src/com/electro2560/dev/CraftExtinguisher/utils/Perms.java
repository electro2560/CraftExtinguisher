package com.electro2560.dev.CraftExtinguisher.utils;

import org.bukkit.permissions.Permission;

public class Perms {
	
	//Access to administrator commands
	public static Permission isAdmin = new Permission("craftextinguisher.admin");
	
	//Access to use the craft extinguisher 
	public static Permission canUse = new Permission("craftextinguisher.use");
	
	//Can player check for updates
	public static Permission canCheckForUpdates = new Permission("craftextinguisher.updates");
	
}
