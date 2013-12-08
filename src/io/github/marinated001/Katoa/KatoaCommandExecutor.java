package io.github.marinated001.Katoa;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class KatoaCommandExecutor implements CommandExecutor
{
	// =============================================================================================
	private Katoa	plugin;

	private static enum RootCommand {
		paint
	}

	public KatoaCommandExecutor(Katoa instance) {
		this.plugin = instance;
	}

	// =============================================================================================

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {
		// Make sure command was not sent from console
		if (sender instanceof Player == false)
		{
			plugin.getLogger().info("/katoa is only available in game.");
			return true;
		}

		// If no arguments display the default help message
		if (args.length == 0)
			return false;

		// Convert sender to player
		Player player = (Player) sender;

		// Grab root command
		RootCommand rootCommand = this.getRootCommand(args[0].toLowerCase());

		// Execute the command
		if (rootCommand != null)
		{
			this.execute(rootCommand, args, player);
			return true;
		} else
			return false;

	}

	// ###################################################################################################################################

	// ###################################################################################################################################
	private void execute(RootCommand rootCommand, String[] args, Player player) {
		switch (rootCommand)
		{
		case paint:
			this.executePaint(args, player);
			break;
		}
	}

	// ###################################################################################################################################

	// ###################################################################################################################################
	private void executePaint(String[] args, Player player) {
		//katoa paint [clay/wool]
		
		if (args.length < 2)
		{
			this.printUse(player, RootCommand.paint);
			return;
		}
		
	}

	// ###################################################################################################################################
	private RootCommand getRootCommand(String command) {
		RootCommand retval = null;
		try
		{
			retval = RootCommand.valueOf(command);
		} catch (IllegalArgumentException e)
		{
		}
		return retval;
	}

	// ###################################################################################################################################
	private void printUse(Player player, RootCommand command) {
		switch (command)
		{
		case paint:
			plugin.getLog().sendPlayerWarn(player, "Usage: /kaota paint [clay/wool] - toggles floor painting.");
			break;
		}
	}
	// ###################################################################################################################################
}
