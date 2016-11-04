package me.dernudel.pokemongo.util;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.google.common.io.Files;

import me.dernudel.pokemongo.PokemonGO;
import me.dernudel.pokemongo.map.Point;
import me.dernudel.pokemongo.pokemon.Pokemon;

public class Save {
	
	public static File setupf = new File(PokemonGO.c, "setup.json");

	public static File cfgf = new File(PokemonGO.c, "config.yml");
	
	
	public static boolean exist(String uuid) {
		return Save.db() ? true : new File(PokemonGO.c, "users/" + uuid + ".json").exists();
	}
	
	public static void register(String uuid) {
		if (Save.db()) {
			PokemonGO.sql.registerUser(uuid);
		} else {
			File f = new File(PokemonGO.c, "users/" + uuid + ".json");
			try {
				f.createNewFile();
			} catch (IOException e) {
				System.out.println(PokemonGO.p + "ERROR: creating user file");
				e.printStackTrace();
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	public static void updateDex(Player pl, Pokemon po) {
		if (Save.db()) {
			PokemonGO.sql.doUpdate(pl.getUniqueId().toString(), po.getPos());
		} else {
			String uuid = pl.getUniqueId().toString();
			File f = new File(PokemonGO.c + "/users/" + uuid + ".json");
			JSONObject jo = getObj(f);
			JSONArray ja = (JSONArray) jo.get("Pokemon");
			if (ja == null || ja.size() == 0) ja = new JSONArray();
			if (!ja.contains(po.getPos())) {
				ja.add(po.getPos());
			}
			jo.put("Pokemon", ja);
			saveObj(jo, f);
		}
	}

	@SuppressWarnings("rawtypes")
	public static ArrayList<Integer> getDex(Player p) {
			ArrayList<Integer> l = new ArrayList<Integer>();
		if (Save.db()) {
			l = PokemonGO.sql.getPkmn(p.getUniqueId().toString());
		} else {
			String uuid = p.getUniqueId().toString();
			File f = new File(PokemonGO.c, "/users/" + uuid + ".json");
			JSONObject jo = getObj(f);
			JSONArray ja = (JSONArray) jo.get("Pokemon");
			if (ja == null || ja.size() == 0) ja = new JSONArray();
			Iterator i = ja.iterator();
			while (i.hasNext()) {
				l.add(Integer.parseInt(i.next().toString()));
			}
		}
		return l;
	}
	
	@SuppressWarnings("unchecked")
	public static void addPoint(Point po, int index) {
		JSONObject jo = getObj(setupf);
		jo.put("Point" + index, po.toString());
		saveObj(jo, setupf);
	}

	public static Point[] getPoints() {
		Point[] ps = new Point[2];
		JSONObject jo = getObj(setupf);
		ps[0] = Point.fromString(((String) jo.get("Point1")));
		ps[1] = Point.fromString(((String) jo.get("Point2")));
		return ps;
	}
	
	@SuppressWarnings("unchecked")
	public static void hidePokemon(List<Pokemon> p, List<Location> loc) {
		JSONObject jo = getObj(setupf);
		JSONArray ja = new JSONArray();
		for (int it = 0; it<=p.size()-1; it++) {
			ja.add(String.valueOf(p.get(it).getPos()) + ":" + locToString(loc.get(it)));
		}
		jo.put("HiddenPokemon", ja);
			saveObj(jo, setupf);
		
	}
	
	static String locToString(Location loc) {
		String s = "";
		String f = s.concat("L")
		.concat(String.valueOf(loc.getX()))
		.concat(",")
		.concat(String.valueOf(loc.getY()))
		.concat(",")
		.concat(String.valueOf(loc.getZ()))
		.concat(",")
		.concat(String.valueOf(loc.getWorld().getName()))
		.concat("L");
		return f;
	}
	
	public static Location stringToLoc(String s) {
		String f = s.replace("L", "");
		String[] ss = f.split(",");
		return new Location(PokemonGO.s.getWorld(ss[3]), Double.parseDouble(ss[0]), Double.parseDouble(ss[1]), Double.parseDouble(ss[2]));
	}
	
	@SuppressWarnings("rawtypes")
	public static List<Location> locations() {
		List<Location> locs = new ArrayList<Location>();
		JSONObject jo = getObj(setupf);
			JSONArray ja = (JSONArray) jo.get("HiddenPokemon");
			Iterator it = ja.iterator();
			while (it.hasNext()) {
				String s = (String) it.next();
				String l = s.split(":")[1];
				locs.add(Save.stringToLoc(l));
			}
		return locs;
	}
	
	@SuppressWarnings("rawtypes")
	public static List<String> pkmnEntries() {
		List<String> entries = new ArrayList<String>();
		JSONObject jo = getObj(setupf);
			JSONArray ja = (JSONArray) jo.get("HiddenPokemon");
			if (ja == null) return null;
			Iterator it = ja.iterator();
			while (it.hasNext()) {
				entries.add((String) it.next());
			}
		return entries;
	}
	@SuppressWarnings("unchecked")
	public static void setup(boolean b) {
		JSONObject jo = getObj(setupf);
		jo.put("SETUP", b);
		saveObj(jo, setupf);
	}
	public static boolean setup() {
		JSONObject jo = getObj(setupf);
		return (boolean) jo.get("SETUP");
	}
	
	
	private static JSONObject getObj(File f) {
		JSONParser parser = new JSONParser();
		Object o;
		try {
			o = parser.parse(new FileReader(f));
		} catch (IOException | ParseException e) {
			o = new JSONObject();
		}
		return (JSONObject) o;
	}

	private static void saveObj(JSONObject jo, File f) {
		try (FileWriter fw = new FileWriter(f)) {
			fw.write(jo.toJSONString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	public static void removePokemon(Pokemon po, Location loc) {
		JSONObject jo = getObj(setupf);
		JSONArray ja = (JSONArray) jo.get("HiddenPokemon");
		for (int i = 0; i<= ja.size()-1; i++) {
			String f = (String) ja.get(i);
			String[] fs = f.split(":");
			int poPos = Integer.parseInt(fs[0]);
			if (po.getPos() == poPos && loc.equals(stringToLoc(fs[1]))) {
				ja.remove(i);
				break;
			}
		}
		jo.put("HiddenPokemon", ja);
		saveObj(jo, setupf);
	}
	
	protected static FileConfiguration l() {		
		File f = new File(PokemonGO.c + File.separator + "lang", langCode() + ".yml");
		return YamlConfiguration.loadConfiguration(f);
	}
	
	public static void exLang(File f, PokemonGO pl) {
	    try  {
	      pl.saveResource("lang" + File.separator + Save.langCode() + ".yml", true);
	    }
	    catch (Exception ex) {
	      try {
	        File file_en = new File(pl.getDataFolder() + File.separator + "lang", "en.yml");
	        if (!file_en.exists()) {
	          pl.saveResource("lang" + File.separator + "en.yml", true);
	        }
	        Files.copy(file_en, f);
	      }
	      catch (IOException e) {
	        e.printStackTrace();
	      }
	    }
	 }
	
	public static String langCode() {
		FileConfiguration cfg = YamlConfiguration.loadConfiguration(cfgf);
		String s = cfg.getString("lang");
		String t = "";
		if (s != null && s.equals("de")) {
			t = s;
		} else if (s!=null && s.equalsIgnoreCase("en")) {
			t = s;
		} else {
			try {
				Save.cfgf.createNewFile();
				File lf = Save.cfgf;
				FileConfiguration lc = YamlConfiguration.loadConfiguration(lf);
				lc.set("lang", "en");
				lc.save(lf);
			} catch (IOException e) {
				e.printStackTrace();
			}
			t = "en";
		}
		return t;
	}
	
	public static boolean db() {
		FileConfiguration cfg = YamlConfiguration.loadConfiguration(cfgf);
		return cfg.getBoolean("use-mysql");
	}

	public static void setupSQL() {
		File f = Save.cfgf;
		FileConfiguration cfg = YamlConfiguration.loadConfiguration(f);
		cfg.set("use-mysql", false);
		cfg.set("host", "localhost");
		cfg.set("port", "3306");
		cfg.set("username", "username");
		cfg.set("password", "password");
		cfg.set("database", "database");
		try {
			cfg.save(f);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static String host() {
		FileConfiguration cfg = YamlConfiguration.loadConfiguration(cfgf);
	return cfg.getString("host");
	}
	public static String port() {
		FileConfiguration cfg = YamlConfiguration.loadConfiguration(cfgf);
	return cfg.getString("port");
	}
	public static String database() {
		FileConfiguration cfg = YamlConfiguration.loadConfiguration(cfgf);
	return cfg.getString("database");
	}
	public static String username() {
		FileConfiguration cfg = YamlConfiguration.loadConfiguration(cfgf);
	return cfg.getString("username");
	}
	public static String password() {
		FileConfiguration cfg = YamlConfiguration.loadConfiguration(cfgf);
	return cfg.getString("password");
	}
}
