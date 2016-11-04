package me.dernudel.pokemongo.map;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import me.dernudel.pokemongo.PokemonGO;
import me.dernudel.pokemongo.pokemon.Pokemon;
import me.dernudel.pokemongo.util.Cipher;
import me.dernudel.pokemongo.util.Save;

public class MapTask implements Runnable{

	World w;
	Random r;
	Player p;
	PokemonGO pl;
	
	public MapTask(World w, Player p, PokemonGO pl) {
		this.w = w;
		this.p = p;
		this.pl = pl;
		r = new Random();
	}
	
	@Override
	public void run() {
		int x1, x2, z1, z2;
		x1 = (int) Math.round(Save.getPoints()[0].x);
		x2 = (int) Math.round(Save.getPoints()[1].x);
		z1 = (int) Math.round(Save.getPoints()[0].z);
		z2 = (int) Math.round(Save.getPoints()[1].z);
		
		long blocks = Math.abs(x1-x2)*Math.abs(z1-z2);
		
		if (blocks <= 16*16*2) {
			PokemonGO.err(PokemonGO.err.SIZE, p);
		}
		
		int pokAmount = (int) Math.round(blocks/Math.pow(r.nextFloat()*16, 2));
		List<Chunk> chunks = new ArrayList<Chunk>();
		if (x1 == x2 || z1 == z2) {
			PokemonGO.err(PokemonGO.err.POS, p); return;
		}
		for (int i = Math.min(x1, x2); i <= Math.max(x1, x2); i += 16) {
			for (int z = Math.min(z1, z2); z <= Math.max(z1, z2); z += 16) {
				chunks.add(new Chunk(new Point(i, z), new Point(i+15, z+15)));
			}
		}
		putPoks(pokAmount, chunks);
	}

	private void putPoks(int pokAmount, List<Chunk> chunks) {
		List<Pokemon> pkmn = new ArrayList<Pokemon>();
		List<Location> loc = new ArrayList<Location>();
		for (Chunk c : chunks) {
			Point ps = c.blockAt(Math.round(r.nextFloat()*265));
			for(int y = 256; y >= 0; y--) {
				Block b = w.getBlockAt((int) ps.x, y, (int) ps.z);
				if (b.getType().equals(Material.AIR) 
						&& (!(w.getBlockAt((int) ps.x, y-1, (int) ps.z).getType().equals(Material.AIR)))){
					
					int ix = r.nextInt(100)+1;
					
					int posc = 0;
					
					if (ix <= 2) {
						posc = r.nextInt(5) + 50;
					} else if (ix > 2 && ix <= 15) {
						posc = r.nextInt(15) + 36;
					} else if (ix > 15 && ix <= 50) {
						posc = r.nextInt(28) + 9;
					} else {
						posc = r.nextInt(9) + 1;
					}
					
					loc.add(b.getLocation());
					pkmn.add(new Pokemon(posc));
					
					
					pokAmount--;
					if (pokAmount <= 0) {
						Save.setup(true);
						p.sendMessage(Cipher.l("map-completed"));
						return;
					}
					break;
				}
			}
		}
		Save.setup(true);
		p.sendMessage(Cipher.l("map-completed"));
		Save.hidePokemon(pkmn, loc);
	}

}
