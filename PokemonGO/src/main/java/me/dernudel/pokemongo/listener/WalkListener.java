package me.dernudel.pokemongo.listener;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.dernudel.pokemongo.PokemonGO;
import me.dernudel.pokemongo.pokemon.Pokemon;
import me.dernudel.pokemongo.util.Cipher;
import me.dernudel.pokemongo.util.Save;

public class WalkListener implements Listener {

	PokemonGO pl;
	
	public WalkListener(PokemonGO pl) {
		this.pl = pl;
	}
	
	
	@EventHandler
	public void onWalk(PlayerMoveEvent e) {
		if (Save.setup()) {
			List<String> entries = Save.pkmnEntries();
			if (entries == null || entries.size() == 0) {
				;
			} else {
				for (String l : entries) {
					Location loc = Save.stringToLoc(l.split(":")[1]);
					if (loc.getWorld().equals(e.getPlayer().getWorld())) {
						if (loc.distance(e.getPlayer().getLocation()) <= 5) {
							showPokemon(e.getPlayer(), new Pokemon(Integer.parseInt(l.split(":")[0])), loc);
						}
					}
				}
			}
		}
	}

	private void showPokemon(Player p, Pokemon po, Location loc) {
		p.sendMessage(Cipher.l("pkmn-appear"));
		Save.removePokemon(po, loc);
		EntityType tp = null;
		int power = 1;
		switch (po.getRar()) {
		case HIGH:
			tp = EntityType.BAT;
			power = 1;
			break;
		case HIGHEST:
			tp = EntityType.BAT;
			power = 5;
			break;
		case LOW:
			tp = EntityType.CHICKEN;
			power = 1;
			break;
		case NORMAL:
			tp = EntityType.CHICKEN;
			power = 5;
			break;
		default:
			break;
		}
		Location llll = loc;
		llll.setY(llll.getY()+2);
		LivingEntity e = (LivingEntity) loc.getWorld().spawnEntity(llll, tp);
		e.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, power));
		e.setCustomName(po.getName());
		PokemonGO.cpe.add(e);
		PokemonGO.pp.put(p, po);
		PokemonGO.t.put(p, PokemonGO.s.getScheduler().scheduleSyncDelayedTask(pl, new Runnable() {
			
			@Override
			public void run() {
				e.remove();
				PokemonGO.cpe.remove(e);
				p.sendMessage(Cipher.l("pkmn-escaped"));
				PokemonGO.t.remove(p);
			}
		}, 20L*20));
	}
}
