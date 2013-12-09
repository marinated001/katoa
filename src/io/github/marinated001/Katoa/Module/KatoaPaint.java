package io.github.marinated001.Katoa.Module;

import io.github.marinated001.Katoa.Katoa;
import io.github.marinated001.Katoa.Player.KatoaPlayer;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.block.Block;

public class KatoaPaint
{
	private int							mode;
	private int							taskid			= -1;
	private int							radius			= 1;
	private KatoaPlayer					kp;
	private Server						server			= Bukkit.getServer();
	private HashMap<Location, Material>	blocksChanged	= new HashMap<Location, Material>();

	public KatoaPaint(KatoaPlayer p) {
		this.kp = p;
	}

	public void setRadius(int r) {
		this.radius = r;
	}

	public void schedule(int mode) {
		this.mode = mode;

		this.taskid = server.getScheduler().scheduleSyncRepeatingTask(
				Katoa.getInstance(), new Runnable() {

					@Override
					public void run() {
						if (kp.getPlayer() == null)
							return;
						paint();
					}

				}, 0L, 2L);
	}

	public void cancel() {
		server.getScheduler().cancelTask(this.taskid);
		this.taskid = -1;
	}

	public void undo() {
		for (Entry<Location, Material> e : blocksChanged.entrySet())
		{
			e.getKey().getBlock().setType(e.getValue());
		}

		blocksChanged.clear();
	}

	@SuppressWarnings("deprecation")
	public void paint() {

		Block center = kp.getPlayer().getLocation().getBlock().getRelative(0,
				-1, 0);

		if (center.getType() == Material.AIR)
			return;

		int half = Math.round(radius / 2);

		if (half <= 0)
		{
			Block b = center;

			addToTracker(b.getLocation(), b.getType());
			if (mode == 0)
			{
				b.setType(Material.STAINED_CLAY);

			} else if (mode == 1)
			{
				b.setType(Material.WOOL);
			}

			b.setData(getRandomDyeColor().getData());
		}

		for (int xoffset = -half; xoffset < half; xoffset++)
		{
			for (int zoffset = -half; zoffset < half; zoffset++)
			{
				Block b = center.getLocation().subtract(xoffset, 0, zoffset)
						.getBlock();
				if (b.getType() == Material.AIR)
					return;

				addToTracker(b.getLocation(), b.getType());
				if (mode == 0)
				{
					b.setType(Material.STAINED_CLAY);

				} else if (mode == 1)
				{
					b.setType(Material.WOOL);
				}

				b.setData(getRandomDyeColor().getData());
			}
		}

	}

	private void addToTracker(Location loc, Material old) {
		if (blocksChanged.containsKey(loc))
			return;
		blocksChanged.put(loc, old);
	}

	public boolean isPainting() {
		return this.taskid != -1;
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
