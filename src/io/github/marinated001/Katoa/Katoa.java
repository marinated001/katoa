package io.github.marinated001.Katoa;

import io.github.marinated001.Katoa.Listener.KatoaLaunchpadListener;
import io.github.marinated001.Katoa.Module.KatoaLaunchpad;
import io.github.marinated001.Katoa.Player.KatoaPlayerListener;
import io.github.marinated001.Katoa.Util.EventListener;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class Katoa extends JavaPlugin
{
	// =============================================================================================
	private KatoaCommandExecutor	commandExecutor;
	private KatoaLogHandler			log;
	private static Katoa			instance;

	private File					configFile;		// declare files

	private FileConfiguration		config;			// declare file
														// configurations

	public KatoaLogHandler getLog() {
		return this.log;
	}

	public static Katoa getInstance() {
		return instance;
	}

	// =============================================================================================
	public void onEnable() {
		// Save a copy of the default config.yml if one is not there
		this.saveDefaultConfig();

		// Instance
		this.instance = this;

		// Listener
		new EventListener();
		new KatoaPlayerListener();
		new KatoaLaunchpadListener();

		// Log
		this.log = new KatoaLogHandler(this);

		// Commands handler
		this.commandExecutor = new KatoaCommandExecutor(this);
		this.getCommand("katoa").setExecutor(this.commandExecutor);

		// Launchpad Config
		KatoaLaunchpad.addLaunchpadsFromConfig();

	}

	public void onDisable() {
		// Config
		this.saveConfig();

	}

}