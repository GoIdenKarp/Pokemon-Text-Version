package enums;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by AriG on 6/10/17.
 */
public enum Target {
    SELF, ALLY, SINGLE_TARGET, ALL_ENEMIES, ALL_OTHERS, ALL, RANDOM_ENEMY;

    private static Map<Integer, Target> map = new HashMap<>();

    static {
        for (Target target : Target.values()) {
            map.put(target.ordinal(), target);
        }
    }

    public static Target valueOf(int x) {
        return map.get(x);
    }

    public static Target valueOf(Object x) {
        if (x.getClass().equals(Long.class)) {
            return map.get(((Long) x).intValue());
        }
        return map.get((int) x);

    }
}
