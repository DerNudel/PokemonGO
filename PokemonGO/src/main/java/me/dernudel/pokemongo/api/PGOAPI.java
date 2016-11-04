package me.dernudel.pokemongo.api;

import java.util.ArrayList;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.dernudel.pokemongo.PokemonGO;
import me.dernudel.pokemongo.pokemon.Pokemon;
import me.dernudel.pokemongo.util.Save;

public class PGOAPI {

	PokemonGO pl;
	
	public PGOAPI(PokemonGO pl) {
		this.pl = pl;
	}
	
	public ArrayList<Pokemon> getPokedex(Player player) {
		ArrayList<Pokemon> l = new ArrayList<>();
		for (int i : Save.getDex(player)) {
			l.add(new Pokemon(i));
		}
		return l;
	}
	
	public void givePokeballs(Player player, int amount) {
		ItemStack is = PokemonGO.pb;
		is.setAmount(amount);
		player.getInventory().addItem(is);
	}
}
