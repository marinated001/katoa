package io.github.marinated001.Katoa;

import io.github.marinated001.Katoa.Player.KatoaPlayer;
import io.github.marinated001.Katoa.Player.KatoaPlayerManager;

import org.bukkit.ChatColor;
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
		plugin = instance;
	}

	// =============================================================================================

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {
		// Make sure command was not sent from console
		if (sender instanceof Player == false)
		{
			plugin.getLog().info("/katoa is only available in game.");
			return true;
		}

		// If no arguments display the default help message
		if (args.length == 0)
			return false;

		// Convert sender to player
		Player player = (Player) sender;

		// Grab root command
		RootCommand rootCommand = getRootCommand(args[0].toLowerCase());

		// Execute the command
		if (rootCommand != null)
		{
			execute(rootCommand, args, player);
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
			executePaint(args, player);
			break;
		}

	}

	// ###################################################################################################################################

	// ###################################################################################################################################
	private void executePaint(String[] args, Player player) {
		// /katoa paint [clay/wool/stop]

		if (args.length < 2)
		{
			printUse(player, RootCommand.paint);
			return;
		}

		KatoaPlayer kp = KatoaPlayerManager.getKatoaPlayer(player);

		if (kp == null)
		{
			plugin.getLog().sendPlayerWarn(player,
					"An error has occured, please try relogging.");
			return;
		}

		if (!kp.getPaint().isPainting())
		{
			if (args[1].equalsIgnoreCase("clay"))
			{
				int paintMode = 0;
				kp.getPaint().schedule(paintMode);
				player.sendMessage(ChatColor.GREEN + "Painting enabled [mode: "
						+ String.valueOf(paintMode) + "]");
			} else if (args[1].equalsIgnoreCase("wool"))
			{
				int paintMode = 1;
				kp.getPaint().schedule(paintMode);
				player.sendMessage(ChatColor.GREEN + "Painting enabled [mode: "
						+ String.valueOf(paintMode) + "]");

			} else if (args[1].equalsIgnoreCase("stop"))
			{
				plugin.getLog().sendPlayerWarn(player,
						"You are not currently painting!");
			} else
				printUse(player, RootCommand.paint);
		} else
		{
			if (args[1].equalsIgnoreCase("clay"))
				plugin.getLog().sendPlayerWarn(player,
						"You are already painting!");
			else if (args[1].equalsIgnoreCase("wool"))
				plugin.getLog().sendPlayerWarn(player,
						"You are already painting!");
			else if (args[1].equalsIgnoreCase("stop"))
			{
				kp.getPaint().cancel();
				player.sendMessage(ChatColor.RED + "Painting disabled");
			} else
				printUse(player, RootCommand.paint);
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
			plugin.getLog().sendPlayerWarn(player,
					"Usage: /kaota paint [clay/wool/stop]");
			break;
		}
	}
	// ###################################################################################################################################
}
