package me.dernudel.pokemongo.listener;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import me.dernudel.pokemongo.PokemonGO;
import me.dernudel.pokemongo.util.Save;

public class TrackerListener implements Listener {

	@EventHandler
	public void onTrack(PlayerMoveEvent e) {
		if (PokemonGO.trp.contains(e.getPlayer())) {
			e.getPlayer().setCompassTarget(nearest(e.getPlayer()));
		}
	}
	
	@EventHandler
	public void onSlotChange(PlayerItemHeldEvent e) {
		Player p = e.getPlayer();
		if (PokemonGO.trp.contains(p)) {
			if (p.getInventory().getItem(e.getNewSlot()) == null ||!p.getInventory().getItem(e.getNewSlot()).equals(PokemonGO.tr)) {
				PokemonGO.trp.remove(p);
				p.setCompassTarget(p.getWorld().getSpawnLocation());
			}
		} else {
			if (p.getInventory().getItem(e.getNewSlot()) != null && p.getInventory().getItem(e.getNewSlot()).equals(PokemonGO.tr)) {
				PokemonGO.trp.add(p);
				p.setCompassTarget(nearest(p));
			}
		}
	}
	
	
	public Location nearest(Player p) {
		Location l = p.getWorld().getSpawnLocation();
		List<Location> ll = new ArrayList<Location>();
		if (Save.pkmnEntries().size() == 0) return p.getWorld().getSpawnLocation();
		for (String s : Save.pkmnEntries()) {
			ll.add(Save.stringToLoc(s.split(":")[1]));
		}
		double d = Double.MAX_VALUE;
		for (Location llll : ll) {
			d = Math.min(p.getLocation().distance(llll), d);
		}
		for (Location lll : ll) {
			if (p.getLocation().distance(lll) == d) {
				l = lll;
			}
		}
		return l;
	}
}
