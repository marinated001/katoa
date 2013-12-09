package io.github.marinated001.Katoa;

import io.github.marinated001.Katoa.Module.KatoaPaint;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class KatoaCommandExecutor implements CommandExecutor
{
	// =============================================================================================
	private Katoa		plugin;
	private KatoaPaint	paint;
	private boolean		painting	= false;
	private int			paintMode	= 0;		// 0: clay; 1: wool

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
			plugin.getLog().info("/katoa is only available in game.");
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
		// /katoa paint [clay/wool/stop]

		this.paint = new KatoaPaint(paintMode, player, plugin);

		if (args.length < 2)
		{
			this.printUse(player, RootCommand.paint);
			return;
		}

		if (!this.painting)
		{
			if (args[1].equalsIgnoreCase("clay"))
			{
				this.painting = true;
				this.paintMode = 0;
				paint.schedule();
				player.sendMessage(ChatColor.GREEN + "Painting enabled [mode: "
						+ String.valueOf(paintMode) + "]");
			} else if (args[1].equalsIgnoreCase("wool"))
			{
				this.painting = true;
				this.paintMode = 1;
				paint.schedule();
				player.sendMessage(ChatColor.GREEN + "Painting enabled [mode: "
						+ String.valueOf(paintMode) + "]");

			} else if (args[1].equalsIgnoreCase("stop"))
			{
				plugin.getLog().sendPlayerWarn(player,
						"You are not currently painting!");
			} else
				this.printUse(player, RootCommand.paint);
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
				this.painting = false;
				this.paint.cancel();
				player.sendMessage(ChatColor.RED + "Painting disabled");
			} else
				this.printUse(player, RootCommand.paint);
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
