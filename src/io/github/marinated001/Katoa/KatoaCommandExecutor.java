package io.github.marinated001.Katoa;

import io.github.marinated001.Katoa.Player.KatoaPlayer;
import io.github.marinated001.Katoa.Player.KatoaPlayerManager;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class KatoaCommandExecutor implements CommandExecutor
{
	// =============================================================================================
	private Katoa	plugin;

	private static enum RootCommand {
		paint, launchpad
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

		// Verify player permission
		if (!player.hasPermission("katoa.paint"))
		{
			plugin.getLog().sendPlayerWarnPerm(player);
			return true;
		}

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
		case launchpad:
			executeLaunchpad(args, player);
			break;
		}

	}

	// ###################################################################################################################################

	// ###################################################################################################################################
	private void executePaint(String[] args, Player player) {
		// /katoa paint [clay/wool/stop/undo]

		// TODO Make paintMode an enum

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
				plugin.getLog().sendPlayerNormal(
						kp.getPlayer(),
						"Painting enabled [mode: " + String.valueOf(paintMode)
								+ "]");
			} else if (args[1].equalsIgnoreCase("wool"))
			{
				int paintMode = 1;
				kp.getPaint().schedule(paintMode);
				plugin.getLog().sendPlayerNormal(
						kp.getPlayer(),
						"Painting enabled [mode: " + String.valueOf(paintMode)
								+ "]");

			} else if (args[1].equalsIgnoreCase("stop"))
			{
				plugin.getLog().sendPlayerWarn(player,
						"You are not currently painting!");
			} else if (args[1].equalsIgnoreCase("undo"))
			{
				kp.getPaint().undo();
				plugin.getLog().sendPlayerNormal(kp.getPlayer(),
						"Painting undone");
			} else if (args[1].equalsIgnoreCase("radius"))
			{
				try
				{
					int r = Integer.parseInt(args[2]);

					if (r <= 0)
					{
						plugin.getLog().sendPlayerWarn(kp.getPlayer(),
								"Radius must be a positive integer");
						return;
					}

					kp.getPaint().setRadius(r);
					plugin.getLog().sendPlayerNormal(kp.getPlayer(),
							"Brush radius set to " + r);

				} catch (Exception e)
				{
					plugin.getLog().sendPlayerWarn(kp.getPlayer(),
							"/katoa paint radius [#]");
				}
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
				plugin.getLog().sendPlayerNormal(kp.getPlayer(),
						"Painting disabled");
			} else if (args[1].equalsIgnoreCase("undo"))
			{
				plugin.getLog().sendPlayerWarn(player,
						"You are already painting!");
			} else if (args[1].equalsIgnoreCase("radius"))
			{
				try
				{
					int r = Integer.parseInt(args[2]);
					kp.getPaint().setRadius(r);
				} catch (Exception e)
				{
					plugin.getLog().sendPlayerWarn(kp.getPlayer(),
							"/katoa paint radius [#]");
				}
			} else
				printUse(player, RootCommand.paint);
		}

	}

	private void executeLaunchpad(String[] args, Player player) {
		// /katoa launchpad [add/remove/list]

		if (args.length < 2)
		{
			printUse(player, RootCommand.launchpad);
			return;
		}

		KatoaPlayer kp = KatoaPlayerManager.getKatoaPlayer(player);

		if (kp == null)
		{
			plugin.getLog().sendPlayerWarn(player,
					"An error has occured, please try relogging.");
			return;
		}

		if (args[1].equalsIgnoreCase("add"))
		{
			kp.getLaunchpad().addLaunchpad(
					kp,
					kp.getPlayer().getWorld().getName(),
					kp.getPlayer().getLocation().getBlock().getRelative(0, -1,
							0).getLocation());
		} else if (args[1].equalsIgnoreCase("remove"))
		{
			if (kp.getPlayer().getTargetBlock(null, 10).getType() != Material.REDSTONE_BLOCK)
			{
				plugin.getLog()
						.sendPlayerWarn(kp.getPlayer(),
								"Could not find launchpad! Look at the launchpad, then try again.");
				return;
			}

			kp.getLaunchpad().removeLaunchpad(
					kp.getPlayer().getWorld().getName(),
					kp.getPlayer().getTargetBlock(null, 10).getLocation());
		} else if (args[1].equalsIgnoreCase("list"))
		{
			for (Location loc : kp.getLaunchpad().getLaunchpads())
			{
				plugin.getLog().sendPlayerNormal(
						kp.getPlayer(),
						"[" + loc.getBlockX() + ", " + loc.getBlockY() + ", "
								+ loc.getBlockZ() + "]");
			}
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
					"Usage: /kaota paint [clay/wool/stop/radius]");
			break;
		case launchpad:
			plugin.getLog().sendPlayerWarn(player,
					"Usage: /katoa launchpad [add/remove/list]");
		}
	}
	// ###################################################################################################################################
}
