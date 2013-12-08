package io.github.marinated001.Katoa;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public final class Katoa extends JavaPlugin
{
	private boolean				paintcmdClay	= false;
	private boolean				paintcmdWool	= false;
	private Block				block;
	private ArrayList<Block>	blockArray		= new ArrayList<Block>();

	@Override
	public void onEnable()
	{
	}

	@Override
	public void onDisable()
	{
		paintcmdClay = false;
		paintcmdWool = false;
	}

	/* CMD Methods */
	@SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args)
	{
		Player p = (Player) sender;

		if (cmd.getName().equalsIgnoreCase("paintfloor"))
		{
			if (args.length == 0 || args.length > 1)
			{
				this.print(p, ChatColor.RED
						+ "Invalid arguments! Usage: /paintfloor <clay:wool>");
				return false;
			}
			if (args[0].equalsIgnoreCase("clay") && !paintcmdWool)
			{
				paintcmdClay = !paintcmdClay;

				if (paintcmdClay)
				{

					Location pLoc = p.getLocation();

					block = pLoc.getBlock().getRelative(0, -1, 0);

					blockArray.add(block);

					this.print(p, ChatColor.RED
							+ blockArray.get(0).getType().toString());

					block.setTypeIdAndData(159, this.getRandomDyeColor()
							.getData(), true);

					this.print(p, ChatColor.BLUE
							+ blockArray.get(0).getType().toString());
				} else
				{
					/* Undo block changes */
					for (Block bl : blockArray)
					{
						bl.setTypeIdAndData(bl.getTypeId(), bl.getData(), true);
						this.print(p, ChatColor.GREEN + bl.getType().toString());
					}

					blockArray.clear();
					this.print(p, ChatColor.RED + "Blocks reset.");
				}

				/**
				 * while (paintcmdClay) { Location pLoc = p.getLocation(); Block
				 * b = pLoc.getBlock().getRelative(0, -1, 0);
				 * 
				 * this.print(p, b.getType().toString());
				 * 
				 * if (b.getType().equals(Material.getMaterial(159))) {
				 * this.print(p, "true"); continue; } else { block.add(b);
				 * b.setType(Material.getMaterial(159));
				 * b.setData(this.getRandomDyeColor().getData()); this.print(p,
				 * "block added."); }
				 * 
				 * }
				 */

				return true;

			} else if (args[0].equalsIgnoreCase("wool") && !paintcmdClay)
			{
				paintcmdWool = !paintcmdWool;

				if (paintcmdWool)
				{

					Location pLoc = p.getLocation();
					Block b = pLoc.getBlock().getRelative(0, -1, 0);

					blockArray.add(b);

					this.print(p, ChatColor.RED
							+ blockArray.get(0).getType().toString());

					b.setTypeIdAndData(35, this.getRandomDyeColor().getData(),
							true);

					this.print(p, ChatColor.BLUE
							+ blockArray.get(0).getType().toString());
				} else
				{
					/* Undo block changes */
					for (Block bl : blockArray)
					{
						bl.setTypeIdAndData(bl.getTypeId(), bl.getData(), true);
						this.print(p, ChatColor.GREEN + bl.getType().toString());
					}

					blockArray.clear();
					this.print(p, ChatColor.RED + "Blocks reset.");
				}

				/**
				 * while (paintcmdWool) { Location pLoc = p.getLocation(); Block
				 * b = pLoc.getBlock().getRelative(0, -1, 0);
				 * 
				 * this.print(p, b.getType().toString());
				 * 
				 * if (b.getType().equals(Material.WOOL)) { this.print(p,
				 * "true"); continue; } else { block.add(b);
				 * b.setType(Material.WOOL);
				 * b.setData(this.getRandomDyeColor().getData()); this.print(p,
				 * "block added."); }
				 * 
				 * }
				 */

				return true;

			} else
			{
				this.print(p, ChatColor.RED + "Error: Already painting!");
				return false;
			}
		}
		return false;
	}

	/* Miscellaneous Methods */
	public void print(Player p, String s)
	{
		p.sendMessage(ChatColor.GOLD + "||" + ChatColor.AQUA + " Events "
				+ ChatColor.GOLD + "|| " + ChatColor.RESET + s);
	}

	public DyeColor getRandomDyeColor()
	{
		DyeColor color = DyeColor.WHITE;
		Random random = new Random();
		int x = random.nextInt(16);
		switch (x)
		{
		case 1:
			color = DyeColor.BLACK;
			break;
		case 2:
			color = DyeColor.BLUE;
			break;
		case 3:
			color = DyeColor.BROWN;
			break;
		case 4:
			color = DyeColor.CYAN;
			break;
		case 5:
			color = DyeColor.GRAY;
			break;
		case 6:
			color = DyeColor.GREEN;
			break;
		case 7:
			color = DyeColor.LIGHT_BLUE;
			break;
		case 8:
			color = DyeColor.LIME;
			break;
		case 9:
			color = DyeColor.MAGENTA;
			break;
		case 10:
			color = DyeColor.ORANGE;
			break;
		case 11:
			color = DyeColor.PINK;
			break;
		case 12:
			color = DyeColor.PURPLE;
			break;
		case 13:
			color = DyeColor.RED;
			break;
		case 14:
			color = DyeColor.SILVER;
			break;
		case 15:
			color = DyeColor.WHITE;
			break;
		case 16:
			color = DyeColor.YELLOW;
			break;
		}
		return color;

	}
}