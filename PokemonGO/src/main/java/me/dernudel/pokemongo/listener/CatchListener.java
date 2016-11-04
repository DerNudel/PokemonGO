package me.dernudel.pokemongo.listener;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.inventory.meta.FireworkMeta;

import me.dernudel.pokemongo.PokemonGO;
import me.dernudel.pokemongo.api.PokemonCaughtEvent;
import me.dernudel.pokemongo.pokemon.Pokemon;
import me.dernudel.pokemongo.util.Cipher;
import me.dernudel.pokemongo.util.Save;

public class CatchListener implements Listener {
	
	
	@EventHandler
	public void onDmg(EntityDamageByEntityEvent e) {
		if (e.getCause().equals(DamageCause.PROJECTILE)) {
			if (e.getDamager() != null) {
				if (e.getDamager() instanceof Projectile && e.getDamager() instanceof Snowball) {
					Snowball sb = (Snowball) e.getDamager();
					if (sb.getShooter() instanceof Player) {
						Player p = (Player) sb.getShooter();
						if(e.getEntity() != null) {
							if (PokemonGO.cpe.contains(e.getEntity()) && PokemonGO.lp.contains(p)) {
								catchPokemon(p, e.getEntity());
							}
						}
					}
				}
			}
		}
	}
	
	@EventHandler
	public void onLaunch(ProjectileLaunchEvent e) {
		Projectile p = e.getEntity();
		if (p.getShooter() instanceof Player && p instanceof Snowball) {
			PokemonGO.lp.add((Player) p.getShooter());
		}
	}
	
	@EventHandler
	public void onHit(ProjectileHitEvent e) {
		Projectile p = e.getEntity();
		if (p.getShooter() instanceof Player && p instanceof Snowball) {
			PokemonGO.lp.remove((Player) p.getShooter());
		}
	}
	

	private void catchPokemon(Player p, Entity e) {
		Firework fw = (Firework) e.getLocation().getWorld().spawnEntity(e.getLocation(), EntityType.FIREWORK);
		FireworkMeta fwm = fw.getFireworkMeta();
		fwm.addEffect(FireworkEffect.builder()
				.with(Type.BURST)
				.withColor(Color.YELLOW, Color.BLUE, Color.RED)
				.build());
		fwm.setPower(1);
		fw.setFireworkMeta(fwm);
		e.remove();
		Pokemon po = PokemonGO.pp.get(p);
		PokemonGO.s.getScheduler().cancelTask(PokemonGO.t.get(p));
		PokemonGO.t.remove(p);
		Save.updateDex(p, po);
		Bukkit.getServer().getPluginManager().callEvent(new PokemonCaughtEvent(p, po));
		p.sendMessage(Cipher.l("pkmn-caught", po.getName()));
	}
	
	
}
