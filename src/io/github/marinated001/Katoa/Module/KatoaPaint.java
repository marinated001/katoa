package io.github.marinated001.Katoa.Module;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class KatoaPaint
{
	private int		mode;
	private Player	player;
	private Server	server	= Bukkit.getServer();
	private Plugin	plugin;
	private int		taskid;

	public KatoaPaint(int m, Player p, Plugin plugin) {
		this.mode = m;
		this.player = p;
		this.plugin = plugin;
	}

	public void schedule() {

		this.taskid = server.getScheduler().scheduleSyncRepeatingTask(plugin,
				new Runnable() {
			
					@Override
					public void run() {
						paint();
					}
					
				}, 0L, 20L);
	}

	public void cancel() {
		server.getScheduler().cancelTask(this.taskid);
	}

	public void paint() {
		player.sendMessage("ping");
	}

	public DyeColor getRandomDyeColor() {
		Random random = new Random();
		DyeColor color = null;
		int c = random.nextInt(16);
		switch (c)
		{
		case 0:
			color = DyeColor.BLACK;
			break;
		case 1:
			color = DyeColor.BLUE;
			break;
		case 2:
			color = DyeColor.BROWN;
			break;
		case 3:
			color = DyeColor.CYAN;
			break;
		case 4:
			color = DyeColor.GREEN;
			break;
		case 5:
			color = DyeColor.LIGHT_BLUE;
			break;
		case 6:
			color = DyeColor.LIME;
			break;
		case 7:
			color = DyeColor.LIME;
			break;
		case 8:
			color = DyeColor.MAGENTA;
			break;
		case 9:
			color = DyeColor.ORANGE;
			break;
		case 10:
			color = DyeColor.PINK;
			break;
		case 11:
			color = DyeColor.PURPLE;
			break;
		case 12:
			color = DyeColor.RED;
			break;
		case 13:
			color = DyeColor.SILVER;
			break;
		case 14:
			color = DyeColor.WHITE;
			break;
		case 15:
			color = DyeColor.YELLOW;
			break;
		}
		return color;
	}

}
