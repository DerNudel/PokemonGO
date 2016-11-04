package me.dernudel.pokemongo.util;

import me.dernudel.pokemongo.PokemonGO;

public class Cipher {

	public static String l(String s) {
		return Format.a(PokemonGO.p + Save.l().getString(s));
	}
	public static String l(String s, String ss) {
		return Format.a(PokemonGO.p + String.format(Save.l().getString(s), ss));
	}
	public static String ll(String s) {
		return Format.a(Save.l().getString(s));
	}
}
