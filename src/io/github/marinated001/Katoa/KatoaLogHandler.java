package io.github.marinated001.Katoa;

import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;

public class KatoaLogHandler
{
	// ==============================================================
	private Katoa	plugin;
	private Logger	logger;

	public KatoaLogHandler(Katoa plugin) {
		this.plugin = plugin;
		this.logger = Logger.getLogger("Minecraft");
	}

	// ==============================================================

	// Generate message for console
	private String buildString(String message) {
		PluginDescriptionFile pdFile = plugin.getDescription();
		return "[" + pdFile.getName() + "] (" + pdFile.getVersion() + ") "
				+ message;
	}

	// Generate message for player
	private String buildStringPlayer(String message, ChatColor color) {
		PluginDescriptionFile pdFile = plugin.getDescription();
		return color + "[" + pdFile.getName() + "] " + message;
	}

	// Log message as INFO
	public void info(String message) {
		this.logger.info(this.buildString(message));
	}

	// Log message as WARNING
	public void warn(String message) {
		this.logger.warning(this.buildString(message));
	}

	// Normal message to the player
	public void sendPlayerNormal(Player player, String message) {
		player.sendMessage(this.buildStringPlayer(message, ChatColor.AQUA));
	}

	// Warning message to the player
	public void sendPlayerWarn(Player player, String message) {
		player.sendMessage(this.buildStringPlayer(message, ChatColor.YELLOW));
	}

	// Permission warning message to player
	public void sendPlayerWarnPerm(Player player) {
		player.sendMessage(this.buildStringPlayer("You don't have permission!",
				ChatColor.RED));
	}

}
