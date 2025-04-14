package enums;

import java.util.HashMap;
import java.util.Map;

public enum HealEffect {
    HEAL_TWENTY, HEAL_FIFTY, HEAL_PARALYZE, HEAL_POISON;

    private static Map<Integer, HealEffect> map = new HashMap<>();

    static {
        for (HealEffect effect : HealEffect.values()) {
            map.put(effect.ordinal(), effect);
        }
    }

    public static HealEffect valueOf(int x) {
        return map.get(x);
    }

    public static HealEffect valueOf(Object x) {
        if (x.getClass().equals(Long.class)) {
            return map.get(((Long) x).intValue());
        }
        return map.get((int) x);

    }
}
