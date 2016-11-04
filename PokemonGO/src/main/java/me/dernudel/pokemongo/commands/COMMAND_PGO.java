package me.dernudel.pokemongo.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.dernudel.pokemongo.PokemonGO;
import me.dernudel.pokemongo.listener.DexListener;
import me.dernudel.pokemongo.util.Cipher;
import me.dernudel.pokemongo.util.Format;
import me.dernudel.pokemongo.util.Save;

public class COMMAND_PGO implements CommandExecutor {
	
	PokemonGO pl;
	
	public COMMAND_PGO(PokemonGO pl) {
		this.pl = pl;
	}
	
	@Override
	public boolean onCommand(CommandSender s, Command cmd, String lbl, String[] args) {
		if (s instanceof Player) {
			Player p = (Player) s;
			if (args.length == 1) {
				switch(args[0]) {
				case "setup":
					setup(p);
					break;
				case "dex":
					PokemonGO.d.add(p);
					DexListener.showInv(p);
					break;
				case "give":
					if (p.hasPermission("pokemongo.give") || p.hasPermission("pokemongo.admin")) {
						ItemStack is = PokemonGO.pb;
						is.setAmount(5);
						p.getInventory().addItem(is);
						p.sendMessage(Cipher.l("receive-pokeballs"));
					} else {
						p.sendMessage(Cipher.l("insuffiscient-perms"));
					}
					break;
				case "tracker":
					if (p.hasPermission("pokemongo.tracker") || p.hasPermission("pokemongo.admin")) {
						PokemonGO.trp.add(p);
						p.getInventory().addItem(PokemonGO.tr);
					} else {
						p.sendMessage(Cipher.l("insuffiscient-perms"));
						//p.sendMessage(Cipher.l("bad_perm"));
					}
					break;
				default:
					break;
				}
			} else {
				String de = Format.a("\u00A78==========" + PokemonGO.p + "\u00A77==========\n"
						+ "\u00A78||   \u00A72/pgo     \u00A7eZeigt die Hilfe\n"
						+ "\u00A78||   \u00A72/pgo setup     \u00A7eAdmin Command\n"
						+ "\u00A78||   \u00A72/pgo give     \u00A7eGibt dir Pokeb\u00A7lle\n"
						+ "\u00A78||   \u00A72/pgo tracker     \u00A7eGibt dir einen Tracker\n"
						+ "\u00A78||   \u00A72/pgo dex     \u00A7eZeigt dir deinen Pokedex\n"
						+ "\u00A78==========" + PokemonGO.p + "\u00A78==========");
				String en = Format.a("\u00A78==========" + PokemonGO.p + "\u00A77==========\n"
						+ "\u00A78||   \u00A72/pgo     \u00A7eShows the help page\n"
						+ "\u00A78||   \u00A72/pgo setup     \u00A7eAdmin Command\n"
						+ "\u00A78||   \u00A72/pgo give     \u00A7eGives Pokeballs to you\n"
						+ "\u00A78||   \u00A72/pgo tracker     \u00A7eGives the Tracker to you\n"
						+ "\u00A78||   \u00A72/pgo dex     \u00A7eShows your Pokedex\n"
						+ "\u00A78==========" + PokemonGO.p + "\u00A78==========");
				p.sendMessage(Save.langCode().equalsIgnoreCase("de") ? de : en);
			}
		} else {
			s.sendMessage(PokemonGO.p + Format.a("\u00A7cPlayer Command!"));
		}
		return true;
	}


	private void setup(Player p) {
		if (p.hasPermission("pokemongo.admin")) {
			PokemonGO.setup.add(p);
			p.sendMessage(Cipher.l("setup1"));
			p.sendMessage(Cipher.l("setup2"));
			p.getInventory().setItem(0, PokemonGO.mi);
			p.sendMessage(Cipher.l("setup3"));
			p.sendMessage(Cipher.l("setup4"));
			p.sendMessage(Cipher.l("setup5"));
			p.sendMessage(Cipher.l("setup6"));
		} else {
			p.sendMessage(Cipher.l("insuffiscient-perms"));
		}
	}

}
