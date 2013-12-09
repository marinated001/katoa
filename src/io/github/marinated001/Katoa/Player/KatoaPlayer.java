package io.github.marinated001.Katoa.Player;

import io.github.marinated001.Katoa.Module.KatoaPaint;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class KatoaPlayer
{
	private String		username;
	private KatoaPaint	paint;

	public KatoaPlayer(Player p) {
		username = p.getName();
		paint = new KatoaPaint(this);
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
}
