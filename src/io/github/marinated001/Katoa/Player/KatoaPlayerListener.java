package io.github.marinated001.Katoa.Player;

import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;

import io.github.marinated001.Katoa.Util.EventListener;

public class KatoaPlayerListener extends EventListener
{
	public KatoaPlayerListener() {
		super.registerEvents(this);
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		KatoaPlayerManager.registerPlayer(event.getPlayer());
	}
}
