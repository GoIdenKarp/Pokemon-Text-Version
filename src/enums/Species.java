package enums;

import exceptions.BadNameException;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by AriG on 6/10/17.
 */
public enum Species {
    BULBASAUR, IVYSAUR, VENUSAUR, CHARMANDER, CHARMELEON, CHARIZARD,SQUIRTLE, WARTORTLE, BLASTOISE, PIKACHU, RAICHU,
    PIDGEY, PIDGEOTTO, PIDGEOT, RATTATA, RATICATE, CATERPIE, METAPOD, BUTTERFREE, WEEDLE, KAKUNA, BEEDRILL, MANKEY,
    PRIMEAPE, SPEAROW, FEAROW, EKANS, ARBOK, SANDSHREW, SANDSLASH, VULPIX;


    public static Species map(String speciesName) throws BadNameException {
        switch (speciesName) {
            case "Bulbasaur":
                return BULBASAUR;
            case "Ivysaur":
                return IVYSAUR;
            case "Venusaur":
                return VENUSAUR;
            case "Charmander":
                return CHARMANDER;
            case "Charmeleon":
                return CHARMELEON;
            case "Charizard":
                return CHARIZARD;
            case "Squirtle":
                return SQUIRTLE;
            case "Wartortle":
                return WARTORTLE;
            case "Blastoise":
                return BLASTOISE;
            case "Caterpie":
                return CATERPIE;
            case "Metapod":
                return METAPOD;
            case "Butterfree":
                return BUTTERFREE;
            case "Weedle":
                return WEEDLE;
            case "Kakuna":
                return KAKUNA;
            case "Beedrill":
                return BEEDRILL;
            case "Pidgey":
                return PIDGEY;
            case "Pidgeotto":
                return PIDGEOTTO;
            case "Pidgeot":
                return PIDGEOT;
            case "Rattata":
                return RATTATA;
            case "Raticate":
                return RATICATE;
            case "Spearow":
                return SPEAROW;
            case "Fearow":
                return FEAROW;
            case "Ekans":
                return EKANS;
            case "Arbok":
                return ARBOK;
            case "Pikachu":
                return PIKACHU;
            case "Raichu":
                return RAICHU;
            case "Sandshrew":
                return SANDSHREW;
            case "Sandslash":
                return SANDSLASH;
            case "Mankey":
                return MANKEY;
            case "Primeape":
                return PRIMEAPE;
            case "Vulpix":
                return VULPIX;
            default:
                throw new BadNameException(speciesName);
        }
    }

    private static Map<Integer, Species> ordinalMap = new HashMap<>();

    static {
        for (Species species : Species.values()) {
            ordinalMap.put(species.ordinal(), species);
        }
    }

    public static Species valueOf(int x) {
        return ordinalMap.get(x);
    }

    public static Species valueOf(Object x) {
        if (x.getClass().equals(Long.class)) {
            return ordinalMap.get(((Long) x).intValue());
        }
        return ordinalMap.get((int) x);

    }
}
