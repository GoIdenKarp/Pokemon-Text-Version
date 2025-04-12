package enums;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by AriG on 6/4/17.
 */
public enum Weather {
    NONE, SUN, RAIN, SANDSTORM, HAIL;

    private static Map<Integer, Weather> map = new HashMap<>();

    static {
        for (Weather weather : Weather.values()) {
            map.put(weather.ordinal(), weather);
        }
    }

    public static Weather valueOf(int x) {
        return map.get(x);
    }

    public static Weather valueOf(Object x) {
        if (x.getClass().equals(Long.class)) {
            return map.get(((Long) x).intValue());
        }
        return map.get((int) x);

    }
}
