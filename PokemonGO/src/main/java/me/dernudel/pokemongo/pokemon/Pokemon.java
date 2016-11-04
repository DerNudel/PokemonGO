package me.dernudel.pokemongo.pokemon;

import me.dernudel.pokemongo.util.Cipher;
import me.dernudel.pokemongo.util.Format;

public class Pokemon {

	private int pos;
	private String name;
	private Rarity r;
	
	public Pokemon(int posc) {
		pos = posc;
		r = setRar(pos);
		name = colorByRarity(r) + nameByPos(pos);
	}
	
	public Rarity getRar() {
		return r;
	}
	
	
	private String colorByRarity(Rarity r2) {
		switch (r2) {
		case LOW:
			return Format.a("\u00A72");
		case NORMAL:
			return Format.a("\u00A71");
		case HIGH:
			return Format.a("\u00A76");
		case HIGHEST:
			return Format.a("\u00A74");
		default:
			return Format.a("\u00A7a");
		}
	}


	private Rarity setRar(int pos2) {
		if (pos <= 9) {
			return Rarity.LOW;
		} else if (pos > 9 && pos <= 36) {
			return Rarity.NORMAL;
		} else if (pos > 36 && pos <= 49) {
			return Rarity.HIGH;
		} else {
			return Rarity.HIGHEST;
		}
	}


	public String getName() { return name; }
	
	
	public int getPos() { return pos; }
	
	
	public static String nameByPos(int i) {
		String name = "";
		switch (i) {
		case 1:
			name = Cipher.ll("pkmn.bulbasaur");
			break;
		case 2:
			name = Cipher.ll("pkmn.charmander");
			break;
		case 3:
			name = Cipher.ll("pkmn.squirtle");
			break;
		case 4:
			name = Cipher.ll("pkmn.pidgey");
			break;
		case 5:
			name = Cipher.ll("pkmn.rattata");
			break;
		case 6:
			name = Cipher.ll("pkmn.nidoranm");
			break;
		case 7:
			name = Cipher.ll("pkmn.nidoranf");
			break;
		case 8:
			name = Cipher.ll("pkmn.zubat");
			break;
		case 9:
			name = Cipher.ll("pkmn.magikarp");
			break;
		case 10:
			name = Cipher.ll("pkmn.ivysaur");
			break;
		case 11:
			name = Cipher.ll("pkmn.charmeleon");
			break;
		case 12:
			name = Cipher.ll("pkmn.wartortle");
			break;
		case 13:
			name = Cipher.ll("pkmn.pidgeotto");
			break;
		case 14:
			name = Cipher.ll("pkmn.raticate");
			break;
		case 15:
			name = Cipher.ll("pkmn.pikachu");
			break;
		case 16:
			name = Cipher.ll("pkmn.raichu");
			break;
		case 17:
			name = Cipher.ll("pkmn.nidorino");
			break;
		case 18:
			name = Cipher.ll("pkmn.nidorina");
			break;
		case 19:
			name = Cipher.ll("pkmn.vulpix");
			break;
		case 20:
			name = Cipher.ll("pkmn.golbat");
			break;
		case 21:
			name = Cipher.ll("pkmn.growlithe");
			break;
		case 22:
			name = Cipher.ll("pkmn.gastly");
			break;
		case 23:
			name = Cipher.ll("pkmn.haunter");
			break;
		case 24:
			name = Cipher.ll("pkmn.eevee");
			break;
		case 25:
			name = Cipher.ll("pkmn.vaporeon");
			break;
		case 26:
			name = Cipher.ll("pkmn.jolteon");
			break;
		case 27:
			name = Cipher.ll("pkmn.flareon");
			break;
		case 28:
			name = Cipher.ll("pkmn.dratini");
			break;
		case 29:
			name = Cipher.ll("pkmn.dragonair");
			break;
		case 30:
			name = Cipher.ll("pkmn.scyther");
			break;
		case 31:
			name = Cipher.ll("pkmn.abra");
			break;
		case 32:
			name = Cipher.ll("pkmn.kadabra");
			break;
		case 33:
			name = Cipher.ll("pkmn.cubone");
			break;
		case 34:
			name = Cipher.ll("pkmn.marowak");
			break;
		case 35:
			name = Cipher.ll("pkmn.aerodactyl");
			break;
		case 36:
			name = Cipher.ll("pkmn.snorlax");
			break;
		case 37:
			name = Cipher.ll("pkmn.venusaur");
			break;
		case 38:
			name = Cipher.ll("pkmn.charizard");
			break;
		case 39:
			name = Cipher.ll("pkmn.blastoise");
			break;
		case 40:
			name = Cipher.ll("pkmn.pidgeot");
			break;
		case 41:
			name = Cipher.ll("pkmn.nidoking");
			break;
		case 42:
			name = Cipher.ll("pkmn.nidoqueen");
			break;
		case 43:
			name = Cipher.ll("pkmn.ninetales");
			break;
		case 44:
			name = Cipher.ll("pkmn.arcanine");
			break;
		case 45:
			name = Cipher.ll("pkmn.gengar");
			break;
		case 46:
			name = Cipher.ll("pkmn.onix");
			break;
		case 47:
			name = Cipher.ll("pkmn.alakazam");
			break;
		case 48:
			name = Cipher.ll("pkmn.gyarados");
			break;
		case 49:
			name = Cipher.ll("pkmn.dragonite");
			break;
		case 50:
			name = Cipher.ll("pkmn.articuno");
			break;
		case 51:
			name = Cipher.ll("pkmn.moltres");
			break;
		case 52:
			name = Cipher.ll("pkmn.zapdos");
			break;
		case 53:
			name = Cipher.ll("pkmn.mewtwo");
			break;
		case 54:
			name = Cipher.ll("pkmn.mew");
			break;
		default:
			name = "null";
			break;
		}
		return name;
	}
}