package enums;

import java.util.HashMap;
import java.util.Map;

public enum EvolveMethod {
    NONE, LEVEL, FRIENDSHIP, STONE, TRADE;

    private static Map<Integer, EvolveMethod> map = new HashMap<>();

    static {
        for (EvolveMethod method : EvolveMethod.values()) {
            map.put(method.ordinal(), method);
        }
    }

    public static EvolveMethod valueOf(int x) {
        return map.get(x);
    }

    public static EvolveMethod valueOf(Object x) {
        if (x.getClass().equals(Long.class)) {
            return map.get(((Long) x).intValue());
        }
        return map.get((int) x);

    }
}
