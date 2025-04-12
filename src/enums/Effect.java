package enums;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by AriG on 6/3/17.
 */
public enum Effect {
    NONE, FLY, TARGET_DEF_DOWN, PARATEN, TARGET_ATK_DOWN, PARALYZE, SELF_EVADE_UP, PARATHIRTY, SELF_SPEED_UP_SHARP,
    QUARTER_RECOIL, BURNTEN, TARGET_ACC_DOWN, TARGET_SPD_DOWN_SHARP, BURN_TEN_FLINCH, BURN, BURN_THIRD_RECOIL,
    FLINCH_THIRTY, SELF_DEF_UP, SPD_DOWN_TEN, FLINCH_TEN, PROTECT, CONFUSE_TWENTY, SELF_DEF_UP_SHARP, RAIN,
    SPDEF_DOWN_TEN, POISON, SLEEP, TARGET_EVADE_DOWN_SHARP, GROWTH, RESTORE_SUN, LIGHT_SCREEN, REFLECT, REMOVE_HAZARDS,
    FIRE_SPIN, LEECH_SEED, WORRY_SEED, LIFT_PROTECT, TARGET_ATK_DOWN_SHARP, FOCUS_ENERGY, DEF_DOWN_TWETNY,
    FLINCH_TWENTY, CONFUSE_THIRTY, TAILWIND, FORCE_SWITCH, RESTORE, MIRROR_MOVE, SELF_ATK_UP_SHARP, STRUGGLE,
    TARGET_SPD_DOWN, BUG_BITE, CONFUSE_TEN, RAISE_ALL_TEN, CONFUSE, SAFEGUARD, CAPTIVATE, QUIVER_DANCE, POISON_THIRTY,
    POISON_TWENTY, TOXIC_SPIKES, FELL_STINGER, TARGET_DEF_DOWN_SHARP, TAUNT, SWITCH_USER, ENCORE, WRAP, HAZE, TOXIC, DIG, HELPING_HAND, SELF_ATK_DEF_DOWN, SELF_SPATK_UP_SHARP;

    private static Map<Integer, Effect> map = new HashMap<>();

    static {
        for (Effect effect : Effect.values()) {
            map.put(effect.ordinal(), effect);
        }
    }

    public static Effect valueOf(int x) {
        return map.get(x);
    }

    public static Effect valueOf(Object x) {
        if (x.getClass().equals(Long.class)) {
            return map.get(((Long) x).intValue());
        }
        return map.get((int) x);

    }

}
