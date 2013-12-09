package io.github.marinated001.Katoa;

import io.github.marinated001.Katoa.Player.KatoaPlayerListener;
import io.github.marinated001.Katoa.Util.EventListener;

import org.bukkit.plugin.java.JavaPlugin;

public class Katoa extends JavaPlugin
{
	// =============================================================================================
	private KatoaCommandExecutor	commandExecutor;
	private KatoaLogHandler			log;
	private static Katoa			instance;

	public KatoaLogHandler getLog() {
		return this.log;
	}

	public static Katoa getInstance() {
		return instance;
	}

	// =============================================================================================
	public void onEnable() {
		// Instance
		this.instance = this;

		// Listener
		new EventListener();
		new KatoaPlayerListener();

		// Log
		this.log = new KatoaLogHandler(this);

		// Commands handler
		this.commandExecutor = new KatoaCommandExecutor(this);
		this.getCommand("katoa").setExecutor(this.commandExecutor);
	}

}