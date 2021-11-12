package me.smaks6.plugin.commands;


import me.smaks6.plugin.Main;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class NokautCommand implements CommandExecutor{

	public NokautCommand(Main main) {
		Main.getInstance().getCommand("nokaut").setExecutor(this);
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(cmd.getName().equalsIgnoreCase("nokaut")) {
			Player p = (Player) sender;
			if(args.length > 0) {
				if(args[0].equalsIgnoreCase("reload")){
					Main.getInstance().reloadConfig();
					if(Main.getInstance().getConfig().getString("messages.pluginReloaded") != null)  {
						p.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.getInstance().getConfig().getString("messages.pluginReloaded")));
					} else {
						p.sendMessage("no_message_error (pluginReloaded)");
					}
				}else{
					if(Main.getInstance().getConfig().getString("messages.adminCommandNoArgs") != null)  {
						p.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.getInstance().getConfig().getString("messages.adminCommandNoArgs")));
					} else {
						p.sendMessage("no_message_error (adminCommandNoArgs)");
					}
					return false;
				}
			}else {
				if(Main.getInstance().getConfig().getString("messages.adminCommandNoArgs") != null)  {
					p.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.getInstance().getConfig().getString("messages.adminCommandNoArgs")));
				} else {
					p.sendMessage("no_message_error (adminCommandNoArgs)");
				}
			}
			
		}
		return false;
	}
}
