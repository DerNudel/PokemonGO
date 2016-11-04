package me.dernudel.pokemongo.api;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import me.dernudel.pokemongo.pokemon.Pokemon;

public final class PokemonCaughtEvent extends Event {
	
	private Player p;
	private Pokemon pk;
	
	public PokemonCaughtEvent(Player p, Pokemon pk) {
		this.p = p;
		this.pk = pk;
	}
	
	public Player getPlayer() { return p; }
	
	public Pokemon getPokemon() { return pk; }
	
	private static final HandlerList handlers = new HandlerList();

	public HandlerList getHandlers() {
	    return handlers;
	}

	public static HandlerList getHandlerList() {
	    return handlers;
	}
}
