package enums;

import java.util.*;

/**
 * Created by AriG on 6/1/17.
 */

public enum Type {
    NORMAL (new ArrayList<>(Arrays.asList("FIGHTING")), new ArrayList<>(),
            new ArrayList<>(Arrays.asList("GHOST"))),
    WATER (new ArrayList<>(Arrays.asList("ELECTRIC", "GRASS")),
            new ArrayList<>(Arrays.asList("FIRE", "WATER", "ICE", "STEEL")), new ArrayList<>()),
    FIRE (new ArrayList<>(Arrays.asList("WATER", "GROUND", "ROCK")),
            new ArrayList<>(Arrays.asList("FIRE", "GRASS", "BUG", "ICE", "STEEL", "FAIRY")), new ArrayList<>()),
    GRASS (new ArrayList<>(Arrays.asList("FIRE", "ICE", "POISON", "FLYING", "BUG")),
            new ArrayList<>(Arrays.asList("WATER", "ELECTRIC", "GRASS", "GROUND")), new ArrayList<>()),
    FIGHTING (new ArrayList<>(Arrays.asList("PSYCHIC", "FLYING", "FAIRY")),
            new ArrayList<>(Arrays.asList("BUG", "ROCK", "DARK")), new ArrayList<>()),
    PSYCHIC (new ArrayList<>(Arrays.asList("DARK", "BUG", "GHOST")),
            new ArrayList<>(Arrays.asList("FIGHTING", "PSYCHIC")), new ArrayList<>()),
    GHOST (new ArrayList<>(Arrays.asList("GHOST", "DARK")), new ArrayList<>(Arrays.asList("POISON", "BUG")),
            new ArrayList<>(Arrays.asList("NORMAL", "FIGHTING"))),
    ICE (new ArrayList<>(Arrays.asList("FIRE", "FIGHTING", "ROCK", "STEEL")),
            new ArrayList<>(Arrays.asList("ICE")), new ArrayList<>()),
    DRAGON (new ArrayList<>(Arrays.asList("DRAGON", "FAIRY")),
            new ArrayList<>(Arrays.asList("FIRE", "WATER", "GRASS", "ELECTRIC")), new ArrayList<>()),
    //Uses Gen. 6+ resistances
    STEEL (new ArrayList<>(Arrays.asList("FIRE", "FIGHTING", "GROUND")),
            new ArrayList<>(Arrays.asList("NORMAL", "GRASS", "ICE", "FLYING", "PSYCHIC", "BUG", "ROCK", "DRAGON",
                    "STEEL", "FAIRY")), new ArrayList<>(Arrays.asList("POISON"))),
    ROCK (new ArrayList<>(Arrays.asList("WATER", "GRASS", "FIGHTING", "GROUND", "STEEL")),
            new ArrayList<>(Arrays.asList("NORMAL", "FIRE", "POISON", "FLYING")), new ArrayList<>()),
    GROUND (new ArrayList<>(Arrays.asList("WATER", "GRASS", "ICE")),
            new ArrayList<>(Arrays.asList("ROCK", "POISON")), new ArrayList<>(Arrays.asList("ELECTRIC"))),
    ELECTRIC (new ArrayList<>(Arrays.asList("GROUND")),
            new ArrayList<>(Arrays.asList("ELECTRIC", "FLYING", "STEEL")), new ArrayList<>()),
    BUG (new ArrayList<>(Arrays.asList("FIRE", "FLYING", "ROCK")),
            new ArrayList<>(Arrays.asList("GROUND", "GRASS", "FIGHTING")), new ArrayList<>()),
    DARK (new ArrayList<>(Arrays.asList("BUG", "FIGHTING", "FAIRY")),
            new ArrayList<>(Arrays.asList("GHOST", "DARK")), new ArrayList<>(Arrays.asList("PSYCHIC"))),
    FLYING (new ArrayList<>(Arrays.asList("ELECTRIC", "ICE", "ROCK")),
            new ArrayList<>(Arrays.asList("GRASS", "FIGHTING", "BUG")),
            new ArrayList<>(Arrays.asList("GROUND"))),
    POISON (new ArrayList<>(Arrays.asList("GROUND", "PSYCHIC")),
            new ArrayList<>(Arrays.asList("GRASS", "FIGHTING", "POISON", "BUG", "FAIRY")),
            new ArrayList<>()),
    FAIRY (new ArrayList<>(Arrays.asList("STEEL", "POISON")),
            new ArrayList<>(Arrays.asList("FIGHTING", "BUG", "DARK")),
            new ArrayList<>(Arrays.asList("DRAGON"))),
    TYPELESS (new ArrayList<>(), new ArrayList<>(), new ArrayList<>());

    private final List<String> weakTo;
    private final List<String> resists;
    private final List<String> immuneTo;

    Type(ArrayList<String> weakTo, ArrayList<String> resists, ArrayList<String> immuneTo) {
        this.weakTo = weakTo;
        this.resists = resists;
        this.immuneTo = immuneTo;
    }

    public static double calculateTypeMod(Type attackType, List<Type> defTypes) {
        double mod = 1;
        for (Type typ : defTypes) {
            if (typ.resists.contains(attackType.toString())) {
                mod *= .5;
            } else if (typ.weakTo.contains(attackType.toString())) {
                mod *= 2;
            } else if (typ.immuneTo.contains(attackType.toString())) {
                mod *= 0;
            }
        }
        return mod;
    }

    public static double calculateSTAB(Type attackType, List<Type> monTypes) {
        return (monTypes.contains(attackType)) ? 1.5 : 1;
    }

    private static Map<Integer, Type> map = new HashMap<>();

    static {
        for (Type type : Type.values()) {
            map.put(type.ordinal(), type);
        }
    }

    public static Type valueOf(int x) {
        return map.get(x);
    }

    public static Type valueOf(Object x) {
        if (x.getClass().equals(Long.class)) {
            return map.get(((Long) x).intValue());
        }
        return map.get((int) x);

    }

}



