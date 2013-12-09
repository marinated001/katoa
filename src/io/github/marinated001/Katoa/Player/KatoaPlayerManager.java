package io.github.marinated001.Katoa.Player;

import java.util.ArrayList;

import org.bukkit.entity.Player;

public class KatoaPlayerManager
{
	private static ArrayList<KatoaPlayer>	players	= new ArrayList<KatoaPlayer>();

	public static void registerPlayer(Player p) {
		if (isRegistered(p))
			return;
		players.add(new KatoaPlayer(p));
	}

	public static KatoaPlayer getKatoaPlayer(String username) {
		for (KatoaPlayer k : players)
		{
			if (k.getName().equals(username))
				return k;
		}
		return null;
	}

	public static KatoaPlayer getKatoaPlayer(Player p) {
		return getKatoaPlayer(p.getName());
	}

	public static boolean isRegistered(Player p) {
		return getKatoaPlayer(p) != null;
	}

}
