package enums;

import java.util.HashMap;
import java.util.Map;

public enum Gender {
    MALE, FEMALE, NONE;

    private static Map<Integer, Gender> map = new HashMap<>();

    static {
        for (Gender gender : Gender.values()) {
            map.put(gender.ordinal(), gender);
        }
    }

    public static Gender valueOf(int x) {
        return map.get(x);
    }

    public static Gender valueOf(Object x) {
        if (x.getClass().equals(Long.class)) {
            return map.get(((Long) x).intValue());
        }
        return map.get((int) x);

    }
}
