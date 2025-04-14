package enums;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by AriG on 6/3/17.
 */
public enum GrowthRate {
    ERRATIC, FAST, MEDIUM_FAST, MEDIUM_SLOW, SLOW, FLUCTUATING;

    private static Map<Integer, GrowthRate> map = new HashMap<>();

    static {
        for (GrowthRate rate : GrowthRate.values()) {
            map.put(rate.ordinal(), rate);
        }
    }

    public static GrowthRate valueOf(int x) {
        return map.get(x);
    }

    public static GrowthRate valueOf(Object x) {
        if (x.getClass().equals(Long.class)) {
            return map.get(((Long) x).intValue());
        }
        return map.get((int) x);

    }
}
