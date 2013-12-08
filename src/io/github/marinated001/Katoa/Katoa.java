package io.github.marinated001.Katoa;

import org.bukkit.plugin.java.JavaPlugin;

public class Katoa extends JavaPlugin
{
	// =============================================================================================
	private KatoaCommandExecutor	commandExecutor;
	private KatoaLogHandler			log;
	public KatoaLogHandler getLog() { return this.log; } 
	// =============================================================================================
	public void onEnable()
	{
		//Log
    	this.log = new KatoaLogHandler(this);
    	
		// Commands handler
		this.commandExecutor = new KatoaCommandExecutor(this);
		this.getCommand("katoa").setExecutor(this.commandExecutor);
	}

}