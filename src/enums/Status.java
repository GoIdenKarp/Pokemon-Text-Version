package enums;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by AriG on 6/3/17.
 */
public enum Status {
    NONE, PARALYZED, BURNED, POISONED, BADLY_POISONED, ASLEEP, FROZEN, FAINTED;

    private static Map<Integer, Status> map = new HashMap<>();

    static {
        for (Status status : Status.values()) {
            map.put(status.ordinal(), status);
        }
    }

    public static Status valueOf(int x) {
        return map.get(x);
    }

    public static Status valueOf(Object x) {
        if (x.getClass().equals(Long.class)) {
            return map.get(((Long) x).intValue());
        }
        return map.get((int) x);

    }
}
