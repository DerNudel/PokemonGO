package me.dernudel.pokemongo.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import me.dernudel.pokemongo.PokemonGO;
import me.dernudel.pokemongo.map.MapTask;
import me.dernudel.pokemongo.map.Point;
import me.dernudel.pokemongo.util.Cipher;
import me.dernudel.pokemongo.util.Format;
import me.dernudel.pokemongo.util.Save;

public class MarkerListener implements Listener {
	
	boolean set = false;
	boolean right = false;
	boolean left = false;
	int i = 0;
	PokemonGO pl;
	
	public MarkerListener(PokemonGO pl) {
		this.pl = pl;
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		if (PokemonGO.setup.contains(e.getPlayer())) {
			if (e.getPlayer().getItemInHand().equals(PokemonGO.mi)) {
				switch(e.getAction()) {
					case RIGHT_CLICK_BLOCK:
						e.setCancelled(true);
						Point p = new Point(e.getClickedBlock().getX(), e.getClickedBlock().getZ());
						Save.addPoint(p, 1);
						if (!right) e.getPlayer().sendMessage(Format.a(PokemonGO.p + "\u00A75Pos" + (set ? 2 : 1) + " set"));
						if (set && !right) {
							continueSetup(e.getPlayer());
							break;
						} else if (!set && !right){
							set = true;
						}
						right = true;
						break;
					case LEFT_CLICK_BLOCK:
						e.setCancelled(true);
						p = new Point(e.getClickedBlock().getX(), e.getClickedBlock().getZ());
						Save.addPoint(p, 2);
						if (!left) e.getPlayer().sendMessage(Format.a(PokemonGO.p + "\u00A75Pos" + (set ? 2 : 1) + " set"));
						if (set && !left) {
							continueSetup(e.getPlayer());
							break;
						} else if(!set && !left) {
							set = true;
						}
						left = true;
						break;
					default:
						break;
				}
			}
			else {
				i++;
			}
		}
	}
	
	private void continueSetup(Player p) {
		right = false;
		left = false;
		set = false;
		i = 0;
		p.getInventory().remove(PokemonGO.mi);
		p.sendMessage(Cipher.l("setup7"));
		p.sendMessage(Cipher.l("setup8"));
		p.sendMessage(Cipher.l("setup9"));
		PokemonGO.setup.remove(p);
		PokemonGO.s.getScheduler().runTask(pl, new MapTask(p.getWorld(), p, pl));
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onDrop(PlayerDropItemEvent e) {
		if (PokemonGO.setup.contains(e.getPlayer())) {
			if (e.getItemDrop().getItemStack().equals(PokemonGO.mi) || e.getPlayer().getItemInHand().equals(PokemonGO.mi)) {
				e.setCancelled(true);
				PokemonGO.setup.remove(e.getPlayer());
				e.getPlayer().getInventory().remove(PokemonGO.mi);
				e.getPlayer().sendMessage(Cipher.l("setup-cancelled"));
			}
		}
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		if (PokemonGO.setup.contains(e.getPlayer())) {
			PokemonGO.setup.remove(e.getPlayer());
			e.getPlayer().getInventory().remove(PokemonGO.mi);
		}
	}
	
	@EventHandler
	public void onInvClick(InventoryClickEvent e) {
		if (PokemonGO.setup.contains(((Player) e.getWhoClicked()))) {
			if (e.getCursor().equals(PokemonGO.mi) || e.getCurrentItem().equals(PokemonGO.mi)) {
				e.setCancelled(true);
			}
		}
	}
}
