package me.smaks6.plugin.listeners;

import me.smaks6.plugin.utilities.PlayerUtilities;
import me.smaks6.plugin.Main;
import me.smaks6.plugin.pose.Pose;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.ArrayList;
import java.util.List;

public class JoinListener implements Listener {

    @EventHandler
    public void joinListenear(PlayerJoinEvent event) {
        Player p = event.getPlayer();

        List<Player> npc = new ArrayList<>();

        for (Player player : Bukkit.getServer().getOnlinePlayers()) {
            if(!PlayerUtilities.isNull(player)){
                npc.add(player);
            }
        }

        for (Player znokautowany : npc) {
            Pose.createNPC(znokautowany, p);
            p.hidePlayer(Main.getInstance(), znokautowany);
        }

//        if(p.isOp()){
//            new updatechecker(85152).getVersion(version -> {
//                if(!Main.getInstance().getDescription().getVersion().equalsIgnoreCase(version)){
//                    p.sendMessage(ChatColor.GOLD + "=======================================");
//                    p.sendMessage(" ");
//                    p.sendMessage(ChatColor.RED + "UPDATE!");
//                    p.sendMessage(ChatColor.RED + "You do not have the current version of the knockout!");
//                    p.sendMessage(ChatColor.RED + "Please update!");
//                    p.sendMessage(ChatColor.RED + "Your version: " + ChatColor.GOLD + Main.getInstance().getDescription().getVersion() +
//                            ChatColor.RED + " Latest Version: " + ChatColor.GOLD + version);
//                    p.sendMessage(" ");
//                    p.sendMessage(ChatColor.GOLD + "=======================================");
//                }
//            });
//        }

        if(p.isOp()) {
           if(Main.getInstance().getConfig().getBoolean("sendWarning")) {
               TextComponent msg = new TextComponent("§8» §cThis server is running modified version of Knockout by AW_meister! If you want to download original version ");

               TextComponent msg1 = new TextComponent("§c§nclick here!");
               msg1.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§c§oClick here to open original plugin site").create()));
               msg1.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://www.spigotmc.org/resources/nokaut-knockout.85152/"));

               TextComponent msg2 = new TextComponent(" §cYou can disable this warning in config.yml file.");

               p.spigot().sendMessage(msg, msg1, msg2);
           }
        }
    }
}
