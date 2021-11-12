
//
// Copyright (c) 2021 smaks6 & AW_meister
//

package me.smaks6.plugin;

import me.smaks6.plugin.commands.DeathNowCommand;
import me.smaks6.plugin.commands.DropPlayerCommand;
import me.smaks6.plugin.commands.ForceReciveCommand;
import me.smaks6.plugin.commands.NokautCommand;
import me.smaks6.plugin.commands.tabcomplete.TabCompleter;
import me.smaks6.plugin.commands.PickupPlayerCommand;
import me.smaks6.plugin.pose.Pose;
import me.smaks6.plugin.service.UpdateChecker;
import me.smaks6.plugin.service.WorldGuardFlag;
import me.smaks6.plugin.utilities.PlayerUtilities;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffectType;
import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;
import java.util.Set;


public class Main extends JavaPlugin{
	
	private static Main instance;

	private String version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
	
//    public static HashMap<Player, NokautEnum> gracze = new HashMap<Player, NokautEnum>();// value = stoi, lezy, nies

	public void onEnable() {
		instance = this;
		Bukkit.getConsoleSender().sendMessage("");
		Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "|\\     |  |  /");
		Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "| \\    |  | /");
		Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "|  \\   |  |/");
		Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "|   \\  |  |\\");
		Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "|    \\ |  | \\");
		Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "|     \\|  |  \\");
		Bukkit.getConsoleSender().sendMessage("");
		Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "Enabling the plugin nokaut by smaks6 & modified by AW_meister");
		Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "Server version: " + version);

		try {
			registerEvents();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}


		//register WorldguardFlag
		if(getServer().getPluginManager().getPlugin("WorldGuard") != null) {
			new WorldGuardFlag().registerFlag();
		}
		
		new DeathNowCommand(this);
		new PickupPlayerCommand(this);
		new DropPlayerCommand(this);
		new ForceReciveCommand(this);

		new NokautCommand(this);
		getCommand("nokaut").setTabCompleter(new TabCompleter());

		if(!getDescription().getAuthors().contains("smaks6") || !getDescription().getAuthors().contains("AW_meister")){
			getLogger().info("Please don't remove authors' nicknames from plugin.yml file!");
			Bukkit.getPluginManager().disablePlugin(this);
		}
		getConfig().options().copyDefaults(true);
		saveConfig();
		reloadConfig();

        new UpdateChecker(85152).getVersion(version -> {
            if (this.getDescription().getVersion().equalsIgnoreCase(version)) {
            	Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "You have the latest version of the plugin");
            	Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "Nokaut plugin BY smaks6 & modified by AW_meister");
            } else {
            	Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "You don't have the latest plugin version");
            	Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "\\          /");
            	Bukkit.getConsoleSender().sendMessage(ChatColor.RED + " \\        /");
            	Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "  \\      /");
            	Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "   \\    /");
            	Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "    \\  /");
            	Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "     \\/");
            	Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Nokaut plugin BY smaks6 & modified by AW_meister");
            }
        });

	}

	@Override
	public void onDisable() {
		for (Player player : Bukkit.getServer().getOnlinePlayers()) {
			if(!PlayerUtilities.isNull(player)) {
				if (Main.getInstance().getConfig().getString("DeathOnEnd").equals("true")) {

					EntityDamageEvent lastDamageCause = player.getLastDamageCause();
					if (lastDamageCause instanceof EntityDamageByEntityEvent) {
						EntityDamageByEntityEvent damageByEntityEvent = (EntityDamageByEntityEvent) lastDamageCause;
						Entity damager = damageByEntityEvent.getDamager();
						player.damage(500, damager);
						Pose.stop(player);
					} else {
						player.setHealth(0);
					}

					Pose.stop(player);
				} else {
					Pose.stop(player);
					player.removePotionEffect(PotionEffectType.BLINDNESS);
				}
			}
		}
	}

	public static Main getInstance() {
	    return instance;
	}

	private void registerEvents() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
		Reflections reflections = new Reflections("me.smaks6.plugin");
		Set<Class<? extends Listener>> classSet = reflections.getSubTypesOf(Listener.class);
		for (Class<?> aClass : classSet) {
			Object o = aClass.getConstructor().newInstance();
			Bukkit.getServer().getPluginManager().registerEvents((Listener) o, this);
		}
	}


}
