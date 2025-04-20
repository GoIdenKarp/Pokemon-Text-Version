package ui;

import actions.MoveAction;
import actions.SwapAction;
import areas.Area;
import battle.BattleSlot;
import battle.Side;
import enums.*;
import game.Player;
import items.Bag;
import items.Item;
import moves.Move;
import moves.ChargeMove;
import pokémon.Pokémon;
import trainer.Trainer;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by AriG on 6/14/17.
 */
public class GameFrame extends JFrame{

    private static final int LABEL_HEIGHT = 25;
    private static final ArrayList<Character> vowels = new ArrayList<>(Arrays.asList('a', 'e', 'i', 'o', 'u'));

    private GameCanvas gameCanvas;

    private List<String> canvasText;
    private BattlePrinter battlePrinter;
    private InputHelper inputHelper;
    private GamePrinter gamePrinter;

    private int timeDelay;


    public GameFrame(boolean debugMode) {
        setTitle("Pokémon Text Version");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(1000, 600);
        setResizable(false);
        setLocationRelativeTo(null);
        battlePrinter = new BattlePrinter();
        inputHelper = new InputHelper();
        gameCanvas = new GameCanvas();
        gamePrinter = new GamePrinter();
        this.add(gameCanvas);
        setVisible(true);
        timeDelay = (debugMode) ? 0 : 1500;
    }


    public BattlePrinter getBattlePrinter() {
        return battlePrinter;
    }

    public GamePrinter getGamePrinter() {return gamePrinter; }

    public InputHelper getInputHelper() {
        return inputHelper;
    }

    public class GameCanvas extends JPanel {


        public GameCanvas() {
            setLayout(new GridLayout(0, BoxLayout.Y_AXIS));
        }

        private void setUpText() {
            removeAll();
            int cutoff;
            int numLabels = getHeight()/LABEL_HEIGHT;
            setLayout(new GridLayout(numLabels, 1));
            if (canvasText == null) {
                canvasText = new ArrayList<>();
            }
            cutoff = (canvasText.size() - numLabels <= 0) ? 0 : canvasText.size() - numLabels;
            List<String> newText = new ArrayList<>(numLabels);
            for (int i = cutoff; i < canvasText.size(); i++) {
                try {
                    newText.add(canvasText.get(i));
                } catch (ArrayIndexOutOfBoundsException e) {
                    break;
                }
            }
            canvasText = newText;
            for (int i = 0; i < numLabels; i++) {
                try {
                    JPanel panel = new JPanel();
                    panel.setSize(getWidth(), LABEL_HEIGHT);
                    JLabel label = new JLabel(canvasText.get(i), SwingConstants.LEFT);
                    label.setMinimumSize(new Dimension(getWidth(), LABEL_HEIGHT));
                    label.setHorizontalTextPosition(0);
                    panel.add(label);
                    label.setAlignmentX(CENTER_ALIGNMENT);
                    add(panel, BorderLayout.WEST);
                } catch (IndexOutOfBoundsException e) {
                    break;
                }
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
        }

        public void addString(String newString) {
            if (canvasText == null) {
                canvasText = new ArrayList<>();
            }
            canvasText.add(newString);
            setUpText();
            updateUI();
            try {
                TimeUnit.MILLISECONDS.sleep(timeDelay);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    public  void addString(String newString) {

        gameCanvas.addString(newString);
    }

    public class BattlePrinter {

        private static final String WILD_BATTLE_START = "A wild %s appeared!\n";
        private static final String TRAINER_BATTLE_START = "%s would like to battle!\n";
        private static final String I_CHOOSE_YOU = "I choose you, %s!";
        private static final String TURN_START_DESCRIPTION = "%s level %d %s is %s. Its HP is %d/%d.\n";
        private static final String SELECT_MOVE_PROMPT = "What move should be used: %s\n";
        private static final String PLAYER_SWAP = "Come back, %s!\nGo, %s!\n";
        private static final String COMPUTER_SWAP = "%s called back %s!\n%s sent out %s!\n";
        private static final String WAKE_MESSAGE = "%s %s woke up!\n";
        private static final String IS_ASLEEP_MESSAGE = "%s %s is fast asleep...\n";
        private static final String THAW_MESSAGE = "%s %s thawed out!\n";
        private static final String IS_FROZEN_MESSAGE = "%s %s is frozen! It can't move!\n";
        private static final String IS_CONFUSED_MESSAGE = "%s %s is confused!\n";
        private static final String SNAP_OUT_MESSAGE = "%s %s snapped out of confusion!\n";
        private static final String HURT_SELF_MESSAGE = "%s %s hurt itself in confusion!\n";
        private static final String IS_INFATUATED_MESSAGE = "%s %s is infatuated!\n";
        private static final String IMMOBILIZED_MESSAGE = "%s %s is immobilized by love!\n";
        private static final String IS_PARALYZED_MESSAGE = "%s %s is fully paralyzed! It can't move!\n";
        private static final String SUPER_EFFECTIVE = "It's super effective!\n";
        private static final String NOT_VERY_EFFECTIVE = "It's not very effective...\n";
        private static final String CRITICAL_HIT = "A critical hit!\n";
        private static final String NO_EFFECT = "It doesn't effect %s %s...\n";
        private static final String USE_MOVE = "%s %s used %s!\n";
        private static final String NO_TARGET = "But there was no target...\n";
        private static final String FAINTED = "%s %s fainted!\n";
        private static final String MOVE_MISSED = "%s %s missed!\n";
        private static final String BURN_DAMAGE = "%s %s was hurt by its burn!\n";
        private static final String POISON_DAMAGE = "%s %s was hurt by poison!\n";
        private static final String RAIN_START = "It started to rain!\n";
        private static final String SUN_START = "The sunlight became harsh!\n";
        private static final String SANDSTORM_START = "A sandstorm is brewing!\n";
        private static final String HAIL_START = "It started to hail!\n";
        private static final String SUN_MESSAGE = "The sun is shining.\n";
        private static final String HAIL_MESSAGE = "Hail continues to fall.\n";
        private static final String RAIN_MESSAGE = "Rain continues to fall.\n";
        private static final String SANDSTORM_MESSAGE = "The sandstorm rages.\n";
        private static final String WEATHER_DAMAGE_MESSAGE = "%s %s is buffeted by the %s.\n";
        private static final String HAIL_END = "The hail stopped.\n";
        private static final String SANDSTORM_END = "The sandstorm stopped.\n";
        private static final String SUN_END = "The sunlight faded.\n";
        private static final String RAIN_END = "The rain stopped.\n";
        private static final String BEAT_TRAINER = "You defeated %s!\n";
        private static final String TRAINER_WIN_MESSAGE =  "%s: %s!\n";
        private static final String WIN_MONEY = "You won $%d!\n";
        private static final String WHITE_OUT_ONE = "You are out of usable Pokémon!\n";
        private static final String WHITE_OUT_TWO = "You whited out!\n";
        private static final String LOST_TO_TRAINER = "You lost to %s!";
        private static final String TRAINER_LOSE_MESSAGE = "\n%s: %s\n";
        private static final String DAMAGE_TAKEN = "%s %s took %d damage!\n";
        private static final String NOT_ENOUGH_POKEMON = "You don't have any other Pokémon who can battle! You can't switch!\n";
        private static final String SWAP_CHOICE_IS_FAINTED = "%s has no energy left to battle!\n";
        private static final String SWITCH_PROMPT = "Who should switch in?\n";
        private static final String NO_SWITCH_SELF = "%s is already in battle!\n";
        private static final String SEND_OUT_POKÉMON = "I choose you, %s!\n";
        private static final String TRAINER_SEND_OUT = "%s sent out %s!\n";
        private static final String PARALYSIS_GRANTED = "%s %s is paralyzed! It may not be able to move!\n";
        private static final String MOVE_FAILED = "But it failed...\n";
        private static final String PROTECTION_ACTIVE = "%s protected itself!\n";
        private static final String STAT_RAISE_FAIL = "%s %s's %s couldn't go higher!\n";
        private static final String STAT_LOWER_FAIL = "%s %s's %s couldn't go lower!\n";
        private static final String STAT_RAISE = "%s %s's %s rose%s!\n";
        private static final String STAT_LOWER = "%s %s's %s fell%s!\n";
        private static final String ABILITY_ACTIVATE = "%s %s's %s!\n";
        private static final String RECOIL_DAMAGE = "%s %s was hurt by recoil for %d damage!\n";
        private static final String BURN_GRANTED = "%s %s was burned!\n";
        private static final String POISON_GRANTED = "%s %s was poisoned!\n";
        private static final String BAD_POISON_GRANTED = "%s %s was badly poisoned!\n";
        private static final String SLEEP_GRANTED = "%s %s fell asleep!\n";
        private static final String MON_FLINCHED = "%s %s flinched!\n";
        private static final String CONFUSION_GRANTED = "%s %s became confused!\n";
        private static final String HEALTH_RESTORED = "%s %s restored %d HP!\n";
        private static final String LIGHT_SCREEN_ACTIVATE = "Light Screen raised %s team's Special Defense!\n";
        private static final String REFLECT_ACTIVATE = "Reflect raised %s team's Defense!\n";
        private static final String LIGHT_SCREEN_OVER = "%s Light Screen faded!\n";
        private static final String REFLECT_OVER = "%s Reflect faded!\n";
        private static final String MON_BOUND = "%s %s was trapped by %s!\n";
        private static final String BINDING_DAMAGE = "%s %s was hurt by %s!\n";
        private static final String BIND_END = "%s %s was freed from %s!\n";
        private static final String HAZARDS_REMOVED = "%s %s removed all hazards from their side of the field!\n";
        private static final String LEECH_SEEDED = "%s %s was seeded!\n";
        private static final String LEECH_SEED_ACTIVATE = "%s %s's health is sapped by Leech Seed!\n";
        private static final String MULTIPLE_HITS = "Hit %d time%s!";
        private static final String WORRY_SEED_ACTIVATE = "%s %s acquired Insomnia!";
        private static final String FEINT_ACTIVATE = "%s %s fell for the feint!";
        private static final String ACCURACY_CANT_DECREASE = "%s %s accuracy couldn't be lowered!";
        private static final String FOCUS_ENERGY_STRING = "%s %s is getting pumped!";
        private static final String FLEE_FAILED = "You couldn't get away!";
        private static final String RUN_FROM_BATTLE = "You got away safely!";
        private static final String TAILWIND_ACTIVATE = "The tailwind blew from behind %s!";
        private static final String TAILWIND_END = "%s tailwind petered out.";
        private static final String MIRROR_MOVE_ACTIVATE = "Mirror Move became %s!";
        private static final String NO_RUN_FROM_TRAINER = "You can't run from a trainer battle!";
        private static final String NO_PP_LEFT = "That move has no PP left!";
        private static final String ITEM_USED = "You used %s %s!";
        private static final String CANNOT_CATCH_OWNED = "You can't catch a Pokémon that isn't wild!";
        private static final String HEALING_ITEM_USED = "%s %s was healed by %d!";
        private static final String NO_SHAKES = "Oh no! The Pokémon broke free!";
        private static final String ONE_SHAKE = "Aww! It appeared to be caught!";
        private static final String TWO_SHAKES = "Aargh! Almost had it!";
        private static final String THREE_SHAKES = "Gah! It was so close, too!";
        private static final String CATCH_SUCCESS = "Gotcha! %s was caught!";
        private static final String SHED_SKIN_ACTIVATE = "%s %s's Shed Skin cured its status problem!";
        private static final String SAFEGUARD_ACTIVATE = "%s %s's team is now protected by Safeguard!";
        private static final String SAFEGUARD_END = "%s Safeguard ended.";
        private static final String TOXIC_SPIKES_SET = "Toxic Spikes were scattered around %s team!";
        private static final String POISON_SPIKES_ACTIVATE = "%s were poisoned by the toxic spikes!";
        private static final String RAGE_BUILDING = "%s %s's Rage is building!";
        private static final String SENT_TO_PC = "%s was sent to the PC.";
        private static final String SEND_OUT_DOUBLE = "Let's go, %s and %s!";
        private static final String TRAINER_SEND_OUT_DOUBLE = "%s sent out %s and %s!";
        private static final String EVADE_ATTACK = "%s %s evaded the attack!";
        private static final String MULTI_TURN_MOVE_END = "%s %s\'s %s ended.";
        private static final String TAUNT_ACTIVATE = "%s %s fell for the taunt!";
        private static final String TAUNT_STOPS_MOVE = "%s %s can't use %s after the taunt!";
        private static final String TAUNT_ENDED = "%s %s got over the taunt.";
        private static final String ENCORE_START = "%s %s got an received an encore!";
        private static final String ENCORE_END = "%s %s's encore ended.";
        private static final String HAZE_ACTIVATE = "All stat changes were eliminated!";
        private static final String ONE_HIT_KO = "It's a One-hit KO!";
        private static final String BUFFER = "---------------------------------\n";


        private String getBindingEffectName(BindingEffect effect) {
            switch (effect) {
                case FIRE_SPIN:
                    return "Fire Spin";
                default:
                    return "";
            }
        }

        private final String[] statNames = {"HP", "Attack", "Defense", "Special Attack", "Special Defense", "Speed", "Accuracy", "Evasion"};



        private String formatStatus(Status status) {
            switch (status) {
                case NONE:
                    return "healthy";
                default:
                    return status.toString().toLowerCase().replace("_", " ");
            }
        }

        public void printCantRaiseStat(Pokémon mon, int statIndex) {
            addString(String.format(STAT_RAISE_FAIL, mon.getOwnerMsg(), mon, statNames[statIndex]));

        }

        public void printCantLowerStat(Pokémon mon, int statIndex) {
            addString(String.format(STAT_LOWER_FAIL, mon.getOwnerMsg(), mon, statNames[statIndex]));
        }

        public void printStatRaise(Pokémon mon, int statIndex, boolean sharply) {
            String sharpChange = (sharply) ? " sharply" : "";
            addString(String.format(STAT_RAISE, mon.getOwnerMsg(), mon, statNames[statIndex], sharpChange));
        }

        public void printStatFall(Pokémon mon, int statIndex, boolean sharply) {
            String sharpChange = (sharply) ? "sharply" : "";
            addString(String.format(STAT_LOWER, mon.getOwnerMsg(), mon, statNames[statIndex], sharpChange));
        }


        public void printWildBattleStart(Pokémon playerMon, Pokémon compMon) {
            addString(String.format(WILD_BATTLE_START, compMon));
            addString(String .format(I_CHOOSE_YOU, playerMon));

        }

        public void printTrainerBattleStart(Trainer trainer, Side playerSide, Side compSide) {
            addString(String.format(TRAINER_BATTLE_START, trainer));
            if (playerSide.getSlots().size() > 1) {
                printTrainerSendOutDouble(trainer, compSide.getSlots());
                if (playerSide.getSlotTwo().getPokémon() == null) {
                    addString(String.format(I_CHOOSE_YOU, playerSide.getSlotOne().getPokémon()));
                } else {
                    addString(String.format(SEND_OUT_DOUBLE, playerSide.getSlotOne().getPokémon(), playerSide.getSlotTwo().getPokémon()));
                }
            } else {
                printTrainerSendOut(trainer, compSide.getSlotOne().getPokémon());
                addString(String.format(I_CHOOSE_YOU, playerSide.getSlotOne().getPokémon()));
            }
        }

        private void printTrainerSendOutDouble(Trainer trainer, ArrayList<BattleSlot> slots) {
            addString(String.format(TRAINER_SEND_OUT_DOUBLE, trainer, slots.get(0).getPokémon(), slots.get(1).getPokémon()));
        }

        public void printAbilityActivate(Pokémon mon) {
            addString(String.format(ABILITY_ACTIVATE, mon.getOwnerMsg(), mon, mon.getAbility()));
        }

        public void printSendOutPokémon(Pokémon mon) {
            addString(String.format(SEND_OUT_POKÉMON, mon));
        }

        public void printTrainerSendOut(Trainer trainer, Pokémon mon) {
            addString(String.format(TRAINER_SEND_OUT, trainer, mon));
        }

        public void printChargeEffect(MoveAction action) {
            //The if statement should always be true if this method is called
            if (action.getMove() instanceof ChargeMove) {
                addString(String.format(((ChargeMove) action.getMove()).getChargeMessage(),
                        action.getUserSlot().getPokémon()));
            }

        }

        public void printProtectionActive(Pokémon mon) {
            addString(String.format(PROTECTION_ACTIVE, mon));
        }

        public void printTurnStartDescription(Pokémon mon) {
            addString(String.format(TURN_START_DESCRIPTION, mon.getOwnerMsg(), mon.getLevel(), mon, formatStatus(mon.getStatus()),
                    mon.getCurrentHP(), mon.getStats()[0]));
        }

        public void printMoveChoicePrompt(List<Move> moves) {
            addString(String.format(SELECT_MOVE_PROMPT, moves, moves.size()));
        }

        public void printSwap(SwapAction action) {
            if (action.getUserSlot().getPokémon().getOwner() == Owner.PLAYER) {
                printPlayerSwap(action);
            } else {
                printComputerSwap(action);
            }
        }

        private void printPlayerSwap(SwapAction action) {
            addString(String.format(PLAYER_SWAP, action.getUserSlot().getPokémon(), action.getToSwitchIn()));
        }

        private void printComputerSwap(SwapAction action) {
            addString(String.format(COMPUTER_SWAP, action.getUserSlot(), action.getUserSlot().getPokémon(), action.getUserSlot(), action.getToSwitchIn()));
        }

        public void printWakeMessage(Pokémon mon) {
            addString(String.format(WAKE_MESSAGE, mon.getOwnerMsg(), mon));
        }

        public void printIsAsleepMessage(Pokémon mon) {
            addString(String.format(IS_ASLEEP_MESSAGE, mon.getOwnerMsg(), mon));
        }

        public void printThawMessage(Pokémon mon) {
            addString(String.format(THAW_MESSAGE, mon.getOwnerMsg(), mon));
        }

        public void printIsFrozenMessage(Pokémon mon) {
            addString(String.format(IS_FROZEN_MESSAGE, mon.getOwnerMsg(), mon));
        }

        public void printSnapOutMessage(Pokémon mon) {
            addString(String.format(SNAP_OUT_MESSAGE, mon.getOwnerMsg(), mon));
        }

        public void printIsConfusedMessage(Pokémon mon) {
            addString(String.format(IS_CONFUSED_MESSAGE, mon.getOwnerMsg(), mon));
        }

        public void printHurtSelfMessage(Pokémon mon) {
            addString(String.format(HURT_SELF_MESSAGE, mon.getOwnerMsg(), mon));
        }

        public void printIsInfatuatedMessage(Pokémon mon) {
            addString(String.format(IS_INFATUATED_MESSAGE, mon.getOwnerMsg(), mon));
        }

        public void printImmobilizedByLoveMessage(Pokémon mon) {
            addString(String.format(IMMOBILIZED_MESSAGE, mon.getOwnerMsg(), mon));
        }

        public void printParalyzedMessage(Pokémon mon) {
            addString(String.format(IS_PARALYZED_MESSAGE, mon.getOwnerMsg(), mon));
        }

        public void printMoveEffectiveness(double mod, Pokémon target) {
            if (mod >= 2) {
                addString(String.format(SUPER_EFFECTIVE));
            } else if (mod == 0) {
                addString(String.format(NO_EFFECT, target.getOwnerMsg().toLowerCase(), target));
            } else if (mod < 1) {
                addString(String.format(NOT_VERY_EFFECTIVE));
            }
        }

        public void printMoveAction(MoveAction action) {
            addString(String.format(USE_MOVE, action.getUserSlot().getPokémon().getOwnerMsg(),
                    action.getUserSlot().getPokémon(), action.getMove()));
        }

        public void printCriticalHit() {
            addString(String.format(CRITICAL_HIT));
        }

        public void printNoTarget() {
            addString(String.format(NO_TARGET));
        }

        public void printFainted(Pokémon mon) {
            addString(String.format(FAINTED, mon.getOwnerMsg(), mon));
        }

        public void printMoveMissed(Pokémon mon) {
            addString(String.format(MOVE_MISSED, mon.getOwnerMsg(), mon));
        }

        public void printBurnDamage(Pokémon mon) {
            addString(String.format(BURN_DAMAGE, mon.getOwnerMsg(), mon));
        }

        public void printPoisonDamage(Pokémon mon) {
            addString(String.format(POISON_DAMAGE, mon.getOwnerMsg(), mon));
        }

        public void printWeather(Weather weather) {
            switch (weather) {
                case SUN:
                    addString(String.format(SUN_MESSAGE));
                    break;
                case RAIN:
                    addString(String.format(RAIN_MESSAGE));
                    break;
                case HAIL:
                    addString(String.format(HAIL_MESSAGE));
                    break;
                case SANDSTORM:
                    addString(String.format(SANDSTORM_MESSAGE));
                    break;
                default:
                    break;
            }
        }

        public void printWeatherDamage(Pokémon mon, Weather weather) {
            addString(String.format(WEATHER_DAMAGE_MESSAGE, mon.getOwnerMsg(), mon, weather.toString().toLowerCase()));
        }

        public void printWeatherStart(Weather weather) {
            switch (weather) {
                case SUN:
                    addString(String.format(SUN_START));
                    break;
                case RAIN:
                    addString(String.format(RAIN_START));
                    break;
                case HAIL:
                    addString(String.format(HAIL_START));
                    break;
                case SANDSTORM:
                    addString(String.format(SANDSTORM_START));
                    break;
                default:
                    break;
            }
        }

        public void printWeatherEnd(Weather weather) {
            switch (weather) {
                case SUN:
                    addString(String.format(SUN_END));
                    break;
                case RAIN:
                    addString(String.format(RAIN_END));
                    break;
                case HAIL:
                    addString(String.format(HAIL_END));
                    break;
                case SANDSTORM:
                    addString(String.format(SANDSTORM_END));
                    break;
                default:
                    break;
            }
        }

        public void printWonAgainstTrainer(Trainer trainer) {
            addString(String.format(BEAT_TRAINER, trainer));
            addString(String.format(TRAINER_WIN_MESSAGE, trainer, trainer.getWinMsg()));
            addString(String.format(WIN_MONEY, trainer.getPrizeMoney()));
        }

        public void printWhiteOut() {
            addString(String.format(WHITE_OUT_ONE));
            addString(String.format(WHITE_OUT_TWO));
        }

        public void printLostToTrainer(Trainer trainer) {
            addString(String.format(LOST_TO_TRAINER, trainer));
            addString(String.format(TRAINER_LOSE_MESSAGE, trainer, trainer.getLoseMsg()));
        }

        public void printDamage(Pokémon mon, int damage) {
            addString(String.format(DAMAGE_TAKEN, mon.getOwnerMsg(), mon, damage));
        }

        public void printCantSwap() {
            addString(String.format(NOT_ENOUGH_POKEMON));
        }

        public void printSwitchPrompt() {
            addString(String.format(SWITCH_PROMPT));
        }

        public void printSwapChoiceFainted(Pokémon mon) {
            addString(String.format(SWAP_CHOICE_IS_FAINTED, mon));
        }

        public void printNoSwapSelf(Pokémon mon) {
            addString(String.format(NO_SWITCH_SELF, mon));
        }

        public void printParalysisGranted(Pokémon mon) {
            addString(String.format(PARALYSIS_GRANTED, mon.getOwnerMsg(), mon));
        }

        public void printFailure() {
            addString(String.format(MOVE_FAILED));
        }

        public void printRecoilDamage(Pokémon mon, int damage) {
            addString(String.format(RECOIL_DAMAGE, mon.getOwnerMsg(), mon, damage));
        }

        public void printBurnGranted(Pokémon mon) {
            addString(String.format(BURN_GRANTED, mon.getOwnerMsg(), mon));
        }

        public void printPoisonGranted(Pokémon mon) {
            addString(String.format(POISON_GRANTED, mon.getOwnerMsg(), mon));
        }

        public void printBadPoisonGranted(Pokémon mon) {
            addString(String.format(BAD_POISON_GRANTED, mon.getOwnerMsg(), mon));
        }

        public void printSleepGranted(Pokémon mon) {
            addString(String.format(SLEEP_GRANTED, mon.getOwnerMsg(), mon));
        }

        public void printFlinch(Pokémon mon) {
            addString(String.format(MON_FLINCHED, mon.getOwnerMsg(), mon));
        }

        public void printConfusionGranted(Pokémon mon) {
            addString(String.format(CONFUSION_GRANTED, mon.getOwnerMsg(), mon));
        }

        public void printRestoreHP(Pokémon mon, int amt) {
            addString(String.format(HEALTH_RESTORED, mon.getOwnerMsg(), mon, amt));
        }

        public void printLightScreenActivate(Pokémon user) {
            String userMessage = user.getOwnerMsg().toLowerCase();
            if (userMessage.equals("the wild")) {
                userMessage = "the opposing";
            }
            addString(String.format(LIGHT_SCREEN_ACTIVATE, userMessage));
        }

        public void printReflectActivate(Pokémon user) {
            String userMessage = user.getOwnerMsg().toLowerCase();
            if (userMessage.equals("the wild")) {
                userMessage = "the opposing";
            }
            addString(String.format(REFLECT_ACTIVATE, userMessage));
        }

        public void printLightScreenOver(boolean player) {
            String userMessage = player ? "Your" : "The opposing";
            addString(String.format(LIGHT_SCREEN_OVER, userMessage));
        }

        public void printReflectOver(boolean player) {
            String userMessage = player ? "Your" : "The opposing";
            addString(String.format(REFLECT_OVER, userMessage));

        }

        public void printBound(Pokémon mon, String move) {
            addString(String.format(mon.getOwnerMsg(), mon, move));
        }

        public void printBindingDamage(Pokémon mon, BindingEffect effect) {
            String moveName = getBindingEffectName(effect);
            addString(String.format(BINDING_DAMAGE, mon.getOwnerMsg(), mon, moveName));
        }

        public void printBindEnd(Pokémon mon, BindingEffect effect) {
            String moveName = getBindingEffectName(effect);
            addString(String.format(BIND_END, mon.getOwnerMsg(), mon, moveName));
        }

        public void printMonBound(Pokémon mon, BindingEffect effect) {
            String moveName = getBindingEffectName(effect);
            addString(String.format(MON_BOUND, mon.getOwnerMsg(), mon, moveName));
        }

        public void printRemoveHazards(Pokémon mon) {
            addString(String.format(HAZARDS_REMOVED, mon.getOwnerMsg(), mon));
        }

        public void printSeeded(Pokémon mon) {
            addString(String.format(LEECH_SEEDED, mon.getOwnerMsg(), mon));
        }

        public void printLeechSeedActivate(Pokémon mon) {
            addString(String.format(LEECH_SEED_ACTIVATE, mon.getOwnerMsg(), mon));
        }

        public void printBuffer() {addString(String.format(BUFFER));}

        public void printMultipleHits(int timesHit) {
            String ending = (timesHit == 1) ? "" : "s";
            addString(String.format(MULTIPLE_HITS, timesHit, ending));

        }

        public void printWorrySeed(BattleSlot targetSlot) {
            addString(String.format(WORRY_SEED_ACTIVATE, targetSlot.getPokémon().getOwnerMsg(), targetSlot.getPokémon()));
        }

        public void printFeintActivate(BattleSlot targetSlot) {
            addString(String.format(FEINT_ACTIVATE, targetSlot.getPokémon().getOwnerMsg(), targetSlot.getPokémon()));
        }

        public void printAccCantDecrease(Pokémon pokémon) {
            addString(String.format(ACCURACY_CANT_DECREASE, pokémon.getOwnerMsg(), pokémon));
        }

        public void printFocusEnergy(BattleSlot slot) {
            addString(String.format(FOCUS_ENERGY_STRING, slot.getPokémon().getOwnerMsg(), slot.getPokémon()));
        }

        public void printNoRunFromTrainer() {
            JOptionPane.showMessageDialog(GameFrame.this, NO_RUN_FROM_TRAINER);
        }

        public void printFleeFail() {
            addString(FLEE_FAILED);
        }

        public void printRunFromBattle() {
            addString(RUN_FROM_BATTLE);
        }

        public void printTailwindActivate(BattleSlot slot) {
            String side = (slot.getPokémon().getOwner() == Owner.PLAYER) ? "your team" : "the opposing team";
            addString(String.format(TAILWIND_ACTIVATE, side));

        }

        public void printTailwindOver(boolean player) {
            String side = (player) ? "Your" : "The opponent's";
            addString(String.format(TAILWIND_END, side));
        }

        public void printMirrorMove(Move move) {
            addString(String.format(MIRROR_MOVE_ACTIVATE, move));
        }

        public void printNoPP() {
            JOptionPane.showMessageDialog(GameFrame.this, NO_PP_LEFT);
        }

        public void printUsedBallOnTrainer() {
            addString(String.format(CANNOT_CATCH_OWNED));
        }

        public void printItemUse(Item item) {
            String itemName = item.getName();
            Character firstLetter = itemName.charAt(0);
            String article = (vowels.contains(firstLetter)) ? "an" : "a";
            addString(String.format(ITEM_USED, article, itemName));
        }

        public void printHealItemUsed(Pokémon pokémon, int amt) {
            addString(String.format(HEALING_ITEM_USED, pokémon.getOwnerMsg(), pokémon, amt));
        }

        public void printCatchFail(int shakes) {
            String print;
            if (shakes == 0) {
                print = NO_SHAKES;
            } else if (shakes == 1) {
                print = ONE_SHAKE;
            } else if (shakes == 2) {
                print = TWO_SHAKES;
            } else {
                print = THREE_SHAKES;
            }
            addString(String.format(print));
        }

        public void printShedSkinActivate(Pokémon mon) {
            addString(String.format(SHED_SKIN_ACTIVATE, mon.getOwnerMsg(), mon));
        }

        public void printSafeguardActivate(Pokémon mon) {
            addString(String.format(SAFEGUARD_ACTIVATE, mon.getOwnerMsg(), mon));
        }

        public void printSafeguardEnd(boolean player) {
            String prefix = (player) ? "Your" : "The opponent's";
            addString(String.format(SAFEGUARD_END, prefix));
        }

        public void printToxicSpikes(Owner owner) {
            String pronoun = (owner == Owner.PLAYER) ? "your" : "the opponent's";
            addString(String.format(TOXIC_SPIKES_SET, pronoun));
        }

        public void printPoisonSpikesActivate(Pokémon mon) {
            addString(String.format(POISON_SPIKES_ACTIVATE, mon.getOwnerMsg()));
        }

        public void printRageBuilding(Pokémon mon) {
            addString(String.format(RAGE_BUILDING, mon.getOwnerMsg(), mon));
        }

        public void printCatchSuccess(Pokémon mon) {
            addString(String.format(CATCH_SUCCESS, mon));
        }

        public void printSentToPC(Pokémon mon) {
            addString(String.format(SENT_TO_PC, mon));
        }

        public void printEvade(Pokémon mon) {
            addString(String.format(EVADE_ATTACK, mon.getOwnerMsg(), mon));
        }

        public void printMultiTurnMoveEnd(Move move, Pokémon mon) {
            addString(String.format(MULTI_TURN_MOVE_END, mon.getOwnerMsg(), mon, move));
        }

        public void printTauntActivate(BattleSlot target) {
            addString(String.format(TAUNT_ACTIVATE, target.getPokémon().getOwnerMsg(), target.getPokémon()));
        }

        public void printTauntBlockedMove(BattleSlot slot, Move move) {
            addString(String.format(TAUNT_STOPS_MOVE, slot.getPokémon().getOwnerMsg(), slot.getPokémon(), move));
        }

        public void printTauntEnded(BattleSlot slot) {
            addString(String.format(TAUNT_ENDED, slot.getPokémon().getOwnerMsg(), slot.getPokémon()));
        }

        public void printEncoreStart(BattleSlot slot) {
            addString(String.format(ENCORE_START, slot.getPokémon().getOwnerMsg(), slot.getPokémon()));
        }

        public void printEncoreEnded(BattleSlot slot) {
            addString(String.format(ENCORE_END, slot.getPokémon().getOwnerMsg(), slot.getPokémon()));
        }

        public void printHazeEffect() {
            addString(String.format(HAZE_ACTIVATE));
        }

        public void printOneHitKO() {
            addString(String.format(ONE_HIT_KO));
        }
    }


    public class InputHelper {

        public static final int NICKNAME_CODE = 0;

        public final String[] DIALOG_OPTIONS = {"OK"};

        public final String[] areaOptions = {"Move", "Talk", "Story", "Search for Items", "Look for Pokémon", "Battle Trainers"};
        private final String[] MART_OPTIONS = {"Buy", "Sell", "Exit"};

        private static final String ALLY = "Ally";
        private static final String OPPONENT = "Opponent";


        public Boolean getYesOrNo(String message) {
            int response = JOptionPane.showConfirmDialog(GameFrame.this, message, "Yes or No", JOptionPane.YES_NO_OPTION);
            return response == JOptionPane.YES_OPTION;
        }

        public int getNumberInRange(int low, int high) {
            Scanner scanner =  new Scanner(System.in);
            int number = low - 1;
            Boolean validType = false;
            while (!validType || (number < low || number > high)) {
                System.out.printf("You must choose a number between %d and %d\n", low, high);
                try {
                    number = scanner.nextInt();
                    validType = true;
                } catch (InputMismatchException e) {

                } finally {
                    scanner.nextLine();
                }

            }
            scanner.close();
            return number;
        }

        public String getString(String title, String message) {
//            String response = JOptionPane.showInputDialog(GameFrame.this, message, title);
//            while (response.isEmpty()) {
//                JOptionPane.showMessageDialog(GameFrame.this,"You must input something.");
//                response = JOptionPane.showInputDialog(GameFrame.this, message, title);
//
//            }
//            return response;

            JPanel jPanel = new JPanel();
            JLabel label = new JLabel(message);
            JTextField textField = new JTextField(15);
            jPanel.add(label);
            jPanel.add(textField);

            while(true) {
                int selectedOption = JOptionPane.showOptionDialog(GameFrame.this, jPanel, title,
                        JOptionPane.NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, DIALOG_OPTIONS, DIALOG_OPTIONS[0]);
                if (selectedOption != 0) {
                    JOptionPane.showMessageDialog(GameFrame.this, "You must select an option.");
                } else {
                    String response = textField.getText();
                    if (response.isEmpty()) {
                        JOptionPane.showMessageDialog(GameFrame.this,"You must input something.");
                    } else {
                        return response;
                    }
                }
            }




        }

        public String getNickname(Pokémon mon) {
            String nickname = JOptionPane.showInputDialog(GameFrame.this, "What will you nickname " + mon + "?");
            if (nickname == null || nickname.equals("")) {
                return mon.getSpeciesName();
            } else {
                return nickname;
            }
        }

        public <T> T getInputFromOptions(T[] options, String title, String message) {
            JPanel jPanel = new JPanel();
            JLabel label = new JLabel(message);
            JComboBox<T> jComboBox = new JComboBox<>(options);
            jPanel.add(label);
            jPanel.add(jComboBox);

            while(true) {
                int selectedOption = JOptionPane.showOptionDialog(GameFrame.this, jPanel, title,
                        JOptionPane.NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, DIALOG_OPTIONS, DIALOG_OPTIONS[0]);
                if (selectedOption != 0) {
                    JOptionPane.showMessageDialog(GameFrame.this, "You must select an option.");
                } else {
                    return (T) jComboBox.getSelectedItem();
                }
            }
        }

        public <T> T getInputFromOptions(List<T> options, String title, String message) {
            T[] objectArray = (T[]) options.toArray();
            return getInputFromOptions(objectArray, title, message);

        }

        public <T> int getResponseIndexFromOptions(T[] options, String title, String message) {
            JPanel jPanel = new JPanel();
            JLabel label = new JLabel(message);
            JComboBox<T> jComboBox = new JComboBox<>(options);
            jPanel.add(label);
            jPanel.add(jComboBox);

            while(true) {
                int selectedOption = JOptionPane.showOptionDialog(GameFrame.this, jPanel, title,
                        JOptionPane.NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, DIALOG_OPTIONS, DIALOG_OPTIONS[0]);
                if (selectedOption != 0) {
                    JOptionPane.showMessageDialog(GameFrame.this, "You must select an option.");
                } else {
                    return jComboBox.getSelectedIndex();
                }
            }
        }

        public <T> int getResponseIndexFromOptions(List<T> options, String title, String message) {
            T[] objectArray = (T[]) options.toArray();
            return getResponseIndexFromOptions(objectArray, title, message);

        }




        public int getMoveToBeDeleted(List<Move> moves) {
//            int choice;
//            Object[] moveArray = moves.toArray();
//            while (true) {
//                choice = JOptionPane.showOptionDialog(GameFrame.this, "Which move should be deleted?",
//                        "Choose an Option", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, moveArray, moveArray[0]);
//                if (choice == -1) {
//                    JOptionPane.showMessageDialog(GameFrame.this,"You must select an option.");
//                } else {
//                    break;
//                }
//            }
//            return choice;

//            JPanel jPanel = new JPanel();
//            JLabel label = new JLabel("Which move should be deleted?");
//            //Move[] moveArray = moves.toArray(new Move[0]);
//            JComboBox<Move> jComboBox = new JComboBox<Move>(moves.toArray(new Move[0]););
//            jPanel.add(label);
//            jPanel.add(jComboBox);
//
//            while(true) {
//                int selectedOption = JOptionPane.showOptionDialog(GameFrame.this, jPanel, "Choose an option",
//                        JOptionPane.NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, DIALOG_OPTIONS, DIALOG_OPTIONS[0]);
//                if (selectedOption != 0) {
//                    JOptionPane.showMessageDialog(GameFrame.this, "You must select an option.");
//                } else {
//                    return String.valueOf(jComboBox.getSelectedItem());
//                }
//            }

            return getResponseIndexFromOptions(moves, "Choose an option", "Which move should be deleted?");

        }

        public Pokémon getPokémonToSwitchIn(Pokémon currentMon, ArrayList<Pokémon> pokémon) {
            String[] monStrings = new String[pokémon.size()];
            for (int i=0; i < pokémon.size(); i++) {
                String base = "%s (%d/%d)";
                Pokémon mon = pokémon.get(i);
                String stringified = String.format(base, mon, mon.getCurrentHP(), mon.getStats()[0]);
                monStrings[i] = stringified;
            }
            int choice;
            while (true) {
                choice = JOptionPane.showOptionDialog(GameFrame.this, "Who will replace " + currentMon + "?",
                        "Switch Pokémon", JOptionPane.DEFAULT_OPTION, 0, null, monStrings, monStrings[0]);
                if (choice == -1) {
                    JOptionPane.showMessageDialog(GameFrame.this, "You must select an option.");
                } else if (pokémon.get(choice).getStatus() == Status.FAINTED) {
                    JOptionPane.showMessageDialog(GameFrame.this, String.format("%s has no strength left to battle!", pokémon.get(choice)));
                } else if (pokémon.get(choice).equals(currentMon)) {
                    JOptionPane.showMessageDialog(GameFrame.this, currentMon + " is already in battle!");
                } else {
                    break;
                }
            }
            return pokémon.get(choice);
        }

        public int getMoveChoice(List<Move> options, Pokémon mon) {
            String[] moveStrings = new String[options.size()];
            for (int i=0; i < moveStrings.length; i++) {
                String base = "%s (%d/%d)";
                Move currentMove = options.get(i);
                String stringified = String.format(base, currentMove.getName(), currentMove.getCurrPP(), currentMove.getCurrMaxPP());
                moveStrings[i] = stringified;
            }
            int toReturn;
            while(true) {
                toReturn = JOptionPane.showOptionDialog(GameFrame.this,
                        "What move should " + mon + " use?", "Fight!",
                        JOptionPane.DEFAULT_OPTION, 0, null, moveStrings, moveStrings[0]);
                if (toReturn == -1) {
                    JOptionPane.showMessageDialog(GameFrame.this, "You must select an option.");
                } else if (!options.get(toReturn).hasPPLeft()) {
                    JOptionPane.showMessageDialog(GameFrame.this, "That move has no PP left!");
                } else {
                    break;
                }
            }
            return toReturn;

        }

        public int getBattleChoice(String[] options, Pokémon mon) {
            int toReturn;
            while (true) {
                toReturn = JOptionPane.showOptionDialog(GameFrame.this, "What will " + mon + " do?",
                        "Choose an Option", JOptionPane.DEFAULT_OPTION, 0, null, options,
                        options[0]);
                if (toReturn == -1) {
                    JOptionPane.showMessageDialog(GameFrame.this, "You must select an option.");

                } else {
                    break;
                }
            }

            return toReturn;
        }

        public String getBattleItem(Bag playerBag) {
            ArrayList<String> totalList = new ArrayList<>();
            totalList.addAll(playerBag.getHealingItems());
            totalList.addAll(playerBag.getBalls());
            totalList.addAll(playerBag.getBerries());
            String option = getInputFromOptions(totalList, "Bag", "Which item will you use?");
            return option.split(" ")[0];
        }

        public boolean shouldEvolve(Pokémon mon) {
            return getYesOrNo("Should " + mon + " evolve?");
        }

        public BattleSlot selectTarget(Side playerSide, Side compSide, BattleSlot userSlot, Move move) {
            ArrayList<String> targetStrings = new ArrayList<>();
            ArrayList<BattleSlot> targetSlots = new ArrayList<>();
            BattleSlot sisterSlot = playerSide.getSisterSlot(userSlot);
            if (sisterSlot != null && sisterSlot.getPokémon() != null) {
                Pokémon allyMon = sisterSlot.getPokémon();
                String allyString = ALLY + " " + allyMon + " (" + allyMon.getCurrentHP() + "/" + allyMon.getStats()[0] + ")";
                targetStrings.add(allyString);
                targetSlots.add(sisterSlot);
            }
            for (BattleSlot compSlot : compSide.getSlotsSafe()) {
                Pokémon oppMon = compSlot.getPokémon();
                String oppString = OPPONENT + " " + oppMon + " (" + oppMon.getCurrentHP() + "/" + oppMon.getStats()[0] + ")";
                targetStrings.add(oppString);
                targetSlots.add(compSlot);
            }
            int responseIndex = getResponseIndexFromOptions(targetStrings, "Targeting",
                    "Which Pokémon should be the target of " + move + "?");
            return targetSlots.get(responseIndex);
        }

        /**
         * Run the Poké Mart interface: buying and selling
         * @param player
         * @param availableItems
         */
        public void runMart(Player player, ArrayList<String> availableItems) {
            JPanel jPanel = new JPanel();
            JLabel label = new JLabel("Welcome! What can I do for you today?");
            JComboBox<String> jComboBox = new JComboBox<>(MART_OPTIONS);
            jPanel.add(label);
            jPanel.add(jComboBox);
            while (true) {
                int selectedOption = JOptionPane.showOptionDialog(GameFrame.this, jPanel, "Poké Mart",
                        JOptionPane.NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, DIALOG_OPTIONS, DIALOG_OPTIONS[0]);
                if (selectedOption != 0) {
                    JOptionPane.showMessageDialog(GameFrame.this, "You must select an option.");
                } else {
                    switch (String.valueOf(jComboBox.getSelectedItem())) {
                        case "Buy":
                            buyFromMart(player, availableItems);
                            break;
                        case "Sell":
                            sellToMart(player);
                            break;
                        case "Exit":
                            return;
                    }
                }
            }


        }

        //TODO
        private void buyFromMart(Player player, ArrayList<String> availableItems) {
        }

        //TODO
        private void sellToMart(Player player) {
        }
    }

    public class GamePrinter {

        private static final String PLAYER_NAME_PLACEHOLDER = "{{player}}";
        private static final String RIVAL_NAME_PLACEHOLDER = "{{rival}}";
        private static final String LEVEL_UP = "%s leveled up to level %d!";
        private static final String LEARN_MOVE_ATTEMPT = "%s wants to learn the move %s.\n But, %s already knows four moves." +
                "Should %s forget a move to learn %s?";
        private static final String FORGET_MOVE_PROMPT = "\"Which move should %s forget? Use the number representing its order in the list.";
        private static final String MOVE_FORGOTTEN = "%s forgot %s...";
        private static final String MOVE_LEARNED = "And learned %s!";
        private static final String NO_NEW_MOVE = "%s did not learn %s.";
        private static final String NICKNAME_PROMPT = "What nickname will you give %s?";
        private static final String NICKNAME_ASK = "Would you like to give a nickname to %s?";
        private static final String EXP_GAIN = "%s gained %d exp. points!";
        private static final String MOVE_AREAS = "%s left %s and entered %s.";
        private static final String NO_MORE_EVENTS = "There are no events to do in %s right now.";
        private static final String NO_MORE_TRAINERS = "There are no more trainers to battle in %s.";
        private static final String GET_MONEY = "You got $%d!";
        private static final String REQUIRES_EVENT = "You need to do more of the story to go that way.";
        private static final String EVENT_BLOCKED = "You can't do this event right now.";
        private static final String EVOLVE_START = "What? %s is evolving!";
        private static final String EVOLVE_FINISH = "Congratulations! Your %s evolved into %s!";
        private static final String EVOLVE_CANCELLED = "%s stopped evolving.";
        private static final String OBTAIN_ITEMS = "You got %d %s%s";
        private static final String SAVE_SUCCESS = "Save successful!";
        private static final String NOT_YET_IMPLEMENTED = "This feature is not yet implemented.";
        private static final String ITEM_FOUND = "You found %s %s!";
        private static final String CANT_GET_ITEM = "You see %s %s, but you can't get it without the move %s.";
        private static final String NO_MORE_ITEMS = "There are no more items to find in %s.";
        private static final String WHITE_OUT_RESULTS = "You scurry back to %s, and fully heal your party!";

        public void printLevel_UP(Pokémon mon) {
            addString(String.format(LEVEL_UP, mon, mon.getLevel()));
        }

        public void printEXPGain(Pokémon mon, int exp) {
            addString(String.format(EXP_GAIN, mon, exp));
        }

        public void printLearnMoveAttempt(Pokémon mon, Move move) {
            addString(String.format(LEARN_MOVE_ATTEMPT, mon, move, mon, mon, move));
        }

        public void printForgetMovePrompt(Pokémon mon) {
            addString(String.format(FORGET_MOVE_PROMPT, mon));
        }

        public void printMoveForgotten(Pokémon mon, Move oldMove) {
            addString(String.format(MOVE_FORGOTTEN, mon, oldMove));
        }

        public void printMoveLearned(Move newMove) {
            addString(String.format(MOVE_LEARNED, newMove));
        }

        public void printMoveOverwrite(Pokémon mon, Move oldMove, Move newMove) {
            printMoveForgotten(mon, oldMove);
            printMoveLearned(newMove);
        }

        public void printNoNewMove(Pokémon mon, Move move) {
            addString(String.format(NO_NEW_MOVE, mon, move));
        }

        public void printNicknamePrompt(Pokémon mon) {
            addString(String.format(NICKNAME_PROMPT, mon));
        }

        public void printNicknameAsk(Pokémon mon) {
            addString(String.format(NICKNAME_ASK, mon));
        }

        public void printAreaChange(String playerName, Area currentArea, Area newArea) {
            addString(String.format(MOVE_AREAS, playerName, currentArea.getName(), newArea.getName()));
        }

        public void printHealing() {
            addString("Your party was restored to full power!");
        }

        public void printNoMoreEvents(Area area) {
            String toShow = String.format(NO_MORE_EVENTS, area.getName());
            JOptionPane.showMessageDialog(GameFrame.this, toShow);
        }

        public void printNoMoreTrainers(Area area) {
            String toShow = String.format(NO_MORE_TRAINERS, area.getName());
            JOptionPane.showMessageDialog(GameFrame.this, toShow);

        }

        public void printEventText(String string, String playerName, String rivalName) {
            String fixedString = string.replace(PLAYER_NAME_PLACEHOLDER, playerName).replace(RIVAL_NAME_PLACEHOLDER, rivalName);
            addString(fixedString);
        }

        public void printEventText(List<String> stringList, String playerName, String rivalName) {
            for (String string : stringList) {
                String fixedString = string.replace(PLAYER_NAME_PLACEHOLDER, playerName).replace(RIVAL_NAME_PLACEHOLDER, rivalName);
                addString(fixedString);
            }
        }


        public void printMovementBlocked(MoveRequirement moveRequirement) {
            switch (moveRequirement) {
                case EVENT:
                    JOptionPane.showMessageDialog(GameFrame.this, REQUIRES_EVENT);
                    break;
                default:
                    JOptionPane.showMessageDialog(GameFrame.this, "I haven't coded this requirement yet. Just don't go here.");
                    break;
            }
        }

        public void printFileNotFound() {
            JOptionPane.showMessageDialog(GameFrame.this, "File was not found! Quitting...");
        }


        public void printUnknownException() {
            JOptionPane.showMessageDialog(GameFrame.this, "An unknown error has occurred. Quiting now...");
        }

        public void printEventBlocked() {
            String toShow = String.format(EVENT_BLOCKED);
            JOptionPane.showMessageDialog(GameFrame.this, toShow);
        }

        public void printLine(String talkString) {
            addString(talkString);
        }

        public void printEvolveStart(Pokémon mon) {
            addString(String.format(EVOLVE_START, mon));
        }

        public void printEvolveFinish(String oldName, Pokémon mon) {
            addString(String.format(EVOLVE_FINISH, oldName, mon.getSpeciesName()));
        }

        public void printEvolutionCancelled(Pokémon mon) {
            addString(String.format(EVOLVE_CANCELLED, mon));
        }

        public void printObtainItems(Item item, int quantity) {
            String suffix = (quantity > 1) ? "s" : "";
            addString(String.format(OBTAIN_ITEMS, quantity, item, suffix));
        }

        public void printSaveSuccess() {
            addString(SAVE_SUCCESS);
        }

        public void printNotYetImplemented() {
            addString(NOT_YET_IMPLEMENTED);
        }

        public void printItemFound(Item item) {
            String itemName = item.getName();
            Character firstLetter = itemName.charAt(0);
            String article = (vowels.contains(firstLetter)) ? "an" : "a";
            addString(String.format(ITEM_FOUND, article, itemName));
        }

        public void printCantGetItem(Item item, MoveRequirement moveRequirement) {
            String itemName = item.getName();
            Character firstLetter = itemName.charAt(0);
            String article = (vowels.contains(firstLetter)) ? "an" : "a";
            String moveName = moveRequirement.toString();
            addString(String.format(CANT_GET_ITEM, article, itemName, moveName));
        }

        public void printNoMoreItems(Area area) {
            addString(String.format(NO_MORE_ITEMS, area.getName()));
        }

        public void printWhiteOutResults(Area area) {
            addString(String.format(WHITE_OUT_RESULTS, area.getName()));
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        GameFrame gf = new GameFrame(false);
        while(true) {
            String string = scanner.nextLine();
            gf.addString(string);
        }
    }

}

