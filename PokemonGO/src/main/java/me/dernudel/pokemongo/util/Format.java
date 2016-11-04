package me.dernudel.pokemongo.util;

import org.bukkit.ChatColor;

public class Format {

	
	public static String a(String b) {
		return b.replace("\u00A71", ChatColor.DARK_BLUE.toString())
				.replace("\u00A72", ChatColor.DARK_GREEN.toString())
				.replace("\u00A73", ChatColor.DARK_AQUA.toString())
				.replace("\u00A74", ChatColor.DARK_RED.toString())
				.replace("\u00A75", ChatColor.DARK_PURPLE.toString())
				.replace("\u00A76", ChatColor.GOLD.toString())
				.replace("\u00A77", ChatColor.GRAY.toString())
				.replace("\u00A78", ChatColor.DARK_GRAY.toString())
				.replace("\u00A79", ChatColor.BLUE.toString())
				.replace("\u00A70", ChatColor.BLACK.toString())
				.replace("\u00A7a", ChatColor.GREEN.toString())
				.replace("\u00A7b", ChatColor.AQUA.toString())
				.replace("\u00A7c", ChatColor.RED.toString())
				.replace("\u00A7d", ChatColor.LIGHT_PURPLE.toString())
				.replace("\u00A7e", ChatColor.YELLOW.toString())
				.replace("\u00A7f", ChatColor.WHITE.toString())
				.replace("\u00A7r", ChatColor.RESET.toString())
				.replace("\u00A7l", ChatColor.BOLD.toString())
				.replace("\u00A7n", ChatColor.UNDERLINE.toString())
				.replace("\u00A7m", ChatColor.STRIKETHROUGH.toString())
				.replace("\u00A7k", ChatColor.MAGIC.toString())
				.replace("\u00A7o", ChatColor.ITALIC.toString());
	}
	
}
