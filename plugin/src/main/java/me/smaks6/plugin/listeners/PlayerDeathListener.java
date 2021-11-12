package me.smaks6.plugin.listeners;

import me.smaks6.plugin.pose.Pose;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDeathListener implements Listener {
    @EventHandler
    public void smierc(PlayerDeathEvent event) {
        Player p = event.getEntity();

        Pose.stop(p);
    }
}
