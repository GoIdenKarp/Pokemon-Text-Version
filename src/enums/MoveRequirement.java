package enums;

import java.util.HashMap;
import java.util.Map;

public enum MoveRequirement {
    NONE, SURF, STRENGTH, ROCK_SMASH, EVENT;

    public static MoveRequirement map(String toMap) {
        switch (toMap) {
            case "Surf":
                return SURF;
            case "Strength":
                return STRENGTH;
            case "RockSmash":
                return ROCK_SMASH;
            case "Event":
                return EVENT;
            default:
                return NONE;
        }
    }

    private static Map<Integer, MoveRequirement> ordinalMap = new HashMap<>();

    static {
        for (MoveRequirement requirement : MoveRequirement.values()) {
            ordinalMap.put(requirement.ordinal(), requirement);
        }
    }

    public static MoveRequirement valueOf(int x) {
        return ordinalMap.get(x);
    }

    public static MoveRequirement valueOf(Object x) {
        if (x.getClass().equals(Long.class)) {
            return ordinalMap.get(((Long) x).intValue());
        }
        return ordinalMap.get((int) x);

    }

}
