package me.dernudel.pokemongo.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import me.dernudel.pokemongo.util.Save;

public class JoinListener implements Listener {

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		if (!Save.exist(p.getUniqueId().toString())) Save.register(p.getUniqueId().toString());
	}
	
}
