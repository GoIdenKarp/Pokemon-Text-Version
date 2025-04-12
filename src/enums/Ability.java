package enums;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by AriG on 6/3/17.
 */
public enum Ability {
    NONE, STATIC, LIMBER, OVERGROW, BLAZE, TORRENT, OVERCOAT, INSOMNIA, KEEN_EYE,
    TANGLED_FEET, RUN_AWAY, GUTS, SHIELD_DUST, SHED_SKIN, OBLIVIOUS, COMPOUND_EYES, SWARM, VITAL_SPIRIT, ANGER_POINT,
    INTIMIDATE, SAND_VEIL, POISON_POINT, RIVALRY, FLASH_FIRE;

    private static Map<Integer, Ability> map = new HashMap<>();

    static {
        for (Ability ability : Ability.values()) {
            map.put(ability.ordinal(), ability);
        }
    }

    public static Ability valueOf(int x) {
        return map.get(x);
    }

    public static Ability valueOf(Object x) {
        if (x.getClass().equals(Long.class)) {
            return map.get(((Long) x).intValue());
        }
        return map.get((int) x);

    }
}
