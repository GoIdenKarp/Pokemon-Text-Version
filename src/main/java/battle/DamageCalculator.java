package battle;

import actions.MoveAction;
import enums.*;
import moves.*;
import pokémon.Pokémon;
import ui.GameFrame;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static pokémon.Pokémon.*;
import static pokémon.Pokémon.HP_INDEX;

public class DamageCalculator {

    public static final int DAMAGE_FAILURE = -1;

    protected BattleSlot slotOne;
    protected BattleSlot slotTwo;
    private Weather weather;
    private GameFrame.BattlePrinter battlePrinter;

    private static final int MINIMUM_DAMAGE = 1;
    private static final int CONFUSION_HIT_POWER = 40;
    private static final List<Class<? extends Move>> powerDoublesHittingFly = new ArrayList<>(Arrays.asList(Gust.class, Twister.class));
    private static final List<Class<? extends Move>> powerDoublesHittingDig = new ArrayList<>(Arrays.asList(Earthquake.class));
    private static final List<Class<? extends Move>> specialDamageCalculation = new ArrayList<>(Arrays.asList(DragonRage.class,
            SuperFang.class, Endeavor.class, SeismicToss.class, Counter.class));


    public static int confusionHit(BattleSlot slot) {
        double atk = (double)slot.getPokémon().getStats()[ATK_INDEX];
        double def = (double)slot.getPokémon().getStats()[DEF_INDEX];
        double atkStage = Battle.getStatMod(slot.getStatMod(ATK_INDEX));
        double defStage = Battle.getStatMod(slot.getStatMod(DEF_INDEX));
        atk *= atkStage;
        def *= defStage;
        double partOne = 2.0*(double)slot.getPokémon().getLevel()/5.0 + 2.0;
        double statPiece = atk/def;
        double partThree= partOne*CONFUSION_HIT_POWER*statPiece/50.0 + 2.0;
        return Integer.max((int)partThree, MINIMUM_DAMAGE);
    }

    private static boolean isCriticalHit(MoveAction action) {
        int userStage = action.getUserSlot().getPokémon().getCritStage();
        int moveStage = action.getMove().getCritStage();
        int finalStage = userStage + moveStage;
        int critRoll = action.getUserSlot().getCritRoll();
        return (critRoll <= finalStage + 1);
    }

    public DamageCalculator(Weather weather, GameFrame.BattlePrinter battlePrinter) {

        this.weather = weather;
        this.battlePrinter = battlePrinter;
    }


    public int damageCalc(MoveAction action, BattleSlot target) {
        double typeEffectiveness = Type.calculateTypeMod(action.getMove().getType(), target.getPokémon().getType());
        battlePrinter.printMoveEffectiveness(typeEffectiveness, target.getPokémon());
        if (typeEffectiveness == 0) {
            return DAMAGE_FAILURE;
        }
        if (specialDamageCalculation.contains(action.getMove().getClass())) {
            return specialDamage(action, target);
        }
        boolean crit = isCriticalHit(action);
        if (crit) {
            battlePrinter.printCriticalHit();
            action.setWasCrit(true);

        }
        Pokémon user = action.getUserSlot().getPokémon();
        Move move = action.getMove();
        double partOne = 2*user.getLevel()/5.0 + 2;
        double power;
        if (action.getMove().getClass().equals(ElectroBall.class)) {
            power = calculateElectroBallPower(action, target);
        } else if (action.getMove().getClass().equals(LowKick.class)) {
            power = calculateLowKickPower(action, target);
        } else {
            power = move.getBasePower();
            if (action.getMove().getClass().equals(Venoshock.class) &&
                    target.getPokémon().getStatus().equals(Status.POISONED)) {
                power *= 2;
            }
        }

        power *= getPowerMods(action, target);
        //System.out.println("power = " + power);
        double statPiece = getStatPiece(action, target, crit, move);
        //System.out.println("statPiece = " + statPiece);
        double part3 = partOne*power*statPiece/50 + 2;
        //System.out.println("part3 = " + part3);
        double mod = getMod(action, target, crit);
        //System.out.println("mod = " + mod);
        if (mod == -1) {
            return -1;
        }
        double result = part3*mod;
        //System.out.println("Returning " + result);
        if (target.getSide().isReflectActive() && action.getMove().getCategory() == Category.PHYSICAL) {
            result /= 2.0;
        } else if (target.getSide().isLightScreenActive() && action.getMove().getCategory() == Category.SPECIAL) {
            result /= 2.0;
        }
        //Spread moves do 25% less damage
        if (action.getTargetSlots().size() > 1) {
            result *= 0.75;
        }
        return (result != 0) ? (int)result : 1;
    }

    /**
     * Low Kick's power varies with the weight of the target
     * @param action
     * @param target
     * @return
     */
    private double calculateLowKickPower(MoveAction action, BattleSlot target) {
        double weight = target.getPokémon().getWeight();
        if (weight < 21.8) {
            return 20;
        } else if (weight < 55.0) {
            return 40;
        } else if (weight < 110) {
            return 60;
        } else if (weight < 220.2) {
            return 80;
        } else if (weight < 440.7) {
            return 100;
        } else {
            return 120;
        }
    }

    /**
     * Some moves have specific damage calculations that need to be hardcoded. They go here
     * @param action The MoveAction to get the damage for
     * @return A number representing the damage the move will do, or DAMAGE_FAILURE
     */
    private int specialDamage(MoveAction action, BattleSlot target) {
        if (action.getMove().getClass() == DragonRage.class) {
            return 40;
        } else if (action.getMove().getClass() == SuperFang.class) {
            return Math.max(MINIMUM_DAMAGE, target.getPokémon().getCurrentHP()/2);
        } else if (action.getMove().getClass() == Endeavor.class) {
            int userHP = action.getUserSlot().getPokémon().getCurrentHP();
            int targetHP = target.getPokémon().getCurrentHP();
            if (userHP >= targetHP) {
                return DAMAGE_FAILURE;
            } else {
                return targetHP - userHP;
            }
        } else if (action.getMove().getClass() == SeismicToss.class) {
            return action.getUserSlot().getPokémon().getLevel();
        } else if (action.getMove().getClass() == Counter.class) {
            MoveAction hitBy = action.getUserSlot().getMoveHitByThisTurn();
            if (hitBy.getMove().getCategory() == Category.PHYSICAL) {
                return Math.max(MINIMUM_DAMAGE, action.getUserSlot().getDamageTakenThisTurn()*2);
            } else {
                return DAMAGE_FAILURE;
            }
        }
        // should never happen
        return DAMAGE_FAILURE;
    }

    /**
     * Electro Ball's power is calculated based on the relative Speeds of the two Pokémon
     * @return
     */
    private double calculateElectroBallPower(MoveAction action, BattleSlot target) {
        double userSpeed = action.getUserSlot().getPokémon().getStats()[SPD_INDEX];
        double targetSpeed = target.getPokémon().getStats()[SPD_INDEX];
        userSpeed *= Battle.getStatMod(action.getUserSlot().getStatMod(SPD_INDEX));
        targetSpeed *= Battle.getStatMod(target.getStatMod(SPD_INDEX));
        double quotient = userSpeed/targetSpeed;
        if (quotient >= 4) {
            return 150;
        } else if (quotient >= 3) {
            return 120;
        } else if (quotient >= 2) {
            return 80;
        } else if (quotient >= 1) {
            return 60;
        } else {
            return 40;
        }
    }

    //For any modifications that change the base power of a move during damage calc
    private double getPowerMods(MoveAction action, BattleSlot target) {
        double mod = 1;
        //Solarbeam's power is halved in weather that is not sun
        if (action.getMove().getClass() == SolarBeam.class && (weather != Weather.NONE && weather != Weather.SUN)) {
            mod *= .5;
        }
        //Some moves have their power doubled when hitting a flying opponent
        if (target.getInvulnerabilty() == Invulnerabilty.FLYING && powerDoublesHittingFly.contains(action.getMove().getClass())) {
            mod *= 2;
        }
        //Some moves have their power doubled when hitting a digging opponent
        if (target.getInvulnerabilty() == Invulnerabilty.DIGGING && powerDoublesHittingDig.contains(action.getMove().getClass())) {
            mod *= 2;
        }
        //Assurance has its power doubled if the user has taken damage this turn
        if (action.getMove().getClass() == Assurance.class && target.getDamageTakenThisTurn() > 0) {
            mod *= 2;
        }
        //Rivalry changes the power of moves depending on the genders of the attacker and defender
        if (action.getUserSlot().getPokémon().getAbility() == Ability.RIVALRY) {
            if (action.getTargetSlot().getPokémon().getGender() == Gender.MALE) {
                if (action.getTargetSlot().getPokémon().getGender() == Gender.MALE) {
                    mod *= 1.25;
                } else if (action.getTargetSlot().getPokémon().getGender() == Gender.FEMALE) {
                    mod *= .75;
                }
            } else if (action.getTargetSlot().getPokémon().getGender() == Gender.FEMALE) {
                if (action.getTargetSlot().getPokémon().getGender() == Gender.MALE) {
                    mod *= .75;
                } else if (action.getTargetSlot().getPokémon().getGender() == Gender.FEMALE) {
                    mod *= 1.25;
                }
            }
        }
        //Pursuit has double power if it's specially used
        if (Battle.isPursuit(action)) {
            if (((Pursuit)action.getMove()).isSpecialEffectActive()) {
                mod *= 2;
            }
        }

        //Fire moves have 1.5 times power if a mon has Flash Fire and gets hit with a Fire move
        if (action.getUserSlot().isFlashFire() && action.getMove().getType() == Type.FIRE) {
            mod *= 1.5;
        }
        return mod;
    }

    private double getStatPiece(MoveAction action, BattleSlot target, boolean crit, Move move) {
        int atkIndex = (move.getCategory() == Category.PHYSICAL) ? ATK_INDEX : SPATK_INDEX;
        int defIndex = (move.getCategory() == Category.PHYSICAL) ? DEF_INDEX : SPDEF_INDEX;
        double atk =  action.getUserSlot().getPokémon().getStats()[atkIndex];
        double def = target.getPokémon().getStats()[defIndex];
        int atkStage = action.getUserSlot().getStatMod(atkIndex);
        int defStage = target.getStatMod(defIndex);
        atk *= getBaseStatMods(action);
        if (!(crit && atkStage < 0)) {
            atk *= Battle.getStatMod(atkStage);
        }
        if (!(crit && defStage > 0)) {

            def *= (float) Battle.getStatMod(defStage);
        }
        return atk/def;

    }

    //For any modifications that change the base attack power of a Pokémon during damage calc
    private double getBaseStatMods(MoveAction action) {
        double mod = 1.0;
        Ability ability = action.getUserSlot().getPokémon().getAbility();
        Move move = action.getMove();
        double currHP = (double)action.getUserSlot().getPokémon().getCurrentHP();
        double maxHP = (double) action.getUserSlot().getPokémon().getMaxHP();
        double hpPercentage = currHP/maxHP;
        if (hpPercentage <= .333) {
            if (ability == Ability.BLAZE && move.getType() == Type.FIRE) {
                mod *= 1.5;
            } else if (ability == Ability.OVERGROW && move.getType() == Type.GRASS) {
                mod *= 1.5;
            } else if (ability == Ability.TORRENT && move.getType() == Type.WATER) {
                mod *= 1.5;
            } else if (ability == Ability.SWARM && move.getType() == Type.BUG) {
                mod *= 1.5;
            }
        }
        return mod;
    }

    private double getMod(MoveAction action, BattleSlot target, boolean crit) {
        // System.out.println("Starting getMod");
        double mod = 1;
        List<Type> userTypes = action.getUserSlot().getPokémon().getType();
        if (action.getUserSlot().isRoosting()) {
            userTypes.remove(Type.FLYING);
            if (userTypes.isEmpty()) {
                userTypes.add(Type.NORMAL);
            }
        }
        List<Type> targetTypes = target.getPokémon().getType();
        if (target.isRoosting()) {
            targetTypes.remove(Type.FLYING);
            if (targetTypes.isEmpty()) {
                targetTypes.add(Type.NORMAL);
            }
        }
        double typeEffectiveness = Type.calculateTypeMod(action.getMove().getType(), targetTypes);
        mod *= getWeatherMod(action);
        //System.out.println("After getWeatherMod, mod is " + mod);
        if (crit)
            mod*=2;
        mod *= action.getUserSlot().getAttackRoll();
        //System.out.println("After random roll, mod is " + mod);
        mod *= Type.calculateSTAB(action.getMove().getType(), userTypes);
        //System.out.println("after stab calc, mod is " + mod);
        mod*= typeEffectiveness;
        //System.out.println("after effectiveness, mod is " + mod);
        if (action.getMove().getCategory() == Category.PHYSICAL && action.getUserSlot().getPokémon().getStatus() != Status.NONE
                && action.getUserSlot().getPokémon().getAbility() == Ability.GUTS) {
            mod *= 1.5;
        } else if (action.getMove().getCategory() == Category.PHYSICAL && action.getUserSlot().getPokémon().getStatus() == Status.BURNED) {
            mod *= .5;
        }
        double other = getOther(action);
        mod *= other;
        //System.out.println("after other, mod is " + mod);
        return mod;
    }

    private double getWeatherMod(MoveAction action) {
        switch (action.getMove().getType()) {
            case WATER:
                if (weather == Weather.RAIN) {
                    return 1.5;
                } else if (weather == Weather.SUN) {
                    return .5;
                }
                return 1;
            case FIRE:
                if (weather == Weather.SUN) {
                    return 1.5;
                } else if (weather == Weather.RAIN) {
                    return .5;
                }
                return 1;
            default:
                return 1;
        }
    }

    private double getOther(MoveAction action) {
        return 1;
    }



}
