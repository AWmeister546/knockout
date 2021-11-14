package me.smaks6.plugin.listeners;

import me.smaks6.plugin.utilities.Enum.Nokaut;
import me.smaks6.plugin.utilities.PlayerUtilities;
import me.smaks6.plugin.pose.Pose;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class QuitListener implements Listener {

    @EventHandler
    public void wychodzi(PlayerQuitEvent event) {
        Player p = event.getPlayer();

        if(!PlayerUtilities.isNull(p)) {
            p.setHealth(0);
        }

        if(PlayerUtilities.isNull(p))return;

        if(PlayerUtilities.getState(p).equals(Nokaut.CARRY)){
            PlayerUtilities.setState(p, Nokaut.LAY);
            Player vehicle = (Player) p.getVehicle();
            vehicle.getPassengers().clear();
            Pose.changeGameMode(p, null, false);
        }

        if(!p.getPassengers().isEmpty()){
            Player znokautowany = (Player) p.getPassengers().get(0);
            PlayerUtilities.setState(znokautowany, Nokaut.LAY);
            p.getPassengers().clear();
            Pose.changeGameMode(znokautowany, p, false);
        }

        PlayerUtilities.unSet(p);

    }
}
