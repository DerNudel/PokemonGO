package me.dernudel.pokemongo.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

import me.dernudel.pokemongo.PokemonGO;

public class DexListener implements Listener {
	
	public DexListener() {
	}
	
	@EventHandler
	public void onDexClick(InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();
		if (PokemonGO.d.contains(p)) {
			e.setCancelled(true);
		}
	}
	
	public static  void showInv(Player p) {
		PokemonGO.i.clear();
		for (int i = 0; i <= 53; i++) {
			ItemStack[] ii = PokemonGO.dex(p);
			PokemonGO.i.setItem(i, ii[i]);
		}
		p.openInventory(PokemonGO.i);
		
	}
	
	
	@EventHandler
	public void onInvClose(InventoryCloseEvent e) {
		if (PokemonGO.d.contains(e.getPlayer())) {
			PokemonGO.d.remove(e.getPlayer());
		}
	}
	
}
