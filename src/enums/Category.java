package enums;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by AriG on 6/3/17.
 */
//Enum for move categories
public enum Category {
    PHYSICAL, SPECIAL, STATUS;

    private static Map<Integer, Category> map = new HashMap<>();

    static {
        for (Category category : Category.values()) {
            map.put(category.ordinal(), category);
        }
    }

    public static Category valueOf(int x) {
        return map.get(x);
    }

    public static Category valueOf(Object x) {
        if (x.getClass().equals(Long.class)) {
            return map.get(((Long) x).intValue());
        }
        return map.get((int) x);

    }
}
