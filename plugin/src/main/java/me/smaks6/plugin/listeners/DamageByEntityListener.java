package me.smaks6.plugin.listeners;

import me.smaks6.plugin.Main;
import me.smaks6.plugin.utilities.NokautUtilities;
import me.smaks6.plugin.utilities.PlayerUtilities;
import me.smaks6.plugin.service.CitizensListener;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class DamageByEntityListener implements Listener {

    @EventHandler(priority = EventPriority.HIGH)
    public void stopdamage(EntityDamageByEntityEvent event){

        if(CitizensListener.isNpc(event.getDamager())) {
            return;
        }

        if(event.getDamager() instanceof Player && event.getDamager() != event.getEntity()) {
            if(event.getEntity() instanceof Player) {
                Player p = (Player) event.getDamager();
                if (NokautUtilities.doNokaut(event.getEntity(), event.getFinalDamage())) {
                    p.sendTitle(" ", ChatColor.translateAlternateColorCodes('&', Main.getInstance().getConfig().getString("messages.killerTitle").replace("%player%", ((Player) event.getEntity()).getDisplayName())));
                    for(Player ps : Bukkit.getOnlinePlayers()) {
                        ps.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.getInstance().getConfig().getString("messages.knockoutBroadcast").replace("%player%", p.getDisplayName()).replace("%knocked%", ((Player) event.getEntity()).getDisplayName())));
                    }
                    event.setCancelled(true);
                }
            }
        }

        if(event.getDamager() instanceof Player){
            if(!PlayerUtilities.isNull((Player) event.getDamager())){
                event.setCancelled(true);
            }
        }
    }
}
