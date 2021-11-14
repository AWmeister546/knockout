package me.smaks6.plugin.listeners;

import me.smaks6.plugin.Main;
import me.smaks6.plugin.utilities.Enum.Nokaut;
import me.smaks6.plugin.utilities.PlayerUtilities;
import me.smaks6.plugin.service.CitizensListener;
import me.smaks6.plugin.utilities.Runnables;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSneakEvent;

import java.util.List;

public class SneakListener implements Listener {

    @EventHandler
    public void sneakEvent(PlayerToggleSneakEvent e){
        Player p = e.getPlayer();


        if(!PlayerUtilities.isNull(p)){
            e.setCancelled(true);
            return;
        }
        List<Entity> players = p.getNearbyEntities(2,2,2);
        if(players.isEmpty()){
            return;
        }

        int requiredLevel = Main.getInstance().getConfig().getInt("levelRequired");

        for (Entity player : players) {
            if (CitizensListener.isNpc(player)) {
                return;
            }
            if(player instanceof Player){
                Player plist = (Player) player;
                if(!PlayerUtilities.isNull(plist)){
                    if(PlayerUtilities.getState(plist).equals(Nokaut.LAY)){
                        if(!(p.getExp() == 0) && p.getExp() > 0 || p.getLevel() >= requiredLevel) {
                            Runnables.revivePlayer(p, plist);
                            break;
                        } else {
                            p.sendTitle(ChatColor.translateAlternateColorCodes('&', Main.getInstance().getConfig().getString("messages.reanimationErrorTitle")), ChatColor.translateAlternateColorCodes('&', Main.getInstance().getConfig().getString("messages.reanimationErrorSubtitle")));
                        }
                    }
                }
            }
        }
        return;
    }
}
