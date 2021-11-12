package me.smaks6.plugin.pose;

import com.mojang.authlib.GameProfile;
import me.smaks6.plugin.Main;
import me.smaks6.plugin.utilities.Enum.Nokaut;
import me.smaks6.plugin.utilities.NokautUtilities;
import me.smaks6.plugin.utilities.PlayerUtilities;
import me.smaks6.plugin.utilities.Reflection.New.GameMode.ChangeGameModeNew;
import me.smaks6.plugin.utilities.Reflection.New.Npc.NpcNew;
import me.smaks6.plugin.utilities.Reflection.Old.GameMode.ChangeGameMode;
import me.smaks6.plugin.utilities.Reflection.Old.Npc.Npc;
import me.smaks6.plugin.utilities.ReflectionUtilities.Reflection;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.server.level.PlayerInteractManager;
import net.minecraft.world.entity.EntityPose;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.craftbukkit.v1_17_R1.CraftServer;
import org.bukkit.craftbukkit.v1_17_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_17_R1.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.List;
import java.util.UUID;

public class Pose{
    //start pose animation
    public static void start(Player p) {
        PlayerUtilities.setEnum(p, Nokaut.LAY);
        p.setWalkSpeed(0);
        p.setFlySpeed(0);
        p.setCollidable(false);
        p.removePotionEffect(PotionEffectType.POISON);

        p.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 1000000, 100));

        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            createNPC(p, onlinePlayer);
        }
        hidePlayers(p);

        Entity damager = NokautUtilities.getLastDamager(p);
        List<String> commandsOnNokaut = Main.getInstance().getConfig().getStringList("commandsOnNokaut");
        for (String command : commandsOnNokaut) {
            command = command.replaceAll("\"KnockedPlayer\"", p.getName());
            command = command.replaceAll("\"Damager\"", (damager == null ? "" : damager.getName()));
            Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), command);
        }
    }

    //stop pose animation
    public static void stop(Player p){
        PlayerUtilities.unSet(p);

        p.setCollidable(true);

        p.setDisplayName(p.getName());

        p.removePotionEffect(PotionEffectType.INVISIBILITY);

        p.setWalkSpeed(0.2F);
        p.setFlySpeed(0.1F);

        p.setGameMode(GameMode.SURVIVAL);

        showPlayers(p);

        Entity damager = NokautUtilities.getLastDamager(p);
        List<String> commandsAfterNokaut = Main.getInstance().getConfig().getStringList("commandsAfterNokaut");
        for (String command : commandsAfterNokaut) {
            command = command.replaceAll("\"KnockedPlayer\"", p.getName());
            command = command.replaceAll("\"Damager\"", (damager == null ? "" : damager.getName()));
            Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), command);
        }
    }

    public static void createNPC(Player knockedPlayer, Player see){
//        switch (version){
//            case "v1_16_R3": new me.smaks6.v1_16_R3.CreateNPC(znokautowany, see); break;
//
//            case "v1_16_R2": new me.smaks6.v1_16_R2.CreateNPC(znokautowany, see); break;
//
//            case "v1_16_R1": new me.smaks6.v1_16_R1.CreateNPC(znokautowany, see); break;
//
//            case "v1_15_R1": new me.smaks6.v1_15_R1.CreateNPC(znokautowany, see); break;
//
//        }
        if(Reflection.isNewPackeges())new NpcNew(knockedPlayer, see);
        else new Npc(knockedPlayer, see);
    }

    //change game mode
    public static void changegamemode(Player p, Player reviever, boolean nies){
        //nies:
        //true - na plecach niech idzie
        //false - niech już spada z pleców

//        switch (version){
//            case "v1_16_R3": me.smaks6.v1_16_R3.OtherMetchod.changeGameMode(p, reviever, nies); break;
//
//            case "v1_16_R2": me.smaks6.v1_16_R2.OtherMetchod.changeGameMode(p, reviever, nies); break;
//
//            case "v1_16_R1": me.smaks6.v1_16_R1.OtherMetchod.changeGameMode(p, reviever, nies); break;
//
//            case "v1_15_R1": me.smaks6.v1_15_R1.OtherMetchod.changeGameMode(p, reviever, nies); break;
//
//        }

            if(Reflection.isNewPackeges()) ChangeGameModeNew.changeGameMode(p, reviever, nies);
            else ChangeGameMode.changeGameMode(p, reviever, nies);

    }


    public static void hidePlayers(Player player) {
        for (Player onlinePlayer : Bukkit.getServer().getOnlinePlayers()) {
            onlinePlayer.hidePlayer(Main.getInstance(), player);
        }
    }

    public static void showPlayers(Player player){
        for (Player onlinePlayer : Bukkit.getServer().getOnlinePlayers()) {
            onlinePlayer.showPlayer(Main.getInstance(), player);
        }
    }

}
