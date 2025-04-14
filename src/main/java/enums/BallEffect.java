package enums;

import java.util.HashMap;
import java.util.Map;

public enum BallEffect {
    NONE, GREAT, ULTRA;

    private static Map<Integer, BallEffect> map = new HashMap<>();

    static {
        for (BallEffect effect : BallEffect.values()) {
            map.put(effect.ordinal(), effect);
        }
    }

    public static BallEffect valueOf(int x) {
        return map.get(x);
    }

    public static BallEffect valueOf(Object x) {
        if (x.getClass().equals(Long.class)) {
            return map.get(((Long) x).intValue());
        }
        return map.get((int) x);

    }
}
