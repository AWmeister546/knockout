package me.smaks6.plugin.listeners;

import me.smaks6.plugin.utilities.PlayerUtilities;
import me.smaks6.plugin.Main;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class CommandListener implements Listener {

    @EventHandler(priority = EventPriority.LOW)
    public void commandblock(PlayerCommandPreprocessEvent event) {
        Player p = event.getPlayer();
        if(!PlayerUtilities.isNull(p)) {
            if (!event.getMessage().toLowerCase().startsWith("/zo") && !event.getMessage().toLowerCase().startsWith("/zginodrazu") && !event.getMessage().toLowerCase().startsWith("/deathnow") && !event.getMessage().toLowerCase().startsWith("/dn") && !event.getMessage().toLowerCase().startsWith("/harakiri") && !event.getMessage().toLowerCase().startsWith("/samobojstwo") && !event.getMessage().toLowerCase().startsWith("/suicide")) {
                event.setCancelled(true);
                event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', Main.getInstance().getConfig().getString("messages.cancelMessage")));
            }
        }
    }
}
