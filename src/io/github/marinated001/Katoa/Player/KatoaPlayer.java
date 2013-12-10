package io.github.marinated001.Katoa.Player;

import io.github.marinated001.Katoa.Module.KatoaLaunchpad;
import io.github.marinated001.Katoa.Module.KatoaPaint;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class KatoaPlayer
{
	private String			username;
	private KatoaPaint		paint;
	private KatoaLaunchpad	launchpad;

	public KatoaPlayer(Player p) {
		username = p.getName();
		paint = new KatoaPaint(this);
		launchpad = new KatoaLaunchpad(this);
	}

	public Player getPlayer() {
		return Bukkit.getPlayer(username);
	}

	public String getName() {
		return username;
	}

	public KatoaPaint getPaint() {
		return paint;
	}

	public KatoaLaunchpad getLaunchpad() {
		return launchpad;
	}
}
