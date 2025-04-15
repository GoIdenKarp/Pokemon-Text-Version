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
            case "BULBASAUR":
                return BULBASAUR;
            case "IVYSAUR":
                return IVYSAUR;
            case "VENUSAUR":
                return VENUSAUR;
            case "CHARMANDER":
                return CHARMANDER;
            case "CHARMELEON":
                return CHARMELEON;
            case "CHARIZARD":
                return CHARIZARD;
            case "SQUIRTLE":
                return SQUIRTLE;
            case "WARTORTLE":
                return WARTORTLE;
            case "BLASTOISE":
                return BLASTOISE;
            case "CATERPIE":
                return CATERPIE;
            case "METAPOD":
                return METAPOD;
            case "BUTTERFREE":
                return BUTTERFREE;
            case "WEEDLE":
                return WEEDLE;
            case "KAKUNA":
                return KAKUNA;
            case "BEEDRILL":
                return BEEDRILL;
            case "PIDGEY":
                return PIDGEY;
            case "PIDGEOTTO":
                return PIDGEOTTO;
            case "PIDGEOT":
                return PIDGEOT;
            case "RATTATA":
                return RATTATA;
            case "RATICATE":
                return RATICATE;
            case "SPEAROW":
                return SPEAROW;
            case "FEAROW":
                return FEAROW;
            case "EKANS":
                return EKANS;
            case "ARBOK":
                return ARBOK;
            case "PIKACHU":
                return PIKACHU;
            case "RAICHU":
                return RAICHU;
            case "SANDSHREW":
                return SANDSHREW;
            case "SANDSLASH":
                return SANDSLASH;
            case "NIDORANF":
                return NIDORANF;
            case "NIDORINA":
                return NIDORINA;
            case "NIDOQUEEN":
                return NIDOQUEEN;
            case "NIDORANM":
                return NIDORANM;
            case "NIDORINO":
                return NIDORINO;
            case "NIDOKING":
                return NIDOKING;
            case "VULPIX":
                return VULPIX;
            case "NINETALES":
                return NINETALES;
            case "MANKEY":
                return MANKEY;
            case "PRIMEAPE":
                return PRIMEAPE;
            default:
                System.err.println("Species not found: " + speciesName);
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
