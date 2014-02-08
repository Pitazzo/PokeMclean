package com.ageisanint;

import java.io.File;
import java.util.Date;

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


	Date date = new Date();
	

	@Override
	public boolean onCommand(CommandSender sender, Command cmd,
			String commandLabel, String[] args) {
		Player admin = (Player) sender;
		if (cmd.getName().equalsIgnoreCase("cleanusers")) {
			int players = 0;
			if (sender instanceof Player) {
				if(admin.hasPermission("PokeMclean.use")||admin.isOp()){
					
				for (OfflinePlayer player : Bukkit.getOfflinePlayers()) {
					long lastJoint = player.getLastPlayed();
					File BaseFolder = new File(admin.getWorld().getWorldFolder(), "players");
					long diff = Math.abs(date.getTime() - lastJoint);
					long diffDays = diff / (24 * 60 * 60 * 1000);
					if (diffDays > plugin.getConfig().getInt("DeathTime")) {
						admin.sendMessage(ChatColor.GREEN + "[PokeMclean]"
								+ ChatColor.GOLD + "El jugador "
								+ player.getName() + " ha sido eliminado. ");
						players ++;
						File playerFile = new File(BaseFolder, player.getName()+".dat");
						playerFile.delete();
						
					}
				}
				admin.sendMessage(ChatColor.GREEN + "[PokeMclean]"
						+ ChatColor.RED + "El proceso de limpieza ha terminado. Se han eliminado "+players+" jugadores inactivos.");
				
				}else{
					admin.sendMessage(ChatColor.RED+"Necesitas ser administrador para usar este comando");
				
				}
			} else {
				System.out
						.println("Este comando solo puede ser ejecutado por un jugador");
				
			}
		}

		return false;
	}

}
