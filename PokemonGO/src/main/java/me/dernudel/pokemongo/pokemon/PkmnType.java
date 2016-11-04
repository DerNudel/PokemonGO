package me.dernudel.pokemongo.pokemon;

import me.dernudel.pokemongo.util.Cipher;

public enum PkmnType {

	
	
	WATER(Cipher.ll("type.water")),
	FIRE(Cipher.ll("type.fire")),
	GRASS(Cipher.ll("type.grass")),
	FLYING(Cipher.ll("type.flying")),
	GROUND(Cipher.ll("type.ground")),
	ELECTRIC(Cipher.ll("type.electric")),
	PSYCHIC(Cipher.ll("type.psychic")),
	DRAGON(Cipher.ll("type.dragon")),
	NORMAL(Cipher.ll("type.normal")),
	BUG(Cipher.ll("type.bug")),
	DARK(Cipher.ll("type.dark")),
	ROCK(Cipher.ll("type.rock"));
	
	
	String t;
	
	PkmnType(String t) {
		this.t = t;
	}
	
	public String toString() {
		return t;
	}
}
