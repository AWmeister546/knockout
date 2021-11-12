package me.smaks6.plugin.commands;

import me.smaks6.plugin.utilities.PlayerUtilities;
import me.smaks6.plugin.Main;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

public class DeathNowCommand implements CommandExecutor{

	public DeathNowCommand(Main main) {
		Main.getInstance().getCommand("zginodrazu").setExecutor(this);
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
			Player p = (Player) sender;
			if(!PlayerUtilities.isNull(p)) {
				EntityDamageEvent lastDamageCause = p.getLastDamageCause();
				if(lastDamageCause instanceof EntityDamageByEntityEvent){
					EntityDamageByEntityEvent damageByEntityEvent = (EntityDamageByEntityEvent) lastDamageCause;
					Entity damager = damageByEntityEvent.getDamager();
					p.damage(300, damager);
				}else {
					p.setHealth(0);
				}

				PlayerUtilities.unSet(p);
			}else {
				if(Main.getInstance().getConfig().getString("messages.deathNowNot") != null)  {
					p.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.getInstance().getConfig().getString("messages.deathNowNot")));
				} else {
					p.sendMessage("no_message_error (deathNowNot)");
				}
			}
		return false;
	}
}
