package me.dernudel.pokemongo;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import me.dernudel.pokemongo.api.PGOAPI;
import me.dernudel.pokemongo.commands.COMMAND_PGO;
import me.dernudel.pokemongo.listener.CatchListener;
import me.dernudel.pokemongo.listener.DexListener;
import me.dernudel.pokemongo.listener.JoinListener;
import me.dernudel.pokemongo.listener.MarkerListener;
import me.dernudel.pokemongo.listener.TrackerListener;
import me.dernudel.pokemongo.listener.WalkListener;
import me.dernudel.pokemongo.pokemon.Pokemon;
import me.dernudel.pokemongo.sql.MySQL;
import me.dernudel.pokemongo.util.Cipher;
import me.dernudel.pokemongo.util.Format;
import me.dernudel.pokemongo.util.Save;

public class PokemonGO extends JavaPlugin {
	//prefix
	public static final String p = Format.a("\u00A74[\u00A71Pokemon\u00A76GO\u00A74]\u00A7r ");
	//data folder
	public static File c;
	//Server
	public static Server s;
	//active Setup?
	public static List<Player> setup = new ArrayList<Player>();
	//active dex cmd?
	public static List<Player> d = new ArrayList<Player>();
	//Marker item
	public static ItemStack mi = new ItemStack(Material.DIAMOND_AXE);
	//Pokeballs
	public static ItemStack pb = new ItemStack(Material.SNOW_BALL);
	//Tracker
	public static ItemStack tr = new ItemStack(Material.COMPASS);
	//PokeDex
	public static Inventory i;
	//Chat Pokemon Entity List
	public static List<Entity> cpe = new ArrayList<Entity>();
	// List fr Players
	public static HashMap<Player, Integer> t = new HashMap<Player, Integer>(); 
	public static HashMap<Player, Pokemon> pp = new HashMap<Player, Pokemon>();
	public static List<Player> lp = new ArrayList<Player>();
	//Tracker Players
	public static List<Player> trp = new ArrayList<Player>();
	// Stops activated
	public static HashMap<Player, List<Location>> stops = new HashMap<Player, List<Location>>();
	
	PGOAPI api;
	public static MySQL sql;
	
	@Override
	public void onEnable() {
		c = getDataFolder();
		c.mkdirs();
		new File(c, "users/").mkdirs();
		s = getServer();
		
		ItemMeta mimeta = mi.getItemMeta();
		mimeta.setDisplayName(Format.a("\u00A74Marker Item"));
		mi.setItemMeta(mimeta);

		ItemMeta im = pb.getItemMeta();
		im.setDisplayName(Format.a("\u00A7cPoke\u00A7fball"));
		pb.setItemMeta(im);
		
		ItemMeta imt = tr.getItemMeta();
		imt.setDisplayName(Format.a("\u00A78Tracker"));
		tr.setItemMeta(imt);
		
		i = PokemonGO.s.createInventory(null, 6*9, Format.a("\u00A74Pokedex"));
		
		if (Save.setupf == null || !Save.setupf.exists()) {
			try {
				Save.setupf.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		if (!Save.cfgf.exists()) {
			try {
				Save.cfgf.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
			Save.setupSQL();
		}
		
		File lang = new File(PokemonGO.c + File.separator + "lang", Save.langCode() + ".yml");
		if (lang == null || !lang.exists()) {
			Save.exLang(lang, this);
		}
		if (Save.pkmnEntries() == null || Save.pkmnEntries().size() == 0) Save.setup(false);
		
		if (Save.db()) {
			sql = new MySQL();
			String host = Save.host();
			String port = Save.port();
			String database = Save.database();
			String user = Save.username();
			String password = Save.password();
			if (!sql.connect(host, port, database, user, password)) {
				System.out.println("SQL ERROR");
			}
		}
		
		//register Listeners
		getServer().getPluginManager().registerEvents(new DexListener(), this);
		getServer().getPluginManager().registerEvents(new JoinListener(), this);
		getServer().getPluginManager().registerEvents(new MarkerListener(this), this);
		getServer().getPluginManager().registerEvents(new WalkListener(this), this);
		getServer().getPluginManager().registerEvents(new CatchListener(), this);
		getServer().getPluginManager().registerEvents(new TrackerListener(), this);
		
		//register COMMANDS
		getCommand("pgo").setExecutor(new COMMAND_PGO(this));
		
		System.out.println(p + "\u00A7aenabled");
		super.onEnable();
	}
	
	@Override
	public void onDisable() {
		System.out.println(p + "\u00A7cenabled");
		
		super.onDisable();
	}
	
	public PGOAPI getAPI() {
		return new PGOAPI(this);
	}
	
	public static ItemStack[] dex(Player p) {
		ItemStack[] is = new ItemStack[54];
		ArrayList<Integer> l = Save.getDex(p);
		for (int i = 1; i <= 54; i++) {
			short color = 0;
			if (i <= 9) {
				color = 5;
			} else if (i > 9 && i <= 36) {
				color = 9;
			} else if (i > 36 && i <= 49) {
				color = 4;
			} else {
				color = 14;
			}
			short dmg = (short) (l.contains(i) ? color : 7);
			is[i-1] = new ItemStack(Material.STAINED_GLASS_PANE, i, dmg);
			String name = l.contains(i) ? new Pokemon(i).getName() : "???";
			ItemMeta m = is[i-1].getItemMeta();
			m.setDisplayName(name);
			is[i-1].setItemMeta(m);
		}
		return is;
	}
	
	//Map ERROR handling
	public static enum err {
		SIZE,
		POS;
	}
	
	public static void err(err e, Player p) {
		switch (e) {
		case SIZE:
			p.sendMessage(Cipher.l("err_size"));
			break;
		case POS:
			p.sendMessage(Cipher.l("err_pos"));
			break;
		default:
			break;
		}
	}

	public static void alarmAdmins() {
		for (Player p : s.getOnlinePlayers()) {
			if (p.hasPermission("pokemongo.admin")) {
				p.sendMessage(Cipher.l("all-pkmn-discovered"));
			}
		}
	}
}
