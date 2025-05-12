package pokémon;

import enums.Owner;
import enums.Species;

/**
 * Created by AriG on 6/10/17.
 */
public class PokémonFactory {

    public PokémonFactory() {
    }

    public Pokémon makePokémon(Species species, int level, Owner owner) {
        switch (species) {
            case BULBASAUR:
                return new Bulbasaur(level, owner);
            case IVYSAUR:
                return new Ivysaur(level, owner);
            case VENUSAUR:
                return new Venusaur(level, owner);
            case CHARMANDER:
                return new Charmander(level, owner);
            case CHARMELEON:
                return new Charmeleon(level, owner);
            case CHARIZARD:
                return new Charizard(level, owner);
            case SQUIRTLE:
                return new Squirtle(level, owner);
            case WARTORTLE:
                return new Wartortle(level, owner);
            case BLASTOISE:
                return new Blastoise(level, owner);
            case CATERPIE:
                return new Caterpie(level, owner);
            case METAPOD:
                return new Metapod(level, owner);
            case BUTTERFREE:
                return new Butterfree(level, owner);
            case WEEDLE:
                return new Weedle(level, owner);
            case KAKUNA:
                return new Kakuna(level, owner);
            case BEEDRILL:
                return new Beedrill(level, owner);
            case PIDGEY:
                return new Pidgey(level, owner);
            case PIDGEOTTO:
                return new Pidgeotto(level, owner);
            case PIDGEOT:
                return new Pidgeot(level, owner);
            case RATTATA:
                return new Rattata(level, owner);
            case RATICATE:
                return new Raticate(level, owner);
            case SPEAROW:
                return new Spearow(level, owner);
            case FEAROW:
                return new Fearow(level, owner);
            case EKANS:
                return new Ekans(level, owner);
            case ARBOK:
                return new Arbok(level, owner);
            case PIKACHU:
                return new Pikachu(level, owner);
            case RAICHU:
                return new Raichu(level, owner);
            case SANDSHREW:
                return new Sandshrew(level, owner);
            case SANDSLASH:
                return new Sandslash(level, owner);
            case NIDORANF:
                return new NidoranF(level, owner);
            case NIDORINA:
                return new Nidorina(level, owner);
            case NIDOQUEEN:
                return new Nidoqueen(level, owner);
            case NIDORANM:
                return new NidoranM(level, owner);
            case NIDORINO:
                return new Nidorino(level, owner);
            case NIDOKING:
                return new Nidoking(level, owner);
            case VULPIX:
                return new Vulpix(level, owner);
            case NINETALES:
                return new Ninetales(level, owner);
            case MANKEY:
                return new Mankey(level, owner);
            case PRIMEAPE:
                return new Primeape(level, owner);
            default:
                return new Pikachu(level, owner);
        }
    }
}
