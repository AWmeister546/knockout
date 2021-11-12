package me.smaks6.plugin.commands;

import me.smaks6.plugin.Main;
import me.smaks6.plugin.utilities.Enum.Nokaut;
import me.smaks6.plugin.utilities.PlayerUtilities;
import me.smaks6.plugin.utilities.Runnables;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ForceReciveCommand implements CommandExecutor {

    Main plugin;

    public ForceReciveCommand(Main m) {
        plugin = m;

        m.getCommand("forcerecive").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] args) {
        Player player = (Player) sender;

        if(player.isOp() || player.hasPermission("nokaut.admin")) {
            if(args.length == 1) {
                Player target = Bukkit.getPlayer(args[0]);

                if(target != null) {
                    if(!PlayerUtilities.isNull(target)) {
                        if(PlayerUtilities.getEnum(target).equals(Nokaut.LAY)) {
                            Runnables.revivePlayerCmd(player, target);
                        } else {
                            if(Main.getInstance().getConfig().getString("messages.playerNotKnockedOut") != null)  {
                                player.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.getInstance().getConfig().getString("messages.playerNotKnockedOut")));
                            } else {
                                player.sendMessage("no_message_error (playerNotKnockedOut)");
                            }
                        }
                    } else {
                        if(Main.getInstance().getConfig().getString("messages.playerNotKnockedOut") != null)  {
                            player.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.getInstance().getConfig().getString("messages.playerNotKnockedOut")));
                        } else {
                            player.sendMessage("no_message_error (playerNotKnockedOut)");
                        }
                    }
                } else {
                    if(Main.getInstance().getConfig().getString("messages.playerNotFound") != null)  {
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.getInstance().getConfig().getString("messages.playerNotFound")));
                    } else {
                        player.sendMessage("no_message_error (playerNotFound)");
                    }
                }
            } else {
                if(Main.getInstance().getConfig().getString("messages.noPlayerGiven") != null)  {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.getInstance().getConfig().getString("messages.noPlayerGiven")));
                } else {
                    player.sendMessage("no_message_error (noPlayerGiven)");
                }
            }
        } else {
            if(Main.getInstance().getConfig().getString("messages.noPermission") != null)  {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.getInstance().getConfig().getString("messages.noPermission")));
            } else {
                player.sendMessage("no_message_error (noPermission)");
            }
        }

        return false;
    }
}
