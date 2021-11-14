package me.smaks6.plugin.commands;

import me.smaks6.plugin.utilities.Enum.Nokaut;
import me.smaks6.plugin.utilities.PlayerUtilities;
import me.smaks6.plugin.Main;
import me.smaks6.plugin.pose.Pose;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

public class DropPlayerCommand implements CommandExecutor {

    public DropPlayerCommand(Main main) {
        Main.getInstance().getCommand("dropplayer").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(!(commandSender instanceof Player)){
            System.out.println("You are console!");
        }
        Player sender = (Player) commandSender;

        if(sender.getPassengers().isEmpty()){
            //nic nie masz
            return false;
        }

        Player player = (Player) sender.getPassengers().get(0);
        sender.getPassengers().clear();
        PlayerUtilities.setState(player, Nokaut.LAY);
        Pose.changeGameMode(player, sender, false);
        player.setInvulnerable(false);
        player.teleport(sender);
        if(Main.getInstance().getConfig().getString("messages.dropPlayer") != null)  {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.getInstance().getConfig().getString("messages.dropPlayer")));
        } else {
            sender.sendMessage("no_message_error (dropPlayer)");
        }
        sender.removePotionEffect(PotionEffectType.SLOW);
        return false;
    }
}
