package enums;

import java.util.HashMap;
import java.util.Map;

public enum BindingEffect {
    WRAP, FIRE_SPIN;

    private static Map<Integer, BindingEffect> map = new HashMap<>();

    static {
        for (BindingEffect effect : BindingEffect.values()) {
            map.put(effect.ordinal(), effect);
        }
    }

    public static BindingEffect valueOf(int x) {
        return map.get(x);
    }

    public static BindingEffect valueOf(Object x) {
        if (x.getClass().equals(Long.class)) {
            return map.get(((Long) x).intValue());
        }
        return map.get((int) x);

    }
}
