package com.ageisanint;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements CommandExecutor {

	Plugin plugin = this;

	public void onEnable() {
		getLogger()
				.info(" v"
						+ getDescription().getVersion()
						+ " ha sido activado. Visita ageisanint.com para más información y soporte");
		loadConfiguration();
	}

	public void onDisable() {

	}

	public void loadConfiguration() {
		this.plugin.getConfig().options().copyDefaults(true);
		this.plugin.saveConfig();
	}

	long deathTime = 1000 * 60 * 60 * 24 * this.plugin.getConfig().getInt("DeathTime");

	File BaseFolder = new File(Bukkit.getServer().getWorld(this.plugin.getConfig().getString("World"))
			.getWorldFolder(), "players");

	@Override
	public boolean onCommand(CommandSender sender, Command cmd,
			String commandLabel, String[] args) {
		Player admin = (Player) sender;
		if (cmd.getName().equalsIgnoreCase("cleanusers")) {

			if (sender instanceof Player) {

				for (OfflinePlayer player : Bukkit.getOfflinePlayers()) {
					long lastJoint = player.getLastPlayed();
					if (lastJoint > deathTime) {
						admin.sendMessage(ChatColor.GREEN + "[PokeMclean]"
								+ ChatColor.GOLD + "El jugador "
								+ player.getName() + " ha sido eliminado.");
						
						 File playerFile = new File(BaseFolder, player.getName()+".dat");
				            playerFile.delete();
						
					}
				}
				admin.sendMessage(ChatColor.GREEN + "[PokeMclean]"
						+ ChatColor.GOLD + "El proceso de limpieza a terminado");
			} else {
				System.out
						.println("Este comando solo puede ser ejecutado por un jugador");
			}
		}

		return false;
	}

}
