package enums;

import java.util.HashMap;
import java.util.Map;

public enum Invulnerabilty {
    NONE, FLYING, DIGGING, DIVING, PROTECTED;

    private static Map<Integer, Invulnerabilty> map = new HashMap<>();

    static {
        for (Invulnerabilty invulnerabilty : Invulnerabilty.values()) {
            map.put(invulnerabilty.ordinal(), invulnerabilty);
        }
    }

    public static Invulnerabilty valueOf(int x) {
        return map.get(x);
    }

    public static Invulnerabilty valueOf(Object x) {
        if (x.getClass().equals(Long.class)) {
            return map.get(((Long) x).intValue());
        }
        return map.get((int) x);

    }
}
