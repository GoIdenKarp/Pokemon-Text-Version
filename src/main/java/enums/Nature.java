package enums;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by AriG on 6/2/17.
 */
public enum Nature {
    HARDY, LONELY, BRAVE, ADAMANT, NAUGHTY, BOLD, DOCILE, RELAXED, IMPISH, LAX, TIMID, HASTY, SERIOUS, JOLLY, NAIVE,
    MODEST, MILD, QUIET, BASHFUL, RASH, CALM, GENTLE, SASSY, CAREFUL, QUIRKY;


    private final static List<Nature> VALUES = Collections.unmodifiableList(Arrays.asList(values()));
    private final static int SIZE = VALUES.size();

    private final static List<Nature> ATK_UP = Collections.unmodifiableList(Arrays.asList(LONELY, BRAVE, ADAMANT, NAUGHTY));
    private final static List<Nature> DEF_UP = Collections.unmodifiableList(Arrays.asList(BOLD, RELAXED, IMPISH, LAX));
    private final static List<Nature> SPATK_UP = Collections.unmodifiableList(Arrays.asList(MODEST, MILD, QUIET, RASH));
    private final static List<Nature> SPDEF_UP = Collections.unmodifiableList(Arrays.asList(CALM, GENTLE, SASSY, CAREFUL));
    private final static List<Nature> SPD_UP = Collections.unmodifiableList(Arrays.asList(TIMID, HASTY, JOLLY, NAIVE));
    private final static List<Nature> ATK_DOWN = Collections.unmodifiableList(Arrays.asList(BOLD, MODEST, CALM, TIMID));
    private final static List<Nature> DEF_DOWN = Collections.unmodifiableList(Arrays.asList(LONELY, MILD, GENTLE, HASTY));
    private final static List<Nature> SPATK_DOWN = Collections.unmodifiableList(Arrays.asList(ADAMANT, IMPISH, CAREFUL, JOLLY));
    private final static List<Nature> SPDEF_DOWN = Collections.unmodifiableList(Arrays.asList(NAUGHTY, LAX, RASH, NAIVE));
    private final static List<Nature> SPD_DOWN = Collections.unmodifiableList(Arrays.asList(BRAVE, RELAXED, QUIET, SASSY));

    public final static List<List<Nature>> HELPFUL_NATURES = Collections.unmodifiableList(Arrays.asList(
            null, ATK_UP, DEF_UP, SPATK_UP, SPDEF_UP, SPD_UP));
    public final static List<List<Nature>> HINDERING_NATURES = Collections.unmodifiableList(Arrays.asList(
            null, ATK_DOWN, DEF_DOWN, SPATK_DOWN, SPDEF_DOWN, SPD_DOWN));


    public static Nature getRandomNature() {
        return VALUES.get(ThreadLocalRandom.current().nextInt(SIZE));
    }

    private static Map<Integer, Nature> map = new HashMap<>();

    static {
        for (Nature nature : Nature.values()) {
            map.put(nature.ordinal(), nature);
        }
    }

    public static Nature valueOf(int x) {
        return map.get(x);
    }

    public static Nature valueOf(Object x) {
        if (x.getClass().equals(Long.class)) {
            return map.get(((Long) x).intValue());
        }
        return map.get((int) x);

    }

}
