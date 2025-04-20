package game;

import areas.Area;
import areas.ItemBall;
import areas.WildSlot;
import battle.Battle;
import enums.MoveRequirement;
import enums.Owner;
import enums.Species;
import enums.Status;
import events.*;
import items.Item;
import moves.Move;
import pokémon.Pokémon;
import pokémon.PokémonFactory;
import trainer.Trainer;
import ui.GameFrame;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class Game {

    private final static String NAME_INPUT_TITLE = "Name Entry";
    private final static String NAME_INPUT_MESSAGE = "What is your name?";
    private final static String RIVAL_INPUT_TITLE = "Rival Name";
    private final static String RIVAL_INPUT_MESSAGE = "What is your rival's name?";
    private final static String GET_PLAYER_NAME = "{{getName}}";
    private final static String GET_RIVAL_NAME = "{{getRivalName}}";

    private static final int TALL_GRASS = 0;
    private static final int SURFING = 1;
    private static final int FISHING = 2;
    private static final String BACK_AREA_OPTION = "Back";
    private static final String SAVE_AREA_OPTION = "Save";
    private static final int MAX_PARTY_SIZE = 6;

    private final static String HAS_DEX_FLAG = "DEXOWNED";  

    private static final String TALL_GRASS_OPTION = "Search for wild Pokémon (Tall Grass)";
    private static final String SURF_OPTION = "Search for wild Pokémon (Surfing)";
    private static final String FISHING_OPTION = "Search for wild Pokémon (Fishing)";
    private static final String MOVE_OPTION = "Move";
    private static final String ITEMS_OPTION = "Search for Items";
    private static final String TRAINERS_OPTION = "Battle Trainers";
    private static final String HEALING_OPTION = "Heal";
    private static final String PC_OPTION = "Use the PC";
    private static final String MART_OPTION = "Go Shopping";
    private static final String TALKING_OPTION = "Talk";
    private static final String STORY_OPTION = "Progress the Story";
    private static final String EXIT_OPTION = "Exit Game";
    private static final String POKEDEX_OPTION = "View the Pokédex";
    private static final String AREA_CHOICE_PROMPT = "What would you like to do?";
    private static final ArrayList<String> noBadgeItems = new ArrayList<>(Arrays.asList("Antidote", "ParalyzeHeal", "Potion", "Poké Ball"));
    private static final ArrayList<ArrayList<String>> martStock = new ArrayList<>(Arrays.asList(noBadgeItems));

    private final GameFrame gameFrame;

    private String saveFile;

    private Player player;
    private Area currentArea;
    private Area lastHealingArea;
    private List<Area> areaList;
    private Map<String, Area> worldMap;
    private PokémonFactory factory;
    private Set<Area> flyOptions;
    private Set<String> eventFlags;
    private int starterChoice = -1;
    private List<String> prologue;

    public Game(GameFrame gameFrame, List<Area> areaList, Player player, List<String> prologue, String startingArea) {
        this.gameFrame = gameFrame;
        factory = new PokémonFactory(gameFrame.getInputHelper(), gameFrame.getGamePrinter());
        this.areaList = areaList;
        makeWorldMap();
        flyOptions = new HashSet<>();
        saveFile = "";
        this.player = player;
        eventFlags  = new HashSet<>(Collections.singleton(""));
        this.prologue = prologue;
        currentArea = areaList.stream().filter(area -> area.getName().equals(startingArea)).findFirst().orElse(areaList.get(0));
    }

    public Game(GameFrame gameFrame, List<Area> areaList, Player player, List<String> prologue, String startingArea, String saveFile) {
        this.gameFrame = gameFrame;
        factory = new PokémonFactory(gameFrame.getInputHelper(), gameFrame.getGamePrinter());
        this.areaList = areaList;
        makeWorldMap();
        flyOptions = new HashSet<>();
        this.player = player;
        eventFlags  = new HashSet<>(Collections.singleton(""));
        this.prologue = prologue;
        currentArea = areaList.stream().filter(area -> area.getName().equals(startingArea)).findFirst().orElse(areaList.get(0));
        this.saveFile = saveFile;
    }

    private void makeWorldMap() {
        worldMap = new HashMap<>();
        for (Area area : areaList) {
            worldMap.put(area.getName(), area);
        }
    };

    /**
     * Checks to see if the player has a a Pokémon that knows a specific move for HM move
     * requirements
     * @param neededMove The Class of the Move needed
     * @return The first Pokémon in the user's party that knows the move, or
     * null if no such Pokémon exists
     */
    private Pokémon partyKnowsMove(Class<? extends Move> neededMove) {
        for (Pokémon mon : player.getParty()) {
            for (Move move : mon.getMoveSet()) {
                if (move.getClass().equals(neededMove)) {
                    return mon;
                }
            }
        }
        return null;
    }

    private void getAreaAction() {
        ArrayList<String> areaOptions = new ArrayList<>();
        areaOptions.add(MOVE_OPTION);
        if (eventFlags.contains(HAS_DEX_FLAG)) {
            areaOptions.add(POKEDEX_OPTION);
        }
        if (currentArea.getTrainers() != null && currentArea.getTrainers().size() > 0) {
            areaOptions.add(TRAINERS_OPTION);
        }
        if (currentArea.getTallGrassSlots() != null && currentArea.getTallGrassSlots().size() > 0) {
            areaOptions.add(TALL_GRASS_OPTION);
        }
        if (currentArea.getSurfSlots() != null && currentArea.getSurfSlots().size() > 0) {
            areaOptions.add(SURF_OPTION);
        }
        if (currentArea.getFishingSlots() != null && currentArea.getFishingSlots().size() > 0) {
            areaOptions.add(FISHING_OPTION);
        }
        if (currentArea.getItems() != null) {
            areaOptions.add(ITEMS_OPTION);
        }
        if (currentArea.isHealingSpot()) {
            areaOptions.add(HEALING_OPTION);
        }
        if (player.getParty().size() > 0) {
            areaOptions.add(PC_OPTION);
        }
        if (currentArea.hasMart()) {
            areaOptions.add(MART_OPTION);
        }
        if (currentArea.getEvents() != null) {
            areaOptions.add(STORY_OPTION);
        }
        if (currentArea.getTownsPeople() != null) {
            areaOptions.add(TALKING_OPTION);
        }
        areaOptions.add(SAVE_AREA_OPTION);
        areaOptions.add(EXIT_OPTION);
        String choice = gameFrame.getInputHelper().getInputFromOptions(areaOptions, currentArea.getName(), AREA_CHOICE_PROMPT);
        switch (choice) {
            case TRAINERS_OPTION:
                battleTrainer();
                break;
            case TALL_GRASS_OPTION:
                wildEncounter(TALL_GRASS);
                break;
            case SURF_OPTION:
                wildEncounter(SURFING);
                break;
            case FISHING_OPTION:
                wildEncounter(FISHING);
                break;
            case ITEMS_OPTION:
                searchForItems();
                break;
            case HEALING_OPTION:
                gameFrame.getGamePrinter().printHealing();
                for (Pokémon mon : player.getParty()) {
                    if (mon == null) {
                        break;
                    }
                    mon.fullyHeal();
                }
                break;
            case MART_OPTION:
                goToMart();
                break;
            case STORY_OPTION:
                processNextEvent();
                break;
            case TALKING_OPTION:
                talkToPeople();
                break;
            case EXIT_OPTION:
                System.exit(0);
                break;
            case MOVE_OPTION:
                switchAreas();
                break;
            case SAVE_AREA_OPTION:
                saveGame();
                break;
            case POKEDEX_OPTION:
                viewPokédex();
                break;
            default:
                gameFrame.getGamePrinter().printNotYetImplemented();
                break;
        }
    }

    private void viewPokédex() {
        gameFrame.getGamePrinter().printNotYetImplemented();
    }

    private void searchForItems() {
        List<ItemBall> itemBalls = currentArea.getItems();
        if (itemBalls.isEmpty()) {
            gameFrame.getGamePrinter().printNoMoreItems(currentArea);
            return;
        }
        ItemBall next = itemBalls.get(0);
        if (meetsMoveRequirement(next.getObtainRequirement())) {
            gameFrame.getGamePrinter().printItemFound(next.getItem());
            addItem(next.getItem(), 1);
            currentArea.getItems().remove(0);
        } else {
            gameFrame.getGamePrinter().printCantGetItem(next.getItem(), next.getObtainRequirement());
            //If we can't get item now, add it to the back of the list
            currentArea.getItems().remove(0);
            currentArea.getItems().add(next);
        }
    }

    private boolean meetsMoveRequirement(MoveRequirement moveRequirement) {
        switch(moveRequirement) {
            case NONE:
                return true;
            default:
                return false;
        }
    }

    /**
     * Runs the Poké Mart sequence
     */
    private void goToMart() {
        ArrayList<String> availableItems = getAvailableMartItems();
        gameFrame.getInputHelper().runMart(player, availableItems);
    }

    /**
     * Which items a player can buy is determined by the number of badges they hold
     * @return A list of all items (as represented by strings) that the player can currently buy
     */
    private ArrayList<String> getAvailableMartItems() {
        ArrayList<String> toReturn = new ArrayList<>();
        for (int i = 0; i <= player.getBadgeCount(); i++) {
            toReturn.addAll(martStock.get(i));
        }
        return toReturn;
    }

    private void talkToPeople() {
        gameFrame.getGamePrinter().printLine("NPC: \"" + currentArea.getTalkString() + "\"");
    }

    private void battleTrainer() {
        if (currentArea.getTrainers().isEmpty()) {
            gameFrame.getGamePrinter().printNoMoreTrainers(currentArea);
            return;
        }
        Trainer trainer = currentArea.getTrainers().get(0);
        startBattle(trainer);
        if (playerWhitedOut()) {
            whiteOut();
        } else {
            currentArea.trainerDefeated();
        }
    }

    private void processNextEvent() {
        if (currentArea.getEvents().isEmpty()) {
            gameFrame.getGamePrinter().printNoMoreEvents(currentArea);
            return;
        }
        GameEvent event = currentArea.getEvents().get(0);

        if (event.getCanStartFlag() != null && !eventFlags.contains(event.getCanStartFlag())) {
            gameFrame.getGamePrinter().printEventBlocked();
            return;
        }
        while(event.isRunning()) {
            SubEvent subEvent = event.next();
            if (passSubEvent(subEvent)) {
                gameFrame.getGamePrinter().printEventText(subEvent.getAfterMain(), player.getPlayerName(), player.getRivalName());
                if(event.subEventPassed()) {
                    setFlags(event);
                    //TODO: how can i implement repeatable events?
                    currentArea.getEvents().remove(0);
                }
            } else if (event.ignoreFail()) {
                setFlags(event);
                currentArea.getEvents().remove(0);
                break;
            } else {
                if (event.resetOnFail()) {
                    event.reset();
                }
                break;
            }
        }
    }

    /**
     * Sometimes, passing an event lifts restrictions on movement somewhere in the map. This lets that happen
     * @param event
     */
    private void setFlags(GameEvent event) {
        List<MovementFlag> movementFlags = event.getMovementFlagsToLift();
        for (MovementFlag flag : movementFlags) {
            String fromArea = flag.getAreaOne();
            String toArea = flag.getAreaTwo();
            Area toLoosen = worldMap.get(fromArea);
            Area newConnection = worldMap.get(toArea);
            toLoosen.getMovePermissions().put(newConnection, MoveRequirement.NONE);
            newConnection.getMovePermissions().put(toLoosen, MoveRequirement.NONE);
        }
        eventFlags.addAll(event.getEventFlagsToLift());
    }

    /**
     * Process the different types of SubEvents
     * @param subEvent The SubEvent to play
     * @return true if the player passed the event, false otherwise
     */
    private boolean passSubEvent(SubEvent subEvent) {
        gameFrame.getGamePrinter().printEventText(subEvent.getBeforeMain(), player.getPlayerName(), player.getRivalName());

        if (subEvent instanceof ItemEvent) {
            return processItemEvent((ItemEvent) subEvent);
        } else if (subEvent instanceof PokémonEvent) {
            return processPokémonEvent((PokémonEvent) subEvent);
        } else if (subEvent instanceof PokémonChoiceEvent) {
            return processPokémonChoiceEvent((PokémonChoiceEvent) subEvent);
        } else if (subEvent instanceof RivalBattleEvent) {
            return processRivalBattleEvent((RivalBattleEvent) subEvent);
        } else if (subEvent instanceof TrainerEvent) {
            return processTrainerEvent((TrainerEvent) subEvent);
        } else {
            return processWildEvent((WildEvent) subEvent);
        }
    }

    private boolean processItemEvent(ItemEvent subEvent) {
        Item item = subEvent.getItem();
        if (item == null) {
            return true;
        }
        int quantity = subEvent.getQuantity();
        gameFrame.getGamePrinter().printObtainItems(item, quantity);
        addItem(item, quantity);
        return true;
    }

    private void addItem(Item item, int quantity) {
        player.getBag().addItem(item, quantity);
    }

    private boolean processPokémonEvent(PokémonEvent subEvent) {
        Species species = subEvent.getMon();
        Pokémon newMon = factory.makePokémon(species, subEvent.getLevel(), Owner.PLAYER);
        if (player.getParty().size() == MAX_PARTY_SIZE) { //TODO: THIS
            System.err.println("UH PLEASE IMPLEMENT PARTY LIMITS!!!");
            System.exit(0);
        }
        newMon.onCatch();
        player.getParty().add(newMon);
        return true;
    }

    private boolean processPokémonChoiceEvent(PokémonChoiceEvent subEvent) {
        List<String> choices = subEvent.getOptions();
        String playerChoice = gameFrame.getInputHelper().getInputFromOptions(choices, "Who will you choose?", subEvent.getPrompt());
        int position = choices.indexOf(playerChoice);
        Species species = subEvent.getSpecies().get(position);
        if (starterChoice == -1) {
            starterChoice = position;
        }
        Pokémon newMon = factory.makePokémon(species, subEvent.getLevel(), Owner.PLAYER);
        if (player.getParty().size() == MAX_PARTY_SIZE) { //TODO: THIS
            System.err.println("UH PLEASE IMPLEMENT PARTY LIMITS!!!");
            System.exit(0);
        }
        newMon.onCatch();
        player.getParty().add(newMon);
        return true;

    }

    private boolean processRivalBattleEvent(RivalBattleEvent subEvent) {
        startBattle(subEvent.getRival(starterChoice, player.getRivalName()));
        if (playerWhitedOut()) {
            whiteOut();
            return false;
        } else {
            return true;
        }
    }

    private boolean processTrainerEvent(TrainerEvent subEvent) {
        Trainer trainer = subEvent.getTrainer();
        startBattle(trainer);
        if (playerWhitedOut()) {
            whiteOut();
            return false;
        } else {
            return true;
        }
    }

    private boolean processWildEvent(WildEvent subEvent) {
        Pokémon mon = factory.makePokémon(subEvent.getMon(), subEvent.getLevel(), Owner.WILD);
        mon.setItem(subEvent.getItem());
        startBattle(mon);
        if (playerWhitedOut()) {
            whiteOut();
            return false;
        } else {
            return true;
        }
    }

    private void switchAreas() {
        List<String> switchable = new ArrayList<>();
        gameFrame.addString("You can move to the following areas: " + currentArea.getConnections());
        for (Area area : currentArea.getConnections()) {
            switchable.add(area.getName());
        }
        switchable.add(BACK_AREA_OPTION);
        String movementChoice = null;
        while (true) {
            movementChoice = gameFrame.getInputHelper().getInputFromOptions(switchable, "Move", "Where would you like to move to?");
            if (movementChoice.equals(BACK_AREA_OPTION)) {
                break;
            } else {
                Area toSwitch = null;
                for (Area other : currentArea.getConnections()) {
                    if (other.getName().equals(movementChoice)) {
                        toSwitch = other;
                    }
                }
                if(canMove(currentArea.getMovePermissions().get(toSwitch))) {
                    gameFrame.getGamePrinter().printAreaChange(player.getPlayerName(), currentArea, toSwitch);
                    currentArea = toSwitch;
                    if (currentArea.isHealingSpot()) {
                        lastHealingArea = currentArea;
                    }
                    if (currentArea.isFlyable()) {
                        flyOptions.add(currentArea);
                    }
                    return;
                } else {
                    gameFrame.getGamePrinter().printMovementBlocked(currentArea.getMovePermissions().get(toSwitch));
                }
            }
        }
    }

    /**
     * Determines if the player can move, given a specific moveRequirement
     * @param moveRequirement the MoveRequirement for the requested area
     * @return true if the player can pass the MoveRequirement, false otherwise
     */
    //TODO: implement things that aren't none
    private boolean canMove(MoveRequirement moveRequirement) {
        switch (moveRequirement) {
            case NONE:
                return true;
            case SURF:
                return false;
            case STRENGTH:
                return false;
            case ROCK_SMASH:
                return false;
            case EVENT:
                return false;
            default:
                return false;
        }
    }

    /**
     * Generate a wild Pokémon encounter
     */
    private void wildEncounter(int type) {
        TreeMap<Integer, WildSlot> slots;
        if (type == TALL_GRASS) {
            slots = currentArea.getTallGrassSlots();
        } else if (type == SURFING) {
            slots = currentArea.getSurfSlots();
        } else {
            slots = currentArea.getFishingSlots();
        }
        int random = ThreadLocalRandom.current().nextInt(0, 101);
        WildSlot slot = null;
        int availableSlots = slots.size();
        int i = 0;
        for (int x : slots.keySet()) {
            if (random < x) {
                slot = slots.get(x);
                break;
            }
            i++;
            if (i == availableSlots) {
                slot = slots.get(x);
                break;
            }
        }
        if (slot == null) {
            slot = slots.get(slots.firstKey());
        }
        Pokémon wild = getMonFromWildSlot(slot);
        startBattle(wild);
    }

    /**
     * Generate a new wild Pokémon for the player to battle
     * @param slot The WildSlot for the Pokémon to be generated
     * @return The wild Pokémon
     */
    private Pokémon getMonFromWildSlot(WildSlot slot) {
        Species species = slot.getSpecies();
        int level = ThreadLocalRandom.current().nextInt(slot.getMinLevel(), slot.getMaxLevel()+1);
        int itemChance = ThreadLocalRandom.current().nextInt(0, 101);
        Pokémon toReturn = factory.makePokémon(species, level, Owner.WILD);
        if (slot.getItemChance() > itemChance) {
            toReturn.setItem(slot.getItem());
        }
        return toReturn;
    }

    private void startBattle(Pokémon wildMon) {
        new Battle(player, wildMon, currentArea.getWeather(), gameFrame).battle();
        checkForEvolution();
    }

    private void startBattle(Trainer trainer) {
        new Battle(player, trainer, currentArea.getWeather(), gameFrame).battle();
        checkForEvolution();
    }

    private void checkForEvolution() {

        for (int i = 0; i < player.getParty().size(); i++) {
            Pokémon mon = player.getParty().get(i);
            if (mon.getEvolveTrigger()) {
                gameFrame.getGamePrinter().printEvolveStart(mon);
                if (gameFrame.getInputHelper().shouldEvolve(mon)) {
                    String oldName = mon.toString();
                    player.getParty().set(i, mon.evolve());
                    gameFrame.getGamePrinter().printEvolveFinish(oldName, player.getParty().get(i));
                } else {
                    gameFrame.getGamePrinter().printEvolutionCancelled(mon);
                    mon.setEvolveTrigger(false);
                }
            }
        }
    }

    //TODO: implement
    private void whiteOut() {
    }

    /**
     * Simply checks to see if the player's Pokémon are all fainted
     * @return true if the player has no more Pokémon, false otherwise
     */
    private boolean playerWhitedOut() {
        for (Pokémon mon : player.getParty()) {
            if (mon == null) {
                return true;
            }
            if (mon.getStatus() != Status.FAINTED) {
                return false;
            }
        }
        return true;
    }


    public void start() {
        run();
    }


    public void startNew() {
        //eventually have something here different when we run a real new game; this is just for testing
        lastHealingArea = currentArea;
        flyOptions.add(currentArea);
        runPrologue();
        run();
    }

    private void runPrologue() {
        String playerName = "";
        String rivalName = "";
        for (String line : prologue) {
            if (line.contains(GET_PLAYER_NAME)) {
                playerName = gameFrame.getInputHelper().getString(NAME_INPUT_TITLE, NAME_INPUT_MESSAGE);
            } else if (line.contains(GET_RIVAL_NAME)) {
                rivalName = gameFrame.getInputHelper().getString(RIVAL_INPUT_TITLE, RIVAL_INPUT_MESSAGE);
                player = new Player(playerName, rivalName);
            } else {
                gameFrame.getGamePrinter().printEventText(line, playerName, rivalName);
            }
        }
    }

    private void run() {
        while(true) {
            getAreaAction();
        }
    }

    private void saveGame() {
        if (saveFile.equals("")) {
            saveFile = gameFrame.getInputHelper().getString("System", "Please give a name to your save file");
        }
        GameSaver.save(this, saveFile);
        gameFrame.getGamePrinter().printSaveSuccess();
    }

    public Player getPlayer() {
        return player;
    }

    public String getSaveFile() {
        return saveFile;
    }

    public Area getCurrentArea() {
        return currentArea;
    }

    public Area getLastHealingArea() {
        return lastHealingArea;
    }

    public List<Area> getAreaList() {
        return areaList;
    }

    public Map<String, Area> getWorldMap() {
        return worldMap;
    }

    public Set<Area> getFlyOptions() {
        return flyOptions;
    }
}
