package me.smaks6.plugin.listeners;

import me.smaks6.plugin.utilities.PlayerUtilities;
import me.smaks6.plugin.Main;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class InteractListener implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void chestitd(PlayerInteractEvent event) {
        Player p = event.getPlayer();
        if(!PlayerUtilities.isNull(p)) {
            event.setCancelled(true);
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.getInstance().getConfig().getString("messages.cancelMessage")));
        }
    }
}
