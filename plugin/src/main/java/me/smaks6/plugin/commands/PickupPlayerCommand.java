package me.smaks6.plugin.commands;

import me.smaks6.plugin.utilities.Enum.Nokaut;
import me.smaks6.plugin.utilities.PlayerUtilities;
import me.smaks6.plugin.Main;
import me.smaks6.plugin.pose.Pose;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PickupPlayerCommand implements CommandExecutor {


    public PickupPlayerCommand(Main main) {
        main.getInstance().getCommand("pickuupplayer").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(!(commandSender instanceof Player)){
            System.out.println("You are console!");
        }

        Player sender = (Player) commandSender;

        if(!PlayerUtilities.isNull(sender) || !sender.getPassengers().isEmpty()){
            return false;
        }

        Entity[] entities = sender.getNearbyEntities(1, 3, 1).toArray(new Entity[0]);


        for(Entity e : entities){
            if(e instanceof Player){
                Player knockedPlayer = (Player) e;

                if(PlayerUtilities.getState(knockedPlayer).equals(Nokaut.LAY)) {
                    PlayerUtilities.setState(knockedPlayer, Nokaut.CARRY);
                    Pose.changeGameMode(knockedPlayer, sender, true);
                    sender.addPassenger(knockedPlayer);
                    knockedPlayer.setInvulnerable(true);
                    if(Main.getInstance().getConfig().getString("messages.pickupPlayer") != null)  {
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.getInstance().getConfig().getString("messages.pickupPlayer")));
                    } else {
                        sender.sendMessage("no_message_error (pickUpPlayer)");
                    }
                    sender.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, Integer.MAX_VALUE, 1));
                    break;
                }
            }
        }

        return false;
    }
}
