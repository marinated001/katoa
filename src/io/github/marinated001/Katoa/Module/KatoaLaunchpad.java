package io.github.marinated001.Katoa.Module;

import io.github.marinated001.Katoa.Katoa;
import io.github.marinated001.Katoa.Player.KatoaPlayer;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.util.Vector;

public class KatoaLaunchpad
{
	private KatoaPlayer					kp;
	public static ArrayList<Location>	launchpads		= new ArrayList<Location>();
	private static List<String>			launchpadsXYZ	= new ArrayList<String>();

	public KatoaLaunchpad(KatoaPlayer kp) {
		this.kp = kp;
	}

	public void launch(Location loc, double par1, double par2) {

		if (!launchpads.contains(loc))
			return;

		kp.getPlayer().setVelocity(
				kp.getPlayer().getLocation().getDirection().multiply(par1));
		kp.getPlayer().setVelocity(
				new Vector(kp.getPlayer().getVelocity().getX(), par2, kp
						.getPlayer().getVelocity().getZ()));

	}

	public void addLaunchpad(KatoaPlayer kp, String world, Location loc) {

		if (launchpads.contains(loc))
		{
			Katoa.getInstance().getLog().sendPlayerWarn(kp.getPlayer(),
					"This block is already a launchpad!");
			return;
		}

		kp.getPlayer().getLocation().getBlock().getRelative(0, -1, 0).setType(
				Material.REDSTONE_BLOCK);

		launchpads.add(loc);

		this.addToList(world);

		this.addToConfig("katoa.launchpads.locations");

		Katoa.getInstance().getLog().sendPlayerNormal(kp.getPlayer(),
				"Launchpad successfully added");
	}

	public void removeLaunchpad(String world, Location loc) {

		if (!launchpads.contains(loc))
		{
			Katoa.getInstance().getLog().sendPlayerWarn(kp.getPlayer(),
					"This block is not yet a launchpad!");
			return;
		}

		launchpads.remove(loc);

		String x = String.valueOf(loc.getX());
		String y = String.valueOf(loc.getY());
		String z = String.valueOf(loc.getZ());

		String element = (world + "," + x + "," + y + "," + z);

		launchpadsXYZ.remove(element);

		Katoa.getInstance().getConfig().getList("katoa.launchpads.locations")
				.remove(element);

		Katoa.getInstance().getLog().sendPlayerNormal(kp.getPlayer(),
				"Launchpad successfully removed");

		Katoa.getInstance().saveConfig();
	}

	public static void addLaunchpadsFromConfig() {
		List<String> lp = Katoa.getInstance().getConfig().getStringList(
				"katoa.launchpads.locations");

		for (String loc : lp)
		{
			String[] arg = loc.split(",");
			Double[] parsed = new Double[4];
			for (int a = 1; a < 4; a++)
			{
				parsed[a] = Double.parseDouble(arg[a]);
			}
			Location location = new Location(Bukkit.getWorld(arg[0]),
					parsed[1], parsed[2], parsed[3]);

			launchpads.add(location);
			addToList(arg[0]);

		}
	}

	public static void addToList(String world) {

		for (Location loc : launchpads)
		{
			String x = String.valueOf(loc.getX());
			String y = String.valueOf(loc.getY());
			String z = String.valueOf(loc.getZ());

			String element = (world + "," + x + "," + y + "," + z);

			launchpadsXYZ.add(element);

		}
	}

	public void addToConfig(String path) {
		Katoa.getInstance().getConfig().set(path, launchpadsXYZ);
		Katoa.getInstance().saveConfig();
	}

	public ArrayList<Location> getLaunchpads() {
		return launchpads;
	}

}
