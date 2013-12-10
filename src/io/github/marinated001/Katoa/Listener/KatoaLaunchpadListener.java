package io.github.marinated001.Katoa.Listener;

import io.github.marinated001.Katoa.Katoa;
import io.github.marinated001.Katoa.Module.KatoaLaunchpad;
import io.github.marinated001.Katoa.Player.KatoaPlayer;
import io.github.marinated001.Katoa.Player.KatoaPlayerManager;
import io.github.marinated001.Katoa.Util.EventListener;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerMoveEvent;

public class KatoaLaunchpadListener extends EventListener
{
	public KatoaLaunchpadListener() {
		super.registerEvents(this);
	}

	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event) {
		if (event.getTo().getBlock().getRelative(0, -1, 0).getType() == Material.REDSTONE_BLOCK)
		{
			if (KatoaLaunchpad.launchpads.contains(event.getTo().getBlock()
					.getRelative(0, -1, 0).getLocation()))
			{
				KatoaPlayer kp = KatoaPlayerManager.getKatoaPlayer(event
						.getPlayer());

				if (kp == null)
				{
					Katoa.getInstance().getLog().sendPlayerWarn(
							event.getPlayer(),
							"An error has occured, please try relogging.");
					return;
				}

				kp.getLaunchpad().launch(
						event.getTo().getBlock().getRelative(0, -1, 0)
								.getLocation(), 3.0D, 1.0D);
			}

		}
	}

	@EventHandler
	public void onPlayerDamage(EntityDamageEvent event) {
		if (event.getEntity() instanceof Player)
		{
			KatoaPlayer kp = KatoaPlayerManager.getKatoaPlayer((Player) event
					.getEntity());
			if (event.getCause() == DamageCause.FALL)
			{
				event.setDamage(0.0);
			}

		}

	}
}
