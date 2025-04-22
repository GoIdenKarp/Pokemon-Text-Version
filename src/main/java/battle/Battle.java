package battle;

import actions.*;
import enums.*;
import game.Player;
import items.*;
import moves.*;
import pokémon.Pokémon;
import pokémon.PokémonFactory;
import trainer.Trainer;
import ui.GameFrame;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

import static pokémon.Pokémon.*;
import static pokémon.Pokémon.HP_INDEX;

public class Battle implements Comparator<Action>{

    private static Pokémon getFirstHealthyPokémon(ArrayList<Pokémon> party) {
        return getFirstHealthyPokémon(party, null);
    }

    private static Pokémon getFirstHealthyPokémon(ArrayList<Pokémon> party, Pokémon toExclude) {
        for (Pokémon mon : party) {
            if (mon == null) {
                return null;
            }
            if (mon.getStatus() != Status.FAINTED && mon != toExclude) {
                return mon;
            }
        }

        return null;
    }

    private static boolean weatherImmune(Weather weather, Pokémon mon) {
        //The only two kinds of weather that cause damage are sandstorm and hail
        List<Type> immune = (weather == Weather.HAIL) ? IMMUNE_TO_HAIL : IMMUNE_TO_SANDSTORM;
        for (Type type : mon.getType()) {
            if (immune.contains(type)) {
                return true;
            }
        }
        //Pokémon with Sand Veil are immune to sandstorms
        if (weather == Weather.SANDSTORM && mon.getAbility() == Ability.SAND_VEIL) {
            return true;
        }
        return false;
    }

    //Public and static because DamageCalculator also uses it
    public static boolean isPursuit(Action action) {
        if (action instanceof MoveAction) {
            return ((MoveAction) action).getMove() instanceof Pursuit;
        }
        return false;
    }

    public static double getStatMod(int stage) {
        switch (stage) {
            case -6:
                return 2.0 / 8.0;
            case -5:
                return 2.0 / 7.0;
            case -4:
                return 2.0 / 6.0;
            case -3:
                return 2.0 / 5.0;
            case -2:
                return 2.0 / 4.0;
            case -1:
                return 2.0 / 3.0;
            case 0:
                return 1.0;
            case 1:
                return 3.0 / 2.0;
            case 2:
                return 4.0 / 2.0;
            case 3:
                return 5.0 / 2.0;
            case 4:
                return 6.0 / 2.0;
            case 5:
                return 7.0 / 2.0;
            default:
                return 8.0 / 2.0;
        }
    }

    private static double getAccEvasionMod(int stage) {
        switch (stage) {
            case -6:
                return 3.0 / 9.0;
            case -5:
                return 3.0 / 8.0;
            case -4:
                return 3.0 / 7.0;
            case -3:
                return 3.0 / 6.0;
            case -2:
                return 3.0 / 5.0;
            case -1:
                return 3.0 / 4.0;
            case 0:
                return 1.0;
            case 1:
                return 4.0 / 3.0;
            case 2:
                return 5.0 / 3.0;
            case 3:
                return 6.0 / 3.0;
            case 4:
                return 7.0 / 3.0;
            case 5:
                return 8.0 / 3.0;
            default:
                return 9.0 / 3.0;
        }
    }

    public static final int TEN_PERCENT = 10;
    public static final int TWENTY_PERCENT = 20;
    public static final int THIRTY_PERCENT = 30;
    public static final int CHOICE_FIGHT = 0;
    public static final int CHOICE_SWITCH = 1;
    public static final int CHOICE_ITEM = 2;
    public static final int CHOICE_RUN = 3 ;
    private static final int MAX_STAT_MOD = 6;
    private static final int MIN_STAT_MOD = -6;
    public static final int GUARANTEED_ACCURACY = -1;
    public static final int SHAKE_CHECKS = 4;
    public static final int SHAKE_CHECK_MAX_NUM = 65535;

    //Instance variables

    private static final String[] BATTLE_OPTIONS = {"Fight", "Switch", "Item", "Run"};
    private static final int THAW_CHANCE = 21;
    private static final int CONFUSE_INFATUATE_CHANCE = 51;
    private static final int PARALYSIS_CHANCE = 26;
    private static final int ACC_INDEX = 6;
    private static final int EVADE_INDEX = 7;
    private static final int SINGLE_BATTLE_SLOTS = 1;
    private static final int DOUBLE_BATTLE_SLOTS = 2;
    private static final Class<MoveAction> MOVE_CLASS = MoveAction.class;
    private static final Class<SwapAction> SWAP_CLASS = SwapAction.class;
    private static final Class<? extends Action> RUN_CLASS = RunAction.class;
    private static final List<Type> IMMUNE_TO_HAIL = new ArrayList<>(Arrays.asList(Type.ICE));
    private static final List<Type> IMMUNE_TO_SANDSTORM = new ArrayList<>(Arrays.asList(Type.GROUND, Type.ROCK, Type.STEEL));
    private static final List<Class<? extends Move>> BYPASSES_PROTECT = new ArrayList<>(Arrays.asList(PlayNice.class, Feint.class, Whirlwind.class));
    private static final List<Class<? extends Move>> BYPASSES_FLYING = new ArrayList<>(Arrays.asList(Thunder.class, Gust.class, Twister.class, Hurricane.class));
    private static final List<Class<? extends Move>> BYPASSES_DIVING = new ArrayList<>(Arrays.asList());
    private static final List<Class<? extends Move>> BYPASSES_DIGGING = new ArrayList<>(Arrays.asList(Earthquake.class));
    private static final List<Class<? extends Move>> HITS_IN_RAIN = new ArrayList<>(Arrays.asList(Thunder.class, Hurricane.class));
    private static final List<Class<? extends Move>> POWDER_MOVES = new ArrayList<>(Arrays.asList(PoisonPowder.class, SleepPowder.class, StunSpore.class));
    private static final List<Class<? extends Move>> ACTIVATES_PURSUIT = new ArrayList<>(Arrays.asList());
    private static final List<Ability> IMMUTABLE_ABILITIES = new ArrayList<>(Arrays.asList());
    private static final List<Class<? extends Move>> ONE_HIT_KO_MOVES = new ArrayList<>(Arrays.asList(HornDrill.class));



    private boolean wild;
    private boolean battleOver;
    private boolean fleeing;
    private boolean isDoubleBattle;
    //For if the battle is over due to effects like whirlwind, teleport, etc.
    private boolean forcedEnd;

    private int weatherTimer;
    private int runAttempts;
    Side playerSide;
    Side compSide;
    Player player;
    private ArrayList<Pokémon> compParty;
    private Trainer oppTrainer;
    private Weather weather;
    private GameFrame.BattlePrinter battlePrinter;
    private GameFrame.InputHelper inputHelper;


    //Constructors
    //Wild
    public Battle(Player player, Pokémon compParty, Weather weather, GameFrame frame) { this.player = player;
        this.player = player;

        this.compParty = new ArrayList<>();
        this.compParty.add(compParty);
        wild = true;
        this.battlePrinter = frame.getBattlePrinter();
        this.weather = weather;
        weatherTimer = 0;
        battleOver = false;
        this.inputHelper = frame.getInputHelper();
        runAttempts = 0;
        //No wild double battles
        this.isDoubleBattle = false;
        setUpSides();
        this.battlePrinter.printWildBattleStart(playerSide.getSlots().get(0).getPokémon(), compSide.getSlots().get(0).getPokémon());

    }

    //Trainer
    public Battle(Player player, Trainer oppTrainer, Weather weather, GameFrame frame) {
        this.player = player;
        this.compParty = oppTrainer.generateParty(new PokémonFactory(frame.getInputHelper(), frame.getGamePrinter()));
        wild = false;
        this.oppTrainer = oppTrainer;
        this.battlePrinter = frame.getBattlePrinter();
        this.weather = weather;
        weatherTimer = 0;
        battleOver = false;
        this.inputHelper = frame.getInputHelper();
        runAttempts = 0;
        this.isDoubleBattle = oppTrainer.isDoubleBattle();
        setUpSides();
        this.battlePrinter.printTrainerBattleStart(oppTrainer, playerSide, compSide);

    }

    private void setUpSides() {
        int slots = isDoubleBattle ? SINGLE_BATTLE_SLOTS : DOUBLE_BATTLE_SLOTS;
        Pokémon firstPlayerMon = getFirstHealthyPokémon(player.getParty());
        Pokémon firstCompMon = compParty.get(0);
        if (isDoubleBattle) {
            Pokémon secondPlayerMon = getFirstHealthyPokémon(player.getParty(), firstPlayerMon);
            Pokémon secondCompMon = compParty.get(1);
            playerSide = new Side(firstPlayerMon, secondPlayerMon, battlePrinter);
            compSide = new Side(firstCompMon, secondCompMon, battlePrinter);
        } else {
            playerSide = new Side(firstPlayerMon, battlePrinter);
            compSide = new Side(firstCompMon, battlePrinter);
        }
        playerSide.setOpposingSide(compSide);
        compSide.setOpposingSide(playerSide);

    }

    /**
     * Main battle loop
     */
    public void battle() {
        playerSide.addOpponents();
        compSide.addOpponents();
        activateSwitchInAbilities(playerSide);
        activateSwitchInAbilities(compSide);
        while (!battleOver) {
            startTurn();
           for (BattleSlot slot : getAllActiveSlots()) {
               setAction(slot);
           }
            LinkedList<Action> actionQueue = getActionOrder();
            for (Action action : actionQueue) {
                processAction(action);
                if (battleOver) {
                    break;
                }
            }
            endTurn();
            if (battleOver) {
                endBattle();
                break;
            }
        }
    }

    private void setAction(BattleSlot slot) {
        if (slot.getPokémon().getOwner().equals(Owner.PLAYER)) {
            slot.setAction(getPlayerAction(slot));
        } else {
            slot.setAction(getCompAction(slot));
        }
    }

    private double getSpeed(BattleSlot slot) {
        double speed = slot.getPokémon().getStats()[SPD_INDEX]*getStatMod(slot.getStatMod(SPD_INDEX));
        if (slot.getPokémon().getStatus() == Status.PARALYZED) {
            speed *= .25;
        }
        if (slot.getSide().isTailwindActive()) {
            speed *= 2;
        }

        return speed;
    }

    /**
     * Orders Actions based on priority, speed, effect, etc.
     * @return An ordered List of Actions for the turn
     */
    private LinkedList<Action> getActionOrder() {
        LinkedList<Action> queue = pursuitCheck();
        ArrayList<Action> actions = new ArrayList<>();
        for (BattleSlot slot : getAllActiveSlots()) {
            actions.add(slot.getAction());
        }
        for (Action action : queue) {
            actions.remove(action);
        }
        //Sort the queue in case of multiple entries already present -- imagine a double battle, where
        //on each side one 'mon switches out, and the other uses Pursuit.
        queue.sort(this::compare);
        actions.sort(this::compare);
        queue.addAll(actions);
        return queue;
    }

    /**
     * Pursuit has a VERY unique effect that is tricky to account for -- it has a special effect
     * that changes turn order, but it doesn't always activate
     * @return A list of all Actions for the turn that are MoveActions using an activated Pursuit
     */
    private LinkedList<Action> pursuitCheck() {
        LinkedList<Action> queue = new LinkedList<>();
        ArrayList<Action> actions = new ArrayList<>();
        for (BattleSlot slot : getAllActiveSlots()) {
            actions.add(slot.getAction());
        }
        for (Action action : actions) {
            boolean pursuitActivated = false;
            if (isPursuit(action)) {
                MoveAction pursuitAction = (MoveAction) action;
                BattleSlot targetSlot = ((MoveAction) action).getTargetSlot();
                //Pursuit will automatically target the non-targeted opponent if it is switching out
                //instead of it's intended target
                BattleSlot otherOppSlot = targetSlot.getSide().getSisterSlot(targetSlot);
                if (targetSlot.getAction() instanceof SwapAction) {
                    pursuitActivated = true;
                } else if (targetSlot.getAction() instanceof MoveAction &&
                        ACTIVATES_PURSUIT.contains(((MoveAction)targetSlot.getAction()).getMove().getClass())) {
                    if (getSpeed(action.getUserSlot()) < getSpeed(targetSlot)) {
                        pursuitActivated = true;
                    }
                } else if (otherOppSlot != null) {
                    if (otherOppSlot.getAction() instanceof SwapAction) {
                        pursuitActivated = true;
                        pursuitAction.setTargetSlot(otherOppSlot);
                    } else if (otherOppSlot.getAction() instanceof MoveAction &&
                            ACTIVATES_PURSUIT.contains(((MoveAction)otherOppSlot.getAction()).getMove().getClass())){
                        pursuitActivated = true;
                        pursuitAction.setTargetSlot(otherOppSlot);
                    }
                }
                if (pursuitActivated) {
                    ((Pursuit)pursuitAction.getMove()).setSpecialEffectActive(true);
                    queue.add(action);
                }
            }
        }
        return queue;
    }

    private void processAction(Action action) {
        if (action.getUserSlot().isFainted() || battleOver) {
            return;
        }
        if (action.getClass().equals(MOVE_CLASS)) {
            processMoveAction((MoveAction) action);
        } else if (action.getClass().equals(SWAP_CLASS)) {
            processSwapAction((SwapAction) action);
        } else if (action.getClass().equals(RUN_CLASS)) {
            processRunAction(action);
        } else {
            processItemAction(action);
        }
    }

    private void processItemAction(Action action) {
        ItemAction itemAction = (ItemAction) action;
        Item item = itemAction.getItem();
        player.getBag().useItem(item);
        battlePrinter.printItemUse(item);
        if (item instanceof Ball) {
            processCatchAttempt(itemAction);
        } else if (item instanceof HealingItem) {
            processHealingItem(itemAction);
        } else if (item instanceof Berry) {
            processBerryUse(itemAction);
        } else if (item instanceof BattleItem) {
            processBattleItem(itemAction);
        }
    }

    private void processHealingItem(ItemAction itemAction) {
        HealingItem item = (HealingItem) itemAction.getItem();
        BattleSlot userSlot = itemAction.getUserSlot();
        switch (item.getEffect()) {
            case HEAL_TWENTY:
                userSlot.getPokémon().heal(20);
                battlePrinter.printHealItemUsed(userSlot.getPokémon(), 20);
                break;
            case HEAL_FIFTY:
                userSlot.getPokémon().heal(50);
                battlePrinter.printHealItemUsed(userSlot.getPokémon(), 50);
                break;  
            default:
                break; 
        }
    }

    private void processBerryUse(ItemAction itemAction) {
        Berry berry = (Berry) itemAction.getItem();
        switch (berry.getEffect()) {

        }
    }

    private void processBattleItem(ItemAction itemAction) {
        BattleItem item = (BattleItem) itemAction.getItem();
        switch(item.getEffect()) {

        }
    }

    private void processCatchAttempt(ItemAction itemAction) {
        if (!wild) {
            battlePrinter.printUsedBallOnTrainer();
            return;
        }
        double ballModifier;
        switch (((Ball) itemAction.getItem()).getEffect()) {
            case GREAT:
                ballModifier = 1.5;
                break;
            case ULTRA:
                ballModifier = 2;
                break;
            default:
                ballModifier = 1;
                break;
        }
        Pokémon catchTarget = compSide.getSlotOne().getPokémon();
        double statusBonus;
        switch(catchTarget.getStatus()) {
            case PARALYZED:
            case BURNED:
            case POISONED:
            case BADLY_POISONED:
                statusBonus = 1.5;
                break;
            case ASLEEP:
            case FROZEN:
                statusBonus = 2.5;
                break;
            default:
                statusBonus = 1;
                break;
        }
        double a = (3 * catchTarget.getStats()[HP_INDEX] - 2 * catchTarget.getCurrentHP());
        a *= catchTarget.getCatchRate() * ballModifier;
        a /= (3 * catchTarget.getStats()[HP_INDEX]);
        a *= statusBonus;
        double shakeProbability = 1048560 / Math.sqrt(Math.sqrt(16711680/a));
        for (int i = 1; i <= SHAKE_CHECKS; i++) {
            double random = new Random().nextInt(SHAKE_CHECK_MAX_NUM + 1);
            if (random >= shakeProbability) {
                //catch fails
                battlePrinter.printCatchFail(catchTarget, i);
                return;
            } else {
                battlePrinter.printShake();
            }
        }
        //if we get here, the catch must have succeeded
        processCatchSuccess();

    }

    private void processCatchSuccess() {
        battlePrinter.printCatchSuccess(compSide.getSlotOne().getPokémon());
        compSide.getSlotOne().integrate();
        player.setTempCaughtStorage(compSide.getSlotOne().getOriginal());
        battleOver = true;
    }


    private void processRunAction(Action action) {
        if (action.getUserSlot().getPokémon().getAbility() == Ability.RUN_AWAY) {
            battlePrinter.printAbilityActivate(action.getUserSlot().getPokémon());
            fleeing = true;
            battleOver = true;
            return;
        }
        Pokémon userMon = action.getUserSlot().getPokémon();
        Pokémon compMon = compSide.getSlotOne().getPokémon();
        runAttempts++;
        int userSpeed = userMon.getStats()[SPD_INDEX];
        int compSpeed = compMon.getStats()[SPD_INDEX];
        int speedCheck = ((userSpeed*128/compSpeed) + 30*runAttempts) % 256;
        int randomRoll = ThreadLocalRandom.current().nextInt(0, 256);
        if (speedCheck > randomRoll) {
            fleeing = true;
            battleOver = true;
        } else {
            battlePrinter.printFleeFail();
        }


    }

    /**
     * Set up all elements that that need to be in place at the start of a turn, and handle printing
     */
    private void startTurn() {
        playerSide.startTurn();
        compSide.startTurn();
        battlePrinter.printBuffer();
        for (BattleSlot slot : playerSide.getSlotsSafe()) {
            battlePrinter.printTurnStartDescription(slot.getPokémon());
        }
        for (BattleSlot slot : compSide.getSlotsSafe()) {
            battlePrinter.printTurnStartDescription(slot.getPokémon());

        }
    }

    /**
     * Method that selects which actions the computer player takes
     */
    private Action getCompAction(BattleSlot compSlot) {
        //Maybe also switching?
        if (!compSlot.needsMove()) {
//            System.out.println(compSlot.getAction());
//            System.out.println(((MoveAction)compSlot.getAction()).getMove());
            ArrayList<BattleSlot> targetSlots;
            if (((MoveAction)compSlot.getAction()).getMove().getTarget() == Target.RANDOM_ENEMY) {
                targetSlots = new ArrayList<>();
                targetSlots.add(getRandomSlotFromSide(playerSide));
            } else {
                targetSlots = ((MoveAction)compSlot.getAction()).getTargetSlots();
            }
            MoveAction newAction = new MoveAction(compSlot, ((MoveAction)compSlot.getAction()).getMove(),targetSlots);
            return newAction;
        } else if (outOfPP(compSlot)) {
            return new MoveAction(compSlot, new Struggle(), getRandomSlotFromSide(playerSide));
        } else if (compSlot.isTaunted()) {
            if (canMoveWhileTaunted(compSlot)) {
                if (compSlot.isEncored()) {
                    if (compSlot.getLastMoveUsed() != null) {
                        ArrayList<BattleSlot> targetSlots = getMoveTarget(compSlot, compSlot.getLastMoveUsed());
                        return new MoveAction(compSlot, compSlot.getLastMoveUsed(), targetSlots);

                    }
                }
                while(true) {
                    Move compMove = compSlot.getPokémon().getMoveSet().get(ThreadLocalRandom.current().nextInt(0,
                            compSlot.getPokémon().getMoveSet().size()));
                    if (compMove.getCategory() != Category.STATUS && compMove.hasPPLeft()) {
                        ArrayList<BattleSlot> targetSlots = getMoveTarget(compSlot, compMove);
//                        switch (compMove.getTarget()) {
//                            case RANDOM_ENEMY:
//                                targetSlots.add(getRandomSlotFromSide(playerSide));
//                                break;
//                            case SELF:
//                                targetSlots.add(compSlot);
//                                break;
//                            case ALL_ENEMIES:
//                                targetSlots.addAll(playerSide.getSlotsSafe());
//                                break;
//                            case ALL_OTHERS:
//                                targetSlots.addAll(playerSide.getSlotsSafe());
//                                BattleSlot sisterSlot = compSide.getSisterSlot(compSlot);
//                                if (sisterSlot != null) {
//                                    targetSlots.add(sisterSlot);
//                                }
//                                break;
//                            default:
//                                targetSlots.add(getRandomSlotFromSide(playerSide));
//                                break;
//                        }
                        return new MoveAction(compSlot, compMove, targetSlots);
                    }
                }
            } else {
                return new MoveAction(compSlot, new Struggle(), getRandomSlotFromSide(playerSide));
            }
        }
        while(true) {
            Move compMove = compSlot.getPokémon().getMoveSet().get(ThreadLocalRandom.current().nextInt(0,
                    compSlot.getPokémon().getMoveSet().size()));
            if (compMove.hasPPLeft()) {
                ArrayList<BattleSlot> targetSlots = new ArrayList<>();
                switch (compMove.getTarget()) {
                    case RANDOM_ENEMY:
                        targetSlots.add(getRandomSlotFromSide(playerSide));
                        break;
                    case SELF:
                        targetSlots.add(compSlot);
                        break;
                    case ALL_ENEMIES:
                        targetSlots.addAll(playerSide.getSlotsSafe());
                        break;
                    case ALL_OTHERS:
                        targetSlots.addAll(playerSide.getSlotsSafe());
                        BattleSlot sisterSlot = compSide.getSisterSlot(compSlot);
                        if (sisterSlot != null) {
                            targetSlots.add(sisterSlot);
                        }
                        break;
                    default:
                        targetSlots.add(getRandomSlotFromSide(playerSide));
                        break;
                }
                return new MoveAction(compSlot, compMove, targetSlots);
            }
        }
    }

    private BattleSlot getRandomSlotFromSide(Side side) {
        if (side.getSlotsSafe().size() == 2) {
            if (side.getSlotOne().getPokémon() != null && side.getSlotTwo().getPokémon() != null) {
                Random rand = new Random();
                return side.getSlotsSafe().get(rand.nextInt(side.getSlotsSafe().size()));
            } else {
                if (side.getSlotOne().getPokémon() == null) {
                    return side.getSlotTwo();
                } else {
                    return side.getSlotOne();
                }
            }
        } else {
            return (side.getSlotOne().getPokémon() != null) ? side.getSlotOne() : side.getSlotTwo();
        }


    }

    /**
     * Get which actions the user is going to take this turn.
     */
    private Action getPlayerAction(BattleSlot playerSlot) {
        //If in the middle of a multi-turn move, the user has no choice but to keep attacking
        if (!playerSlot.needsAction()) {
            return getPlayerMove(playerSlot);
        }
        while (true) {
            int battleChoice = inputHelper.getBattleChoice(BATTLE_OPTIONS, playerSlot.getPokémon());
            Action action = null;
            switch (battleChoice) {
                case CHOICE_FIGHT:
                    action = getPlayerMove(playerSlot);
                    break;
                case CHOICE_SWITCH:
                    if (playerCanSwitch(playerSlot)) {
                        try {
                            action = getPlayerSwap(playerSlot);
                        } catch (Exception e) {
                            e.printStackTrace();
                            System.err.println("Passed BattleSlot to the wrong Side");
                            System.exit(0);
                        }
                    } else {
                        battlePrinter.printCantSwap();
                    }
                    break;
               case CHOICE_ITEM:
                   action = getPlayerItemAction(playerSlot);
                   break;
                case CHOICE_RUN:
                    if (wild) {
                        action = new RunAction(playerSlot);
                    } else {
                        battlePrinter.printNoRunFromTrainer();
                    }
                    break;
            }
            if (action != null) {
                return action;
            }
        }
    }

    /**
     * Picks an item for the player to use during their turn
     * @return The ItemAction representing the player's choice
     */
    private ItemAction getPlayerItemAction(BattleSlot playerSlot) {
        String itemName = inputHelper.getBattleItem(player.getBag());
        Item toUse = player.getBag().getItemByName(itemName);
        return new ItemAction(playerSlot, toUse);
    }

    /**
     * Test whether the user is allowed to switch
     * @param activeSlot The player's BattleSlot
     * @return true if the user is able to switch and has a Pokémon to switch to, false otherwise
     */
    private boolean playerCanSwitch(BattleSlot activeSlot) {
        if (!activeSlot.getBindingEffects().isEmpty()) {
            //Binding effects stop you from switching
            return false;
        }
        for (Pokémon mon : player.getParty()) {
            if (playerSide.contains(mon)) {
                continue;
            } else if (mon.getStatus() != Status.FAINTED) {
                return true;
            }
        }
        return false;
    }
    /**
     * Take the move the user wants to use and turn it into a MoveAction
     * @param playerSlot The BattleSlot of the Pokémon going to move
     * @return the MoveAction representing the player's choice
     */
    private MoveAction getPlayerMove(BattleSlot playerSlot) {
        //If the user is in the middle of a multi-turn move, then they need to use the same move; they don't have a choice.
        if (!playerSlot.needsMove()) {
            //This should only happen if the user is using the same move as before (multi-turn moves, encore, etc.)
            //Therefore, there should be a MoveAction
            ArrayList<BattleSlot> targetSlots;
            if (((MoveAction)playerSlot.getAction()).getMove().getTarget() == Target.RANDOM_ENEMY) {
                targetSlots = new ArrayList<>();
                targetSlots.add(getRandomSlotFromSide(compSide));
            } else {
                targetSlots = ((MoveAction)playerSlot.getAction()).getTargetSlots();
            }
            return new MoveAction(playerSlot, ((MoveAction)playerSlot.getAction()).getMove(), targetSlots);
        } if (playerSlot.isTaunted()) {
            if (!canMoveWhileTaunted(playerSlot)) {
                return new MoveAction(playerSlot, new Struggle(), getRandomSlotFromSide(compSide));
            }
        }
        if (playerSlot.isEncored()) {
            if (playerSlot.getLastMoveUsed() != null) {
                return new MoveAction(playerSlot, playerSlot.getLastMoveUsed(), getMoveTarget(playerSlot, playerSlot.getLastMoveUsed()));
            }
        }
        if (outOfPP(playerSlot)) {
            return new MoveAction(playerSlot, new Struggle(), getRandomSlotFromSide(compSide));
        }
        while (true) {
            int selection = inputHelper.getMoveChoice(playerSlot.getPokémon().getMoveSet(), playerSlot.getPokémon());
            Move playerMove = playerSlot.getPokémon().getMoveSet().get(selection);
            if (playerSlot.isTaunted() && playerMove.getCategory() == Category.STATUS) {
                battlePrinter.printTauntBlockedMove(playerSlot, playerMove);
                continue;
            }
            if (playerMove.hasPPLeft()) {
                ArrayList<BattleSlot> targets = getMoveTarget(playerSlot, playerMove);
                return new MoveAction(playerSlot, playerMove, targets);
            } else {
                battlePrinter.printNoPP();
            }

        }

    }

    /**
     * Taunt stops Pokémon from using status moves. We need to determine whether a Taunted mon, who normally could use any attack
     * has any move it can use now
     * @param slot
     * @return
     */
    private boolean canMoveWhileTaunted(BattleSlot slot) {
        if (slot.isEncored() && slot.getLastMoveUsed() != null) {
            if (slot.getLastMoveUsed().getCategory() == Category.STATUS) {
                return false;
            }
        }
        boolean canMove = false;
        for (Move move : slot.getPokémon().getMoveSet()) {
            if (move.hasPPLeft() && move.getCategory() != Category.STATUS) {
                canMove = true;
            }
        }
        return canMove;
    }

    /**
     * Figure out which BattleSlot will be the target of a MoveAction. Double battles require the player to
     * select a target
     * @param slot The the BattleSlot of the Pokémon that's using the move
     * @param move The move to be used
     * @return The List of BattleSlots that will be the target of the move
     */
    private ArrayList<BattleSlot> getMoveTarget(BattleSlot slot, Move move) {
        Side userSide = slot.getSide();
        Side oppSide = slot.getSide().getOpposingSide();
        ArrayList<BattleSlot> toReturn = new ArrayList<>();
        switch (move.getTarget()) {
            case SELF:
                toReturn.add(slot);
                break;
            case ALL_OTHERS:
                toReturn.addAll(oppSide.getSlotsSafe());
                BattleSlot sisterSlot = userSide.getSisterSlot(slot);
                if (sisterSlot != null) {
                    toReturn.add(sisterSlot);
                }
                break;
            case ALL_ENEMIES:
                toReturn.addAll(oppSide.getSlotsSafe());
                break;
            case ALL:
                toReturn.addAll(oppSide.getSlotsSafe());
                toReturn.addAll(userSide.getSlotsSafe());
                break;
            case RANDOM_ENEMY:
                toReturn.add(getRandomSlotFromSide(oppSide));
                break;
            default:
                if (isDoubleBattle) {
                    if (slot.getPokémon().getOwner() == Owner.PLAYER) {
                        toReturn.add(inputHelper.selectTarget(playerSide, compSide, slot, move));
                    } else {
                        //Only way to get here means a computer picked a single-target move in a double battle
                        toReturn.add(getRandomSlotFromSide(playerSide));
                    }
                } else {
                    //Don't need to ask for a target in a single battle
                    toReturn.add(oppSide.getSlotOne());
                }

        }
        return toReturn;
    }

    /**
     * Method to check if a Pokémon is completely out of PP
     * @param slot The BattleSlot with the Pokémon to check
     * @return true if the Pokémon has no usable moves, false otherwise
     */
    private boolean outOfPP(BattleSlot slot) {
        List<Move> moveSet = slot.getPokémon().getMoveSet();
        for (Move move : moveSet) {
            if (move.hasPPLeft()) {
                return false;
            }
        }
        return true;

    }

    /**
     * Take the Pokémon the user wants to switch to and make a SwapAction
     * @param playerSlot The BattleSlot to be swapped out
     * @return the SwapAction representing the user's choice
     */
    private SwapAction getPlayerSwap(BattleSlot playerSlot) {
        Pokémon chosen = getReplacementMon(playerSlot.getPokémon(), getReserveMons(player.getParty()));
        return new SwapAction(playerSlot, chosen);
    }

    private Pokémon getReplacementMon(Pokémon currentMon, ArrayList<Pokémon> reserves) {
        Pokémon newMon = inputHelper.getPokémonToSwitchIn(currentMon, reserves);
        return newMon;
    }

    /**
     * Some moves force a random switch (by either side). This gets the pokémon that will switch in
     * @param current The curent active Pokémon
     * @param party The party of Pokémon to choose from
     * @return The Pokémon that will switch in, or none if there is no such Pokémon that can
     */
    private Pokémon getForcedSwitch(BattleSlot current, ArrayList<Pokémon> party) {
        List<Pokémon> candidates = new ArrayList<>();
        for (Pokémon mon : party) {
            if (current.getOriginal().equals(mon)) {
                continue;
            } else if (mon.getStatus() == Status.FAINTED) {
                continue;
            } else if (current.getSide().getSisterSlot(current).getOriginal().equals(mon)) {
                continue;
            } else {
                candidates.add(mon);
            }
        }
        if (candidates.isEmpty()) {
            return null;
        } else {
            return candidates.get(new Random().nextInt(candidates.size()));
        }

    }

    /**
     * Process a move actions -- determine if the move will occur, if it will hit, and if so, how much damage it will do
     * @param action
     */
    private void processMoveAction(MoveAction action) {
        if (canMove(action)) {
            action.getUserSlot().setLastMoveUsed(action.getMove());
            battlePrinter.printMoveAction(action);
            action.getMove().decreasePP();
            if (!action.getMove().hasPPLeft()) {
                action.getUserSlot().setForceEndEncore(true);
            }
            if (action.getMove().getClass().equals(Rage.class)) {
                action.getUserSlot().setRaging(true);
            } else {
                action.getUserSlot().setRaging(false);
            }
            if (doesHit(action)) {
                if (action.getMove().getCategory() == Category.STATUS) {
                    processStatusMove(action);
                } else {
                    processAttackMove(action);
                }
            } else {
                //If a multi-turn or charge move failed, we need to end their effects
                if (action.getMove() instanceof MultiTurnMove) {
                    endMultiTurnMove(action);
                } else if (action.getMove() instanceof ChargeMove) {
                    if (((ChargeMove) action.getMove()).getPartOneDone()) {
                     endChargeMove(action);
                    }
                }
            }
        } else {
            if (action.getMove() instanceof MultiTurnMove) {
                endMultiTurnMove(action);
            } else if (action.getMove() instanceof ChargeMove) {
                if (((ChargeMove) action.getMove()).getPartOneDone()) {
                    endChargeMove(action);
                }
            }
        }
        if (isPursuit(action)) {
            ((Pursuit) action.getMove()).setSpecialEffectActive(false);
        }
    }

    /**
     * Determine if the current move can be performed by the Pokémon
     * given their  current status
     * @param action The MoveAction that is attempted to be used
     * @return true if the Pokémon in the Action can use the move, false otherwise
     */
    private boolean canMove(MoveAction action) {
        BattleSlot slot = action.getUserSlot();
        Pokémon mon = slot.getPokémon();
        if (mon.getStatus() == Status.ASLEEP) {
            if (mon.deceaseSlpTimer()) {
                battlePrinter.printWakeMessage(mon);
                mon.setStatus(Status.NONE);
            } else {
                battlePrinter.printIsAsleepMessage(mon);
                return false;
            }
        } else if (mon.getStatus() == Status.FROZEN) {
            if (ThreadLocalRandom.current().nextInt(1, 101) < THAW_CHANCE) {
                battlePrinter.printThawMessage(mon);
                mon.setStatus(Status.NONE);
            } else {
                battlePrinter.printIsFrozenMessage(mon);
                return false;
            }
        }
        if (slot.isFlinching()) {
            battlePrinter.printFlinch(slot.getPokémon());

            return false;
        }
        if (slot.isConfused()) {
            if (slot.decreaseCfnTimer()) {
                battlePrinter.printSnapOutMessage(mon);
            } else {
                battlePrinter.printIsConfusedMessage(mon);
                if (ThreadLocalRandom.current().nextInt(1, 101) > CONFUSE_INFATUATE_CHANCE) {
                    battlePrinter.printHurtSelfMessage(mon);
                    confusionHit(slot);
                    return false;
                }
            }
        }
        if (mon.getStatus() == Status.PARALYZED) {
            if (ThreadLocalRandom.current().nextInt(1, 101) < PARALYSIS_CHANCE) {
                battlePrinter.printParalyzedMessage(mon);
                if (action.getMove() instanceof MultiTurnMove) {
                    endMultiTurnMove(action);
                }
                return false;
            }
        }
        if (slot.isInfatuated()) {
            battlePrinter.printIsInfatuatedMessage(mon);
            if (ThreadLocalRandom.current().nextInt(1, 101) > CONFUSE_INFATUATE_CHANCE) {
                battlePrinter.printImmobilizedByLoveMessage(mon);
                return false;
            }
        }
        return true;
    }

    /**
     * Calculates and inflicts the damage from a confused Pokémon hurting themselves in confusion
     * @param slot The BattleSlot containing the confused 'mon
     */
    private void confusionHit(BattleSlot slot) {
        int dmg = DamageCalculator.confusionHit(slot);
        inflictDamage(slot, dmg);
    }

    /**
     * Calculates whether the Pokémon's move will actually hit (as opposed to missing, for various reasons)
     * @param action The MoveAction to be checked
     * @return true if the move will connect, false otherwise
     */
    private boolean doesHit(MoveAction action) {
        //The charge turn of a ChargeMove always goes ahead
        if (action.getMove() instanceof ChargeMove) {
            if (!((ChargeMove) action.getMove()).getPartOneDone()) {
                return true;
            }
        }
        //These two checks only matter if a target of the move is another Pokémon
        if (!action.getUserSlot().equals(action.getTargetSlot())) {
            int faintedCount = 0;
            int invulnerableCount = 0;
            for (BattleSlot slot : action.getTargetSlots()) {
                if (slot.isFainted()) {
                    faintedCount++;
                } else if (opponentIsInvulnerable(slot, action)) {
                    invulnerableCount++;
                }
            }
            if (faintedCount == action.getTargetSlots().size()) {
                if (canSwitchTarget(action)) {
                    return true;
                } else {
                    battlePrinter.printNoTarget();
                    return false;
                }
            } else if (action.getTargetSlots().size() - faintedCount == invulnerableCount) {
                //This is equivalent to
                // invulnerableCount == action.getTargetSlots().size() || invulnerableCount + faintedCount == action.getTargetSlots().size()
                return false;
            }
        }

        //Sucker Punch can only hit if it's going first, and if the other Pokémon's move is an attack move
        if (action.getMove().getClass() == SuckerPunch.class) {
            if (action.getTargetSlot().getAction() instanceof MoveAction) {
                //TODO: replace true condition with a way to devise if the user is moving before the target
                if (true) {
                    return true;
                } else {
                    return false;
                }
            } else {
                battlePrinter.printFailure();
                return false;
            }
        }
        //Accuracy checks always need to occur
        ArrayList<BattleSlot> targetsToHit = new ArrayList<>();
        ArrayList<BattleSlot> missedTargets = new ArrayList<>();
        for (BattleSlot slot : action.getTargetSlots()) {
            if (accuracyCheck(action, slot)) {
                if (elementalImmunityAbilityCheck(action, slot)) {
                    targetsToHit.add(slot);
                }

            } else {
                missedTargets.add(slot);
            }
        }
        if (targetsToHit.isEmpty()) {
            battlePrinter.printMoveMissed(action.getUserSlot().getPokémon());
            return false;
        } else {
            action.setTargetSlots(targetsToHit);
            for (BattleSlot slot : missedTargets) {
                battlePrinter.printEvade(slot.getPokémon());
            }
        }

        return true;
    }


    /**
     * Many types have an ability that nullifies all move of that type, usually with some additional effect
     *
     * @param action -- The MoveAction being used
     * @param slot -- The BattleSlot of the defending Mon
     * @return true if the attack connects (i.e. ability does NOT activate), false otherwise
     */
    private boolean elementalImmunityAbilityCheck(MoveAction action, BattleSlot slot) {
        boolean active = false;
        if (slot.getPokémon().getAbility() == Ability.FLASH_FIRE && action.getMove().getType() == Type.FIRE) {
            active = true;
            slot.activateFlashFire();
        }
        if (active) {
           battlePrinter.printAbilityActivate(slot.getPokémon());
           return false;
        }

        return true;
    }

    /**
     * In a double battle, if the target of a single-target move has fainted, but the target's sister slot is available to attack,
     * the the sister slot automatically becomes the new target
     * @param action The action to check if the target can be reassigned
     * @return true if the target was able to be switched, false if not
     */
    private boolean canSwitchTarget(MoveAction action) {
        Target target = action.getMove().getTarget();
        if (target == Target.SINGLE_TARGET || target == Target.RANDOM_ENEMY) {
            BattleSlot sisterSlot = action.getUserSlot().getSide().getSisterSlot(action.getUserSlot());
            if (sisterSlot != null && sisterSlot.getPokémon() != null && !sisterSlot.isFainted()) {
                action.setTargetSlot(sisterSlot);
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * Calculates whether an attempted move would hit a certain slot based solely on the attacker's
     * accuracy and the target's evasion
     * @param action The MoveAction representing the attempted move
     * @param targetSlot The slot to check for a hit
     * @return true if the move will hit, false otherwise
     */
    private boolean accuracyCheck(MoveAction action, BattleSlot targetSlot) {
        if (action.getMove().getAccuracy() == GUARANTEED_ACCURACY) {
            return true;
        } else if (weather == Weather.RAIN && HITS_IN_RAIN.contains(action.getMove().getClass())) {
            return true;
        } else if (action.getMove() instanceof ChargeMove) {
            if (!((ChargeMove) action.getMove()).getPartOneDone()) { // Charging turn of ChargeMoves doesn't need accuracy
                if (!skipCharging(action)) {
                    return true;
                }
            }
        } else if (action.getMove().getClass() == Toxic.class && action.getUserSlot().getPokémon().getType().contains(Type.POISON)) {
            //Toxic never misses when used by a Poison-type
            return true;
        }
        //Pursuit always hits if it is "pursuing" a target
        if (isPursuit(action)) {
            if(((Pursuit)action.getMove()).isSpecialEffectActive()) {
                return true;
            }
        }

        int accuracyRoll = ThreadLocalRandom.current().nextInt(1, 101);
        double moveAccuracy = action.getMove().getAccuracy();
        if (ONE_HIT_KO_MOVES.contains(action.getMove().getClass())) {
            moveAccuracy = oneHitKOAccCheck(action, targetSlot);
        }
        if (action.getUserSlot().getPokémon().getAbility().equals(Ability.COMPOUND_EYES)) {
            moveAccuracy *= 1.3;
        }
        //Hurricane and Thunder have an accuracy of 50% in harsh sunlight
        if (weather == Weather.SUN && HITS_IN_RAIN.contains(action.getMove().getClass())) {
            moveAccuracy = 50.0;
        }
        //Tangled Feet halves accuracy of opposing moves if you're confused
        if (targetSlot.getPokémon().getAbility() == Ability.TANGLED_FEET &&
                action.getTargetSlot().isConfused()) {
            moveAccuracy /= 2.0;
        }
        //Sand Veil lowers the accuracy of opposing moves while a sandstorm is active
        if (weather == Weather.SANDSTORM && targetSlot.getPokémon().getAbility() == Ability.SAND_VEIL) {
            moveAccuracy *= 0.8;
        }

        int userAccStage = action.getUserSlot().getStatMod(ACC_INDEX);
        int targetEvasionStage = targetSlot.getStatMod(EVADE_INDEX);
        double userAccMod = getAccEvasionMod(userAccStage);
        double targetEvasionMod = getAccEvasionMod(targetEvasionStage);
        double percentage = moveAccuracy * (userAccMod/targetEvasionMod);
        return percentage >= accuracyRoll;
    }

    /**
     * Special calcuation for One-Hit KO moves
     * @param action
     * @param targetSlot
     * @return
     */
    private double oneHitKOAccCheck(MoveAction action, BattleSlot targetSlot) {
        //Acc = ( ( user_level - target_level ) + 30 ) * 100%
        int userLevel = action.getUserSlot().getPokémon().getLevel();
        int targetLevel = targetSlot.getPokémon().getLevel();
        return ((userLevel - targetLevel) + 30);
    }


    /**
     * Calculates if a move's target has some kind of invulnerability
     * And whether the user is going to bypass that invulnerability
     * @param action The actions representing the attempted move
     * @return True if the opponent has some kind of invulnerability that holds,
     * false otherwise
     */
    private boolean opponentIsInvulnerable(BattleSlot target, MoveAction action) {
        switch (target.getInvulnerabilty()) {
            case PROTECTED:
                if (BYPASSES_PROTECT.contains(action.getMove().getClass())) {
                    return false;
                } else {
                    battlePrinter.printProtectionActive(target.getPokémon());
                    return true;
                }
            case FLYING:
                if (BYPASSES_FLYING.contains(action.getMove().getClass())) {
                    return false;
                } else {
                    battlePrinter.printMoveMissed(action.getUserSlot().getPokémon());
                    return true;
                }
            case DIVING:
                if (BYPASSES_DIVING.contains(action.getMove().getClass())) {
                    return false;
                } else {
                    battlePrinter.printMoveMissed(action.getUserSlot().getPokémon());
                    return true;
                }
            case DIGGING:
                if (BYPASSES_DIGGING.contains(action.getMove().getClass())) {
                    return false;
                } else {
                    battlePrinter.printMoveMissed(action.getUserSlot().getPokémon());
                    return true;
                }
                default:
                    return false;

        }
    }

    /**
     * A method to handle all status moves, split by whether they target the self or an opponent
     * @param action The MoveAction representing the move used
     */
    private void processStatusMove(MoveAction action) {
        if (action.getUserSlot().isTaunted()) {
            battlePrinter.printTauntBlockedMove(action.getUserSlot(), action.getMove());
            return;
        }
        //Grass-types and Pokémon with the Overcoat ability are immune to Powder moves
        if (POWDER_MOVES.contains(action.getMove().getClass()) &&
                (action.getTargetSlot().getPokémon().getType().contains(Type.GRASS) ||
                        action.getTargetSlot().getPokémon().getAbility() == Ability.OVERCOAT))  {
            if (action.getTargetSlot().getPokémon().getAbility() == Ability.OVERCOAT) {
                battlePrinter.printAbilityActivate(action.getTargetSlot().getPokémon());
            }
            battlePrinter.printFailure();
            return;
        } else if (Type.calculateTypeMod(action.getMove().getType(), action.getTargetSlot().getPokémon().getType()) == 0) {
            //Status move has no effect due to typing, e.g. Thunder Wave vs. Diglett
            //But, Glare hits Ghosts
            if (action.getMove().getClass() != Glare.class) {
                battlePrinter.printFailure();
                return;
            }

        }
        for (BattleSlot target : action.getTargetSlots()) {
            switch (action.getMove().getEffect()) {
                case TARGET_DEF_DOWN:
                    attemptToChangeStat(target, DEF_INDEX, -1, true);
                    break;
                case TARGET_ATK_DOWN:
                    attemptToChangeStat(target, ATK_INDEX, -1, true);
                    break;
                case TARGET_ATK_DOWN_SHARP:
                    attemptToChangeStat(target, ATK_INDEX, -2, true);
                case PARALYZE:
                    if (attemptToGiveStatus(target, Status.PARALYZED, true)) {
                        battlePrinter.printParalysisGranted(target.getPokémon());
                    }
                    break;
                case TARGET_ACC_DOWN:
                    if (action.getTargetSlot().getPokémon().getAbility() == Ability.KEEN_EYE) {
                        battlePrinter.printAbilityActivate(target.getPokémon());
                        battlePrinter.printAccCantDecrease(target.getPokémon());
                        break;
                    }
                    attemptToChangeStat(target, ACC_INDEX, -1, true);
                    break;
                case TARGET_SPD_DOWN_SHARP:
                    attemptToChangeStat(target, SPD_INDEX, -2, true);
                    break;
                case POISON:
                    if(attemptToGiveStatus(target, Status.POISONED, true)) {
                        battlePrinter.printPoisonGranted(target.getPokémon());
                    }
                    break;
                case SLEEP:
                    if (attemptToGiveStatus(target, Status.ASLEEP, true)) {
                        battlePrinter.printSleepGranted(target.getPokémon());
                    }
                    break;
                case TARGET_EVADE_DOWN_SHARP:
                    attemptToChangeStat(target, EVADE_INDEX, -2, true);
                    break;
                case LEECH_SEED:
                    if (target.getPokémon().getType().contains(Type.GRASS)) {
                        battlePrinter.printFailure();
                    } else if (target.isLeechSeeded()) {
                        battlePrinter.printFailure();
                    } else {
                        target.setLeechSeeded(action.getUserSlot());
                        battlePrinter.printSeeded(target.getPokémon());
                    }
                    break;
                case WORRY_SEED:
                    if (IMMUTABLE_ABILITIES.contains(target.getPokémon().getAbility())) {
                        battlePrinter.printFailure();
                    } else {
                        target.getPokémon().setAbility(Ability.INSOMNIA);
                        battlePrinter.printWorrySeed(target);
                    }
                    break;
                case FORCE_SWITCH:
                    //In a wild battle, these moves just end the battle
                    if (wild) {
                        battleOver = true;
                        forcedEnd = true;
                    } else {
                        Pokémon current = target.getPokémon();
                        ArrayList<Pokémon> party = (current.getOwner() == Owner.PLAYER) ? player.getParty() : compParty;
                        Pokémon newMon = getForcedSwitch(target, party);
                        if (newMon == null) {
                            battlePrinter.printFailure();
                        } else {
                            switchActiveMon(target, newMon);
                        }
                    }
                    break;
                case MIRROR_MOVE:
                    Move move = target.getLastMoveUsed();
                    if (move == null) {
                        battlePrinter.printFailure();
                    } else {
                        try {
                            Move newMove = move.getClass().newInstance();
                            battlePrinter.printMirrorMove(newMove);
                            MoveAction newMoveAction = new MoveAction(action.getUserSlot(), newMove, target);
                            processMoveAction(newMoveAction);
                        } catch (InstantiationException | IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case TARGET_SPD_DOWN:
                    attemptToChangeStat(target, SPD_INDEX, 1, true);
                    break;
                case CONFUSE:
                    if (action.getTargetSlot().confuse(false)) {
                        battlePrinter.printConfusionGranted(target.getPokémon());
                    } else {
                        battlePrinter.printFailure();
                    }
                    break;
                case CAPTIVATE:
                    if (oppositeGenders(action.getUserSlot().getPokémon(), target.getPokémon())) {
                        if (target.getPokémon().getAbility().equals(Ability.OBLIVIOUS)) {
                            battlePrinter.printAbilityActivate(target.getPokémon());
                            battlePrinter.printFailure();
                        } else {
                            attemptToChangeStat(target, ATK_INDEX, 2, true);
                        }
                    } else {
                        battlePrinter.printFailure();
                    }
                    break;
                case TOXIC_SPIKES:
                    if (target.getSide().addToxicSpikes()) {
                        battlePrinter.printToxicSpikes(action.getTargetSlot().getPokémon().getOwner());
                    } else {
                        battlePrinter.printFailure();
                    }
                    break;
                case SELF_EVADE_UP:
                    attemptToChangeStat(action.getUserSlot(), EVADE_INDEX, 1, true);
                    break;
                case SELF_SPEED_UP_SHARP:
                    attemptToChangeStat(action.getUserSlot(), SPD_INDEX, 2, true);
                    break;
                case SELF_DEF_UP:
                    attemptToChangeStat(action.getUserSlot(), DEF_INDEX, 1, true);
                    break;
                case PROTECT:
                    action.getUserSlot().setInvulnerabilty(Invulnerabilty.PROTECTED);
                    break;
                case SELF_DEF_UP_SHARP:
                    attemptToChangeStat(action.getUserSlot(), DEF_INDEX, 2, true);
                    break;
                case RAIN:
                    weather = Weather.RAIN;
                    weatherTimer = 5;
                    battlePrinter.printWeatherStart(weather);
                    break;
                case GROWTH:
                    int growthAmt = (weather == Weather.SUN) ? 2: 1;
                    attemptToChangeStat(action.getUserSlot(), ATK_INDEX, growthAmt, true);
                    attemptToChangeStat(action.getUserSlot(), SPATK_INDEX, growthAmt, true);
                    break;
                case RESTORE_SUN:
                    int maxHP = action.getUserSlot().getPokémon().getStats()[HP_INDEX];
                    double percentage = (weather == Weather.NONE) ? .5 : (weather == Weather.SUN) ? .666 : .25;
                    int toRestore = (int)(Math.floor((double) maxHP*percentage));
                    battlePrinter.printRestoreHP(action.getUserSlot().getPokémon(), toRestore);
                    action.getUserSlot().getPokémon().heal(toRestore);
                    break;
                case LIGHT_SCREEN:
                    action.getUserSlot().getSide().activateLightScreen(5);
                    battlePrinter.printLightScreenActivate(action.getUserSlot().getPokémon());
                    break;
                case REFLECT:
                    action.getUserSlot().getSide().activateReflect(5);
                    battlePrinter.printReflectActivate(action.getUserSlot().getPokémon());
                    break;
                case FOCUS_ENERGY:
                    if (action.getUserSlot().usedFocusEnergy()) {
                        battlePrinter.printFailure();
                    } else {
                        action.getUserSlot().getPokémon().addCritStage(2);
                        action.getUserSlot().setUsedFocusEnergy(true);
                        battlePrinter.printFocusEnergy(action.getUserSlot());

                    }
                    break;
                case TAILWIND:
                    action.getUserSlot().getSide().activateTailwind();
                    battlePrinter.printTailwindActivate(action.getUserSlot());
                    break;
                case RESTORE:
                    int restoreAmt = action.getUserSlot().getPokémon().getStats()[HP_INDEX]/2;
                    battlePrinter.printRestoreHP(action.getUserSlot().getPokémon(), restoreAmt);
                    action.getUserSlot().getPokémon().heal(restoreAmt);
                    break;
                case SELF_ATK_UP_SHARP:
                    attemptToChangeStat(action.getUserSlot(), ATK_INDEX, 2, true);
                    break;
                case SAFEGUARD:
                    action.getTargetSlot().getSide().activateSafeguard();
                    battlePrinter.printSafeguardActivate(action.getTargetSlot().getPokémon());
                    break;
                case QUIVER_DANCE:
                    attemptToChangeStat(action.getUserSlot(), SPATK_INDEX, 1, true);
                    attemptToChangeStat(action.getUserSlot(), SPDEF_INDEX, 1, true);
                    attemptToChangeStat(action.getUserSlot(), SPD_INDEX, 1, true);
                    break;
                case TARGET_DEF_DOWN_SHARP:
                    attemptToChangeStat(target, DEF_INDEX, 2, true);
                    break;
                case TAUNT:
                    target.setTaunt();
                    battlePrinter.printTauntActivate(target);
                    break;
                case ENCORE:
                    if (target.getLastMoveUsed() == null) {
                        battlePrinter.printFailure();
                    } else {
                        target.setEncored();
                        battlePrinter.printEncoreStart(target);
                    }
                    break;
                case HAZE:
                    for (BattleSlot slot : getAllActiveSlots()) {
                        for (int i = 0; i < 6; i++) {
                            slot.setStatMod(i, 0);
                        }
                    }
                    battlePrinter.printHazeEffect();
                    break;
                case TOXIC:
                    attemptToGiveStatus(target, Status.BADLY_POISONED, true);
                    break;
                case BURN:
                    attemptToGiveStatus(target, Status.BURNED, true);
                    break;
                case SELF_SPATK_UP_SHARP:
                    attemptToChangeStat(target, SPATK_INDEX, 2, true);
                    break;
                default:
                    break;
            }
        }
    }

    private boolean oppositeGenders(Pokémon mon1, Pokémon mon2) {
        Gender mon1Gender = mon1.getGender();
        Gender mon2Gender = mon2.getGender();
        if (mon1Gender == Gender.MALE) {
            return mon2Gender == Gender.FEMALE;
        } else if (mon1Gender == Gender.FEMALE) {
            return mon2Gender == Gender.MALE;
        } else {
            return false;
        }
    }

    /**
     * Try to give a Pokémon a status condition -- it can fail because the Pokémon's type doesn't allow for the given
     * status, or because it already has one
     * @param slot The BattleSlot with the Pokémon to receive the status condition
     * @param status The status condition to be conferred
     * @param showFail true if a failure should be reported (if it's the main/only effect of the move), false otherwise
     *                 (if it's a side effect)
     * @return true if a status was given, false otherwise
     */
    private boolean attemptToGiveStatus(BattleSlot slot, Status status, boolean showFail) {
        Pokémon mon = slot.getPokémon();
        if (mon.getStatus().equals(Status.FAINTED)) {
            return false;
        }
        //Electric types can't be paralyzed
        if (status == Status.PARALYZED && slot.getPokémon().getType().contains(Type.ELECTRIC)) {
            if (showFail) {
                battlePrinter.printFailure();
            }
        } else if (status == Status.PARALYZED && slot.getPokémon().getAbility() == Ability.LIMBER) {
            if (showFail) {
                battlePrinter.printAbilityActivate(slot.getPokémon());
                battlePrinter.printFailure();
            }
        } else if (status == Status.BURNED && slot.getPokémon().getType().contains(Type.FIRE)) {
            if (showFail) {
                //Fire types can't be burned
                battlePrinter.printFailure();
            }
        } else if ((status == Status.POISONED || status == Status.BADLY_POISONED) &&
                (slot.getPokémon().getType().contains(Type.POISON) || slot.getPokémon().getType().contains(Type.STEEL))) {
            //Poison and Steel types can't be poisoned
            if (showFail) {
                battlePrinter.printFailure();
            }
        } else if (status == Status.ASLEEP && (slot.getPokémon().getAbility() == Ability.INSOMNIA ||
                slot.getPokémon().getAbility() == Ability.VITAL_SPIRIT)) {
            if (showFail) {
                battlePrinter.printAbilityActivate(slot.getPokémon());
                battlePrinter.printFailure();
            }
            return false;
        } else if (slot.getSide().isSafeguardUp()) {
            battlePrinter.printFailure();
        } else if (mon.getStatus() == Status.NONE) { //Statuses cannot stack
            mon.setStatus(status);
            return true;
        } else if (showFail) {
            battlePrinter.printFailure();
        }
        return false;
    }

    /**
     * Try to raise or lower a Pokémon's stats -- it may fail if the change would raise a stat above the maximum
     * or lower it below the minimum
     * @param slot The BattleSlot containing the Pokémon to get a stat change
     * @param statIndex the index of the stat to be changed
     * @param amt How much the stat will change
     * @param showFail true if a message should display on failure to raise a stat (if it's the main/only effect of the
     *                  move), false otherwise (if it's a side effect, generally)
     */
    private void attemptToChangeStat(BattleSlot slot, int statIndex, int amt, boolean showFail) {
        if (slot.getPokémon().getStatus().equals(Status.FAINTED)) {
            return;
        }
        int statMod = slot.getStatMod(statIndex);
        int diff = 0;
        boolean sharp;
        if (amt > 0) {
            if (statMod == MAX_STAT_MOD && showFail) {
                battlePrinter.printCantRaiseStat(slot.getPokémon(), statIndex);
            } else {
                diff = MAX_STAT_MOD - statMod;
                statMod += amt;
                if (statMod > MAX_STAT_MOD) {
                    statMod = MAX_STAT_MOD;
                }
                slot.setStatMod(statIndex, statMod);
                sharp = diff > 1 && amt > 1;
                battlePrinter.printStatRaise(slot.getPokémon(), statIndex, sharp);
            }
        } else {
            if (statMod == MIN_STAT_MOD && amt < 0 && showFail) {
                battlePrinter.printCantLowerStat(slot.getPokémon(), statIndex);
            } else {
                diff = MIN_STAT_MOD - statMod;
                statMod += amt;
                if (statMod < MIN_STAT_MOD) {
                    statMod = MIN_STAT_MOD;
                }
                slot.setStatMod(statIndex, statMod);
                sharp = diff > 1 && amt < -1;
                battlePrinter.printStatFall(slot.getPokémon(), statIndex, sharp);
            }
        }
    }

    /**
     * Go through what happens when an attack is made
     * @param action The MoveAction representing the attack
     */
    private void processAttackMove(MoveAction action) {
        if (action.getMove() instanceof ChargeMove) {
            processChargeMove(action);
        } else if (action.getMove() instanceof MultiTurnMove) {
            processMultiTurnMove(action);
        } else if (action.getMove() instanceof MultiStrikeMove) {
            processMultiStrikeMove(action);
        } else {
            for (BattleSlot target : action.getTargetSlots()) {
                int dmg;
                if (ONE_HIT_KO_MOVES.contains(action.getMove().getClass())) {
                    battlePrinter.printOneHitKO();
                    dmg = target.getPokémon().getCurrentHP();
                } else {
                    dmg = new DamageCalculator(weather, battlePrinter).damageCalc(action, target);
                }
                if (dmg == DamageCalculator.DAMAGE_FAILURE) {
                    battlePrinter.printFailure();
                } else {
                    target.setMoveHitByThisTurn(action);
                    target.setDamageTakenThisTurn(dmg);
                    inflictDamage(target, dmg);
                    processExtraEffect(action, target, dmg);
                    processAbilityAfterHit(action, target);
                }
            }
        }
    }

    /**
     * Special method to handle moves with a charging turn (e.g. Fly)
     * @param action The MoveAction representing the move to be used
     */
    private void processChargeMove(MoveAction action) {
        ChargeMove move = (ChargeMove) action.getMove();
        //Conduct the charging turn if needed
        if (!move.getPartOneDone()) {
            if (!skipCharging(action)) {
                //PP is only deducted from a charge move on the attacking turn
                action.getMove().restorePP(1);
                battlePrinter.printChargeEffect(action);
                processChargeEffect(action);
                move.togglePartOneDone();
                action.getUserSlot().setNeedsMove(false);
                action.getUserSlot().setNeedsAction(false);
                return;
            }
        }
        //Perform the actual move, if done charging
        for (BattleSlot target : action.getTargetSlots()) {
            int dmg = new DamageCalculator(weather, battlePrinter).damageCalc(action, target);
            inflictDamage(target, dmg);
            processAbilityAfterHit(action, target);
        }
        endChargeMove(action);

    }

    /**
     * Ends all the effects of a charge move
     * @param action The MoveAction representing the ChargeMove used
     */
    private void endChargeMove(MoveAction action) {
        ((ChargeMove) action.getMove()).togglePartOneDone();
        ArrayList<Invulnerabilty> twoTurnInvulnerabilities = new ArrayList<>(Arrays.asList(Invulnerabilty.FLYING,
                Invulnerabilty.DIGGING, Invulnerabilty.DIVING));
        if (twoTurnInvulnerabilities.contains(action.getUserSlot().getInvulnerabilty())) {
            action.getUserSlot().setInvulnerabilty(Invulnerabilty.NONE);
        }

        action.getUserSlot().setNeedsMove(true);
        action.getUserSlot().setNeedsAction(true);
    }


    /**
     * Processes the effect the charging turn of a ChargeMove takes
     * @param action The MoveAction representing the ChargeMove
     */
    private void processChargeEffect(MoveAction action) {
        switch (action.getMove().getEffect()) {
            case FLY:
                action.getUserSlot().setInvulnerabilty(Invulnerabilty.FLYING);
                break;
            case DIG:
                action.getUserSlot().setInvulnerabilty(Invulnerabilty.DIGGING);
                break;
            case SELF_DEF_UP:
                attemptToChangeStat(action.getUserSlot(), DEF_INDEX, 1, true);
                break;
            default:
                break;
        }
    }

    /**
     * Method that determines if the charging period of a move will be skipped
     * @param action The MoveAction representing the move used
     * @return True if the charge turn is skipped, false otherwise
     */
    private boolean skipCharging(MoveAction action) {
        //Solarbeam skips charging in sunlight
        if (action.getMove().getClass().equals(SolarBeam.class) && weather == Weather.SUN) {
            return true;
        }
        return false;
    }

    /**
     * Special method to handle multi-turn moves (e.g. Outrage)
     * @param action The MoveAction representing the move to be used
     */
    private void processMultiTurnMove(MoveAction action) {
        MultiTurnMove move = (MultiTurnMove) action.getMove();
        //PP is only deducted on the first turn
        if (!move.isFirstTurn()) {
            move.restorePP(1);
        }
        for (BattleSlot target : action.getTargetSlots()) {
            int damage = new DamageCalculator(weather, battlePrinter).damageCalc(action, target);
            inflictDamage(target, damage);
            processAbilityAfterHit(action, target);
        }
        move.decrementTurnsLeft();
        if (move.moveIsDone()) {
            endMultiTurnMove(action);
        } else {
            action.getUserSlot().setNeedsMove(false);
            action.getUserSlot().setNeedsAction(false);
        }

    }

    /**
     * Special method to handle multi-strike moves (e.g. Bullet Seed, Icicle Spear)
     * @param action The MoveAction representing the move to be used
     */
    private void processMultiStrikeMove(MoveAction action) {
        int timesHit = 0;
        for (int i = 0; i <  ((MultiStrikeMove) action.getMove()).getNumberofHits(); i++) {
            //No multi-strike move hits multiple targets, so we don't need to iterate here
            int dmg = new DamageCalculator(weather, battlePrinter).damageCalc(action, action.getTargetSlot());
            action.getUserSlot().reRollCrit();
            inflictDamage(action.getTargetSlot(), dmg);
            timesHit++;
            if (action.getTargetSlot().isFainted()) {
                break;
            }
            processExtraEffect(action, action.getTargetSlot(), dmg);

        }
        battlePrinter.printMultipleHits(timesHit);
    }

    /**
     * Currently, all multiturn moves end with the user being confused
     * @param action The actions representing the final move of a multi-turn move
     */
    private void endMultiTurnMove(MoveAction action) {
        BattleSlot slot = action.getUserSlot();
        battlePrinter.printMultiTurnMoveEnd(action.getMove(), slot.getPokémon());
        if (slot.confuse(true)) {
            battlePrinter.printConfusionGranted(slot.getPokémon());
        }
        slot.setNeedsAction(true);
        slot.setNeedsMove(true);

    }

    /**
     * Process a swap actions -- attempt to swap out pokémon
     * @param action The SwapAction representing the swap
     */
    private void processSwapAction(SwapAction action) {
        battlePrinter.printSwap(action);
        switchActiveMon(action.getUserSlot(), action.getToSwitchIn());
    }

    /**
     * General method for switching the active Pokémon for a side
     * @param slot The BattleSlot that the new Pokémon will enter
     * @param mon The new Pokémon switching in
     */
    //TODO: hazards
    private void switchActiveMon(BattleSlot slot, Pokémon mon) {
        slot.reset();
        slot.setPokémon(mon);
        //Add the new pokémon to the list of those the current opponent has faced
        slot.getSide().updateOpponents(false);
        activateSwitchInAbilities(slot);
        int poisonSpikeCount = slot.getSide().getPoisonSpikeCount();
        if (poisonSpikeCount != 0) {
            battlePrinter.printPoisonSpikesActivate(mon);
            if (poisonSpikeCount == 1) {
                attemptToGiveStatus(slot, Status.POISONED, false);
            } else {
                attemptToGiveStatus(slot, Status.BADLY_POISONED, false);
            }
        }
    }

    /**
     * Activate any abilities that trigger when a Pokémon comes into battle
     * @param slot The BattleSlot with the new Pokémon
     */
    private void activateSwitchInAbilities(BattleSlot slot) {
        switch(slot.getPokémon().getAbility()) {
            case INTIMIDATE:
                battlePrinter.printAbilityActivate(slot.getPokémon());
                for (BattleSlot opp : slot.getOpposingSlots()) {
                    attemptToChangeStat(opp, ATK_INDEX, -1, false);
                }
                break;
            default:
                break;
        }

    }

    private void activateSwitchInAbilities(Side side) {
        for (BattleSlot slot : side.getSlotsSafe()) {
            if (slot.getPokémon() != null) {
                activateSwitchInAbilities(slot);
            }
        }
    }

    private void inflictDamage(BattleSlot slot, int damage) {
        slot.getPokémon().takeHit(damage);
        battlePrinter.printDamage(slot.getPokémon(), damage);
        if (slot.isFainted()) {
            battlePrinter.printFainted(slot.getPokémon());
            slot.integrate();
            awardEXP(slot);
            checkIfBattleOver();
        }
    }

    /**
     * Award EXP to the surviving opponents of a Pokémon who just fainted -- if it's the computer who fainted
     * @param slot The BattleSlot of the fainted Pokémon
     */
    private void awardEXP(BattleSlot slot) {
        Pokémon fainted = slot.getPokémon();
        //Computer opponents don't gain EXP
        if (fainted.getOwner().equals(Owner.PLAYER)) {
            return;
        }
        double ownerStatus = (fainted.getOwner() == Owner.WILD) ? 1 : 1.5;
        double baseXPYield = (double) fainted.getXpYield();
        //lucky egg
        double e = 1;
        double share = 1;
        double level = (double) fainted.getLevel();
        double traded = 1;
        int totalExp = (int) Math.floor(ownerStatus*baseXPYield*e*level*share*traded/(7*share));
        int[] evYield = fainted.getEvYield();
        Set<Pokémon> toAward = new HashSet<>();
        for (Pokémon mon : slot.getOpponents()) {
            if (!mon.getStatus().equals(Status.FAINTED)) {
                for (int i = 0; i < evYield.length; i++) {
                    mon.addEVs(evYield[i], i);
                }
                toAward.add(mon);
            }
        }
        int expPerMon = totalExp/toAward.size();
        for (Pokémon mon : toAward) {
            mon.gainEXP(expPerMon);
        }

    }

    /**
     * Sees if one side has no Pokémon left to battle
     */
    private void checkIfBattleOver() {
        boolean playerLost = true;
        for (Pokémon mon : player.getParty()) {
            if (mon == null) {
                break;
            }
            if (!mon.getStatus().equals(Status.FAINTED)) {
                playerLost = false;
            }
        }
        boolean computerLost = true;
        for (Pokémon mon : compParty) {
            if (mon == null) {
                break;
            }
            if (!mon.getStatus().equals(Status.FAINTED)) {
                computerLost = false;
            }
        }
        if (playerLost || computerLost) {
            battleOver = true;
        }
    }

    /**
     * Some moves have side effects that can trigger after the damage step
     * @param action The MoveAction that just occurred
     * @param target The BattleSlot of the Pokémon that was just hit
     * @param dmg The amount of damage that was inflicted -- for recoil effects
     */
    private void processExtraEffect(MoveAction action, BattleSlot target, int dmg) {
        boolean shieldDust = target.getPokémon().getAbility() == Ability.SHIELD_DUST;
        if (battleOver) {
            return;
        }
        int chanceRollOne = ThreadLocalRandom.current().nextInt(0, 101);
        int chanceRollTwo = ThreadLocalRandom.current().nextInt(0, 101);
        int recoilDamage;
        //Any frozen Pokémon hit by a damaging Fire-type move is thawed out
        if (action.getMove().getType() == Type.FIRE && target.getPokémon().getStatus() == Status.FROZEN && !shieldDust) {
            target.getPokémon().setStatus(Status.NONE);
            battlePrinter.printThawMessage(target.getPokémon());
        }
        if (target.isRaging()) {
            if (target.getStatMod(ATK_INDEX) < MAX_STAT_MOD) {
                battlePrinter.printRageBuilding(target.getPokémon());
                attemptToChangeStat(target, ATK_INDEX, 1, false);
            }
        }
        switch (action.getMove().getEffect()) {
            case PARATEN:
                if (chanceRollOne < TEN_PERCENT && !target.getPokémon().getType().contains(Type.ELECTRIC) && !shieldDust) {
                    if (attemptToGiveStatus(target, Status.PARALYZED, false)) {
                        battlePrinter.printParalysisGranted(target.getPokémon());
                    }

                }
                break;
            case PARATHIRTY:
                if (chanceRollOne < THIRTY_PERCENT && !target.getPokémon().getType().contains(Type.ELECTRIC) && !shieldDust) {
                    if (attemptToGiveStatus(target, Status.PARALYZED, false)) {
                        battlePrinter.printParalysisGranted(target.getPokémon());
                    }
                }
                break;
            case PARALYZE:
                if (!target.getPokémon().getType().contains(Type.ELECTRIC) && !shieldDust) {
                    if (attemptToGiveStatus(target, Status.PARALYZED, false)) {
                        battlePrinter.printParalysisGranted(target.getPokémon());
                    }
                }
                break;
            case QUARTER_RECOIL:
                recoilDamage = (int) ((float) dmg / 4.0);
                if (recoilDamage < 1) {
                    recoilDamage = 1;
                }
                battlePrinter.printRecoilDamage(action.getUserSlot().getPokémon(), recoilDamage);
                inflictDamage(action.getUserSlot(), recoilDamage);
                break;
            case BURNTEN:
                if (chanceRollOne < TEN_PERCENT && !shieldDust) {
                    if (attemptToGiveStatus(target, Status.BURNED, false)) {
                        battlePrinter.printBurnGranted(target.getPokémon());
                    }
                }
                break;
            case BURN_TEN_FLINCH:
                if (chanceRollOne < TEN_PERCENT && !shieldDust) {
                    if (attemptToGiveStatus(target, Status.BURNED, false)) {
                        battlePrinter.printBurnGranted(action.getTargetSlot().getPokémon());
                    }
                }
                if (chanceRollTwo < TEN_PERCENT && !shieldDust) {
                    action.getTargetSlot().setFlinched();
                }
                break;
            case BURN:
                if (!shieldDust && attemptToGiveStatus(action.getTargetSlot(), Status.BURNED, false) ) {
                    battlePrinter.printBurnGranted(target.getPokémon());
                }
                break;
            case BURN_THIRD_RECOIL:
                if (!shieldDust && chanceRollOne < TEN_PERCENT) {
                    if (attemptToGiveStatus(action.getTargetSlot(), Status.BURNED, false)) {
                        battlePrinter.printBurnGranted(target.getPokémon());
                    }
                }
                recoilDamage = (int) ((float) dmg / 3.0);
                if (recoilDamage < 1) {
                    recoilDamage = 1;
                }
                battlePrinter.printRecoilDamage(action.getUserSlot().getPokémon(), recoilDamage);
                inflictDamage(action.getUserSlot(), recoilDamage);
                break;
            case FLINCH_THIRTY:
                if (chanceRollOne < THIRTY_PERCENT && !shieldDust) {
                    target.setFlinched();
                }
                break;
            case SPD_DOWN_TEN:
                if (chanceRollOne < TEN_PERCENT && !shieldDust) {
                    attemptToChangeStat(target, SPD_INDEX, -1, false);
                }
                break;
            case FLINCH_TEN:
                if (chanceRollOne < TEN_PERCENT && !shieldDust) {
                    target.setFlinched();
                }
                break;
            case CONFUSE_TWENTY:
                if (chanceRollOne < TWENTY_PERCENT && !shieldDust) {
                    if (target.confuse(false)) {
                        battlePrinter.printConfusionGranted(target.getPokémon());
                    }
                }
                break;
            case CONFUSE_THIRTY:
                if (chanceRollOne < THIRTY_PERCENT && !shieldDust) {
                    if (action.getTargetSlot().confuse(false)) {
                        battlePrinter.printConfusionGranted(target.getPokémon());
                    }
                }
                break;
            case SPDEF_DOWN_TEN:
                if (chanceRollOne < TEN_PERCENT && !shieldDust) {
                    attemptToChangeStat(target, SPDEF_INDEX, -1, false);
                }
                break;
            case REMOVE_HAZARDS:
                action.getUserSlot().getSide().resetHazards();
                action.getUserSlot().removePersonalHazards();
                battlePrinter.printRemoveHazards(action.getUserSlot().getPokémon());
                break;
            case FIRE_SPIN:
                int duration = (chanceRollOne < 50) ? 4 : 5;
                target.addNewBindingEffect(BindingEffect.FIRE_SPIN, duration);
                battlePrinter.printMonBound(target.getPokémon(), BindingEffect.FIRE_SPIN);
                break;
            case WRAP:
                int wrapDuration = (chanceRollOne < 50) ? 4 : 5;
                target.addNewBindingEffect(BindingEffect.WRAP, wrapDuration);
                battlePrinter.printMonBound(target.getPokémon(), BindingEffect.WRAP);
                break;
            case LIFT_PROTECT:
                if (target.getInvulnerabilty() == Invulnerabilty.PROTECTED) {
                    target.setInvulnerabilty(Invulnerabilty.NONE);
                    battlePrinter.printFeintActivate(target);
                }
                break;
            case DEF_DOWN_TWETNY:
                if (chanceRollOne < TWENTY_PERCENT) {
                    attemptToChangeStat(target, DEF_INDEX, -1, false);
                }
                break;
            case FLINCH_TWENTY:
                if (chanceRollOne < TWENTY_PERCENT && !shieldDust) {
                    target.setFlinched();
                }
                break;
            case BUG_BITE:
                if (target.getPokémon().getItem() != null &&
                        target.getPokémon().getItem()instanceof Berry) {
                    Berry berry = (Berry) target.getPokémon().getItem();
                    target.getPokémon().setItem(null);
                    //TODO: The user should eat the target's berry
                }
                break;
            case CONFUSE_TEN:
                if (chanceRollOne < TEN_PERCENT && !shieldDust) {
                    if (target.confuse(false)) {
                        battlePrinter.printConfusionGranted(target.getPokémon());
                    }
                }
                break;
            case RAISE_ALL_TEN:
                if (chanceRollOne < TEN_PERCENT) {
                    attemptToChangeStat(action.getUserSlot(), ATK_INDEX, 1, false);
                    attemptToChangeStat(action.getUserSlot(), DEF_INDEX, 1, false);
                    attemptToChangeStat(action.getUserSlot(), SPATK_INDEX, 1, false);
                    attemptToChangeStat(action.getUserSlot(), SPDEF_INDEX, 1, false);
                    attemptToChangeStat(action.getUserSlot(), SPD_INDEX, 1, false);
                }
                break;
            case POISON_THIRTY:
                if (chanceRollOne < THIRTY_PERCENT && !shieldDust) {
                    attemptToGiveStatus(target, Status.POISONED, false);
                }
                break;
            case POISON_TWENTY:
                if (chanceRollOne < TWENTY_PERCENT && !shieldDust) {
                    attemptToGiveStatus(target, Status.POISONED, false);
                }
                break;
            case FELL_STINGER:
                if (action.getTargetSlot().isFainted()) {
                    attemptToChangeStat(action.getUserSlot(), ATK_INDEX, 3, false);
                }
                break;
            case SWITCH_USER:
                if (slotCanSwitch(action.getUserSlot())) {
                    Pokémon mon = getSwap(action.getUserSlot());
                    switchActiveMon(action.getUserSlot(), mon);
                }
                break;
            case SELF_ATK_DEF_DOWN:
                attemptToChangeStat(action.getUserSlot(), ATK_INDEX, -1, true);
                attemptToChangeStat(action.getUserSlot(), DEF_INDEX, -1, true);
                break;
            default:
                break;
        }

    }

    private Pokémon getSwap(BattleSlot slot) {
        if (slot.getPokémon().getOwner() == Owner.PLAYER) {
            return getReplacementMon(slot.getPokémon(), player.getParty());
        } else {
            return getForcedSwitch(slot, compParty);
        }
    }

    private boolean slotCanSwitch(BattleSlot slot) {
        if (slot.getPokémon().getOwner() == Owner.PLAYER) {
            return playerCanSwitch(slot);
        } else {
            return compCanSwitch(slot);
        }
    }

    private boolean compCanSwitch(BattleSlot slot) {
        if (slot.getPokémon().getOwner() == Owner.WILD) {
            return false;
        } else {
            if (!slot.getBindingEffects().isEmpty()) {
                //Binding effects stop you from switching
                return false;
            }
            for (Pokémon mon : compParty) {
                if (compSide.contains(mon)) {
                    continue;
                } else if (mon.getStatus() != Status.FAINTED) {
                    return true;
                }
            }
            return false;
        }
    }

    private void processAbilityAfterHit(MoveAction action, BattleSlot target) {
        int randomRoll = ThreadLocalRandom.current().nextInt(1, 101);
        Ability attackerAbility = action.getUserSlot().getPokémon().getAbility();
        Ability defenderAbility = target.getPokémon().getAbility();
        switch (attackerAbility) {
            default:
                break;
        }
        switch (defenderAbility) {
            case STATIC:
                if (randomRoll <= THIRTY_PERCENT && action.getMove().getMakesContact()) {
                    if (attemptToGiveStatus(action.getUserSlot(), Status.PARALYZED, false)) {
                        battlePrinter.printAbilityActivate(target.getPokémon());
                        battlePrinter.printParalysisGranted(action.getUserSlot().getPokémon());
                    }
                }
                break;
            case ANGER_POINT:
                if (action.wasCrit()) {
                    battlePrinter.printAbilityActivate(target.getPokémon());
                    attemptToChangeStat(target, ATK_INDEX, 6, false);
                }
                break;
            case POISON_POINT:
                if (randomRoll <= THIRTY_PERCENT) {
                    if (attemptToGiveStatus(action.getUserSlot(), Status.POISONED, false)) {
                        battlePrinter.printAbilityActivate(target.getPokémon());
                        battlePrinter.printPoisonGranted(action.getUserSlot().getPokémon());
                    }
                }
                break;
            default:
                break;
        }
    }

    /**
     * A bunch of housekeeping at the end of a turn
     */
    private void endTurn() {
        if (battleOver) {
            return;
        }
        playerSide.endTurnUpdate(true);
        compSide.endTurnUpdate(false);
        processWeather();
        endTurnDamage();
        //Battle might have ended from end turn damage
        if (battleOver) {
            return;
        }
        for (BattleSlot slot : getAllSlots()) {
            if (slot.getPokémon() != null && slot.isFainted()) {
                if (attemptToReplaceSlot(slot)) {
                    activateSwitchInAbilities(slot);
                }
            }
        }
    }

    private boolean attemptToReplaceSlot(BattleSlot slot) {
        if (slot.getPokémon().getOwner().equals(Owner.PLAYER)) {
            return replacePlayerMon(slot);
        } else {
            return replaceCompMon(slot);
        }
    }

    /**
     * Find all Pokémon on a team that are not currently in battle and have HP left
     * @param party The list of all Pokémon on a side
     * @return A list of all Pokémon eligible to switch in
     */
    private ArrayList<Pokémon> getReserveMons(ArrayList<Pokémon> party) {
        ArrayList<Pokémon> toReturn = new ArrayList<>();
        boolean isPlayer = party.get(0).getOwner() == Owner.PLAYER;
        Side side = (isPlayer) ? playerSide : compSide;
        boolean doubleBattle = side.getSlotTwo() != null;
        for (Pokémon mon : party) {
            if (side.getSlotOne().getOriginal().equals(mon) || (doubleBattle && side.getSlotTwo().getOriginal().equals(mon))) {
                continue;
            } else if (mon.getStatus() != Status.FAINTED) {
                toReturn.add(mon);
            }
        }
        return toReturn;
    }

    private boolean replacePlayerMon(BattleSlot playerSlot) {
        ArrayList<Pokémon> reserves = getReserveMons(player.getParty());
        if (reserves.size() == 0) {
            playerSlot.reset();
            playerSlot.setPokémon(null);
            return false;
        } else {
            Pokémon newMon = getReplacementMon(playerSlot.getPokémon(), reserves);
            battlePrinter.printSendOutPokémon(newMon);
            playerSlot.reset();
            playerSlot.setPokémon(newMon);
            playerSide.updateOpponents(false);
            return true;
        }
    }

    private boolean replaceCompMon(BattleSlot compSlot) {
        ArrayList<Pokémon> reserves = getReserveMons(compParty);
        if (reserves.size() == 0) {
            compSlot.reset();
            compSlot.setPokémon(null);
            return false;
        } else {
            Pokémon newMon = reserves.get(0);
            compSlot.reset();
            compSlot.setPokémon(newMon);
            compSide.updateOpponents(false);
            battlePrinter.printTrainerSendOut(oppTrainer, newMon);
            return true;
        }
    }

    /**
     * A few things can cause recurring damage at the end of a turn
     * Leech seed, some status conditions, etc
     */
    private void endTurnDamage() {
        for (BattleSlot slot : getAllActiveSlots()) {
            if(slot.isLeechSeeded()) {
                leechSeedDamage(slot);
            }
            if (slot.isFainted()) {
                return;
            }
            Status status = slot.getPokémon().getStatus();
            if (slot.getPokémon().getAbility() == Ability.SHED_SKIN) {
                if (status != Status.NONE) {
                    int shedSkinRoll = new Random().nextInt(100);
                    if (shedSkinRoll > 70) {
                        battlePrinter.printShedSkinActivate(slot.getPokémon());
                        slot.getPokémon().setStatus(Status.NONE);
                    }
                }
            }
            if (status == Status.POISONED || status == Status.BADLY_POISONED || status == Status.BURNED) {
                statusDamage(slot);
            }
            if (slot.isFainted()) {
                return;
            }
            bindingDamage(slot);
        }
    }

    private void leechSeedDamage(BattleSlot slot) {
        int eighthHP = (int) ((double) slot.getPokémon().getStats()[HP_INDEX] / 8.0);
        battlePrinter.printLeechSeedActivate(slot.getPokémon());
        inflictDamage(slot, eighthHP);
        if (slot.getLeachingSlot().getPokémon() != null && !slot.getLeachingSlot().isFainted()) {
            battlePrinter.printRestoreHP(slot.getLeachingSlot().getPokémon(), eighthHP);
            slot.getLeachingSlot().getPokémon().heal(eighthHP);
        }
    }

    /**
     * Method for calculating and inflicting damage for any binding effects (e.g. Fire Spin)
     * @param slot The BattleSlot to be checked for binding effects
     */
    private void bindingDamage(BattleSlot slot) {
        int eighthHP = (int) ((double) slot.getPokémon().getStats()[HP_INDEX] / 8.0);
        List<BindingEffect> removedEffects = new ArrayList<>();
        for (BindingEffect effect : slot.getBindingEffects().keySet()) {
            int count = slot.getBindingEffects().get(effect);
            if (count == 0) {
                removedEffects.add(effect);
            } else {
                battlePrinter.printBindingDamage(slot.getPokémon(), effect);
                inflictDamage(slot, eighthHP);
                slot.getBindingEffects().put(effect, count-1);
                if (slot.isFainted()) {
                    break;
                }
            }
        }
        if (slot.isFainted()) {
            return;
        }
        for (BindingEffect effect : removedEffects) {
            battlePrinter.printBindEnd(slot.getPokémon(), effect);
            slot.removeBindingEffect(effect);
        }
    }

    /**
     * Inflict any damage that comes from a Pokémon being poisoned, badly poisoned, or burned
     * @param slot The BattleSlot with the Pokémon to be hurt
     */
    private void statusDamage(BattleSlot slot) {
        Pokémon mon = slot.getPokémon();
        int eighthHP = (int) ((double) mon.getStats()[HP_INDEX] / 8.0);
        switch(mon.getStatus()) {
            case BURNED:
                battlePrinter.printBurnDamage(mon);
                inflictDamage(slot, eighthHP);
                break;
            case POISONED:
                battlePrinter.printPoisonDamage(mon);
                inflictDamage(slot, eighthHP);
                break;
            case BADLY_POISONED:
                int toxicCounter = mon.getToxicCounter();
                int dmg = ((toxicCounter/16)*mon.getStats()[HP_INDEX]);
                battlePrinter.printPoisonDamage(mon);
                inflictDamage(slot, dmg);
                mon.incrementToxicCounter();
                break;
            default:
                break;
        }
    }

    /**
     * Weather has a lot of interactions at the end of a turn
     */
    private void processWeather() {
       weatherTimer--;
       if (weatherTimer == 0) {
           battlePrinter.printWeatherEnd(weather);
           weather = Weather.NONE;
       }
       if (weather != Weather.NONE) {
           battlePrinter.printWeather(weather);
           weatherDamage();
       }

    }

    /**
     * Method to handle potential damage from the weather
     */
    private void weatherDamage() {
        if (weather != Weather.HAIL && weather != Weather.SANDSTORM) {
            return;
        }
        for (BattleSlot slot : getAllActiveSlots()) {
            if (!weatherImmune(weather, slot.getPokémon())) {
                battlePrinter.printWeatherDamage(slot.getPokémon(), weather);
                int dmg = slot.getPokémon().getStats()[HP_INDEX] / 16;
                inflictDamage(slot, dmg);
            }
        }
    }

    private void endBattle() {
        playerSide.integrate();
        if (fleeing) {
            battlePrinter.printRunFromBattle();
        } else {
            boolean playerWon = (getFirstHealthyPokémon(player.getParty()) != null);
            if (playerWon) {
                if (!wild) {
                    battlePrinter.printWonAgainstTrainer(oppTrainer);
                    player.addMoney(oppTrainer.getPrizeMoney());
                } else {
                    System.out.println("You won the battle!");
                }
            } else {
                if (!wild) {
                    battlePrinter.printLostToTrainer(oppTrainer);
                }
                battlePrinter.printWhiteOut();
            }
        }
    }

    private ArrayList<BattleSlot> getAllActiveSlots() {
        ArrayList<BattleSlot> slots = new ArrayList<>();
        slots.addAll(playerSide.getSlotsSafe());
        slots.addAll(compSide.getSlotsSafe());
        return slots;
    }

    private ArrayList<BattleSlot> getAllSlots() {
        ArrayList<BattleSlot> slots = new ArrayList<>();
        slots.addAll(playerSide.getSlots());
        slots.addAll(compSide.getSlots());
        return slots;
    }

    @Override
    public int compare(Action a1, Action a2) {
        if (a1.getPriority() > a2.getPriority()) {
            return -1;
        } else if (a2.getPriority() > a1.getPriority()) {
            return 1;
        } else {
            //MoveActions cannot be the same priority as anything else but another move actions
            //So it goes to speed
            if (a1 instanceof MoveAction) {
                double a1Speed = getSpeed(a1.getUserSlot());
                double a2Speed = getSpeed(a2.getUserSlot());
                if (a1Speed > a2Speed) {
                    return -1;
                } else if (a2Speed > a1Speed) {
                    return 1;
                } else { //Speed tie; 50-50 flip
                    if (ThreadLocalRandom.current().nextInt(100) < 50) {
                        return -1;
                    } else {
                        return 1;
                    }
                }
            } else { //If two Pokémon are using non-attack Actions, the Player always goes first
                if (a1.getUserSlot().getPokémon().getOwner().equals(Owner.PLAYER) &&
                        !a2.getUserSlot().getPokémon().getOwner().equals(Owner.PLAYER)) {
                    return -1;
                } else if (a2.getUserSlot().getPokémon().getOwner().equals(Owner.PLAYER) &&
                        !a1.getUserSlot().getPokémon().getOwner().equals(Owner.PLAYER)) {
                    return 1;
                } else { //2 switches, or 2 items both on the same side. Order is irrelevant
                    return 0;
                }
            }
        }
    }
}
