package me.smaks6.plugin.utilities;

import me.smaks6.plugin.utilities.Enum.Nokaut;
import me.smaks6.plugin.Main;
import me.smaks6.plugin.pose.Pose;
import me.smaks6.plugin.service.CitizensListener;
import me.smaks6.plugin.service.WorldGuardFlag;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.List;

public class NokautUtilities {

    public static boolean doNokaut(Entity e, double dmm) {
        if (CitizensListener.isNpc(e)) {
            return false;
        }
        if (e instanceof Player) {
            Player p = (Player) e;

            if(p.getInventory().getItemInMainHand().getType().equals(Material.TOTEM_OF_UNDYING) ||
                    p.getInventory().getItemInOffHand().getType().equals(Material.TOTEM_OF_UNDYING))return false;

            if(Bukkit.getServer().getPluginManager().getPlugin("WorldGuard") != null) {
                if (WorldGuardFlag.isFlag(p)) return false;
            }

            int hp = (int) p.getHealth();
            int dm = (int) dmm;

            if (!PlayerUtilities.isNull(p)) {
                return false;
            }

            if (hp <= dm) {

                if(!p.getPassengers().isEmpty()){
                    Player znokautowany = (Player) p.getPassengers().get(0);
                    PlayerUtilities.setEnum(znokautowany, Nokaut.LAY);
                    p.getPassengers().clear();
                    Pose.changegamemode(znokautowany, p, false);
                }

                p.setFireTicks(0);
                p.setHealth(2);

                Pose.start(p);

                if (Main.getInstance().getConfig().getBoolean("BlindnessOnNokaut")) {
                    p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 1000000, 100));
                }

                p.removePotionEffect(PotionEffectType.SLOW);

                if (p.getHealth() <= 10.0) {
                    p.setHealth(10.0);
                }
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.getInstance().getConfig().getString("messages.helpNokautMessage")));
                Runnables.nokautTimer(p);

                List<Entity> nearbyEntities = p.getNearbyEntities(50, 50, 50);
                for (Entity nearbyEntity : nearbyEntities) {
                    if(nearbyEntity instanceof Mob){
                        Mob mob = (Mob) nearbyEntity;
                        if(mob.getTarget() == null)continue;
                        if(mob.getTarget().getUniqueId().equals(p.getUniqueId()))mob.setTarget(null);
                    }
                }

                return true;
            }
        }
        return false;
    }

    public static Entity getLastDamager(Player player){
        EntityDamageEvent lastDamageCause = player.getLastDamageCause();
        if(lastDamageCause instanceof EntityDamageByEntityEvent){
            EntityDamageByEntityEvent damageByEntityEvent = (EntityDamageByEntityEvent) lastDamageCause;
            return damageByEntityEvent.getDamager();
        }
        return null;
    }
}
