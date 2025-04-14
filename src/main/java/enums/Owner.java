package enums;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by AriG on 6/10/17.
 */
public enum Owner {
    PLAYER, WILD, TRAINER;

    private static Map<Integer, Owner> map = new HashMap<>();

    static {
        for (Owner owner : Owner.values()) {
            map.put(owner.ordinal(), owner);
        }
    }

    public static Owner valueOf(int x) {
        return map.get(x);
    }

    public static Owner valueOf(Object x) {
        if (x.getClass().equals(Long.class)) {
            return map.get(((Long) x).intValue());
        }
        return map.get((int) x);

    }
}
