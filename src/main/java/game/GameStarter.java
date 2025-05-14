package game;

import areas.Area;
import battle.Battle;
import enums.Owner;
import enums.Species;
import enums.Weather;
import exceptions.BadNameException;
import moves.*;
import pokémon.Pokémon;
import pokémon.PokémonFactory;
import trainer.PartySlot;
import trainer.Trainer;
import ui.GameFrame;
import game.Region;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class GameStarter {

    private final static boolean DEBUG_MODE = false;
    private final static boolean JAR_MODE = false;
    private final static String KANTO_MAP = "data/Kanto.region";
    private final static String KANTO_NEW_GAME = "data/Kanto.newgame";

    private final static String KANTO = "Kanto";
    private final static ArrayList<String> REGION_OPTIONS = new ArrayList<>(Arrays.asList(KANTO));
    private static final String SAVE_DIR = System.getProperty("user.home") + "/.pokemon-text-version/saves/";

    private final static String BATTLE_MODE = "Quick Battle";
    private final static String ADVENTURE_MODE = "Adventure Mode";
    private final static String LOAD_MODE = "Load Game";
    private final static String NEW_GAME_MODE = "New Game";
    private final static ArrayList<String> GAME_MODE_OPTIONS = new ArrayList<String>(Arrays.asList(NEW_GAME_MODE, BATTLE_MODE));


    private GameFrame gameFrame;


    public GameStarter() {
        gameFrame = GameFrame.getInstance(DEBUG_MODE);
    }

    public void startGame() {

        List<String> saves = findSaves();
        if (!saves.isEmpty()) {
            GAME_MODE_OPTIONS.add(0, LOAD_MODE);
        }
        String gameModeChoice = gameFrame.getInputHelper().getInputFromOptions(GAME_MODE_OPTIONS, "Game Mode Selection",
                "Please select which game mode you would like to play:");

        if (gameModeChoice.equals(BATTLE_MODE)) {
            startBattleMode();
        } else if (gameModeChoice.equals(NEW_GAME_MODE)) {
            startAdventureMode(true);
        } else if (gameModeChoice.equals(LOAD_MODE)) {
            startAdventureMode(false);
        }
    }

    private void startBattleMode() {
        Player ari = new Player("Ari", "Evil Ari");
        PokémonFactory factory = new PokémonFactory();
        Pokémon myVenusaur = factory.makePokémon(Species.VENUSAUR, 50, Owner.PLAYER);
        Pokémon myCharizard = factory.makePokémon(Species.CHARIZARD, 50, Owner.PLAYER);
        Pokémon myBlastoise = factory.makePokémon(Species.BLASTOISE, 50, Owner.PLAYER);
        PartySlot compVenusaur = new PartySlot(Species.VENUSAUR, 50);
        PartySlot compCharizard = new PartySlot(Species.CHARIZARD, 50);
        PartySlot compBlastoise = new PartySlot(Species.BLASTOISE, 50);
        ArrayList<Move> myBlastoiseMoves = new ArrayList<>(Arrays.asList(new HydroPump(), new Protect(), new SkullBash(),
                new RainDance()));
        ArrayList<Move> myCharizardMoves = new ArrayList<>(Arrays.asList(new Flamethrower(), new Slash(), new DragonRage(),
                new Fly()));
        ArrayList<Move> myVenusaurMoves = new ArrayList<>(Arrays.asList(new PetalDance(), new DoubleEdge(), new SleepPowder(),
                new LeechSeed()));
        ArrayList<Move> compBlastoiseMoves = new ArrayList<>(Arrays.asList(new HydroPump(), new Protect(), new SkullBash(),
                new RainDance()));
        ArrayList<Move> compCharizardMoves = new ArrayList<>(Arrays.asList(new Flamethrower(), new Slash(), new DragonRage(),
                new Fly()));
        ArrayList<Move> compVenusaurMoves = new ArrayList<>(Arrays.asList(new PetalDance(), new DoubleEdge(), new SleepPowder(),
                new LeechSeed()));
        myBlastoise.setMoveSet(myBlastoiseMoves);
        compBlastoise.setMoveSet(compBlastoiseMoves);
        myCharizard.setMoveSet(myCharizardMoves);
        compCharizard.setMoveSet(compCharizardMoves);
        myVenusaur.setMoveSet(myVenusaurMoves);
        compVenusaur.setMoveSet(compVenusaurMoves);
        Pokémon[] temp = {myBlastoise, myCharizard, myVenusaur};
        ArrayList<Pokémon> myParty = new ArrayList<>(Arrays.asList(temp));
        ari.setParty(myParty);
        ArrayList<PartySlot> compParty = new ArrayList<>(Arrays.asList(compCharizard, compVenusaur, compBlastoise));
        Trainer testTrainer = new Trainer("Tester", "Joe", "Ready for a battle?","Lookin' good, Ari!",
                "The circle is now complete.", 1, false, compParty);
        new Battle(ari, testTrainer, Weather.NONE).battle();
    }

    private void startAdventureMode(boolean newGame) {
        if (newGame) {
            startKanto(true, KANTO_NEW_GAME);
        } else {
            List<String> saves = findSaves();
            String saveChoice = gameFrame.getInputHelper().getInputFromOptions(saves, "Game Selection",
                    "Please select which save you would like to load:");
            startKanto(false, saveChoice);
        }
    }

    private List<String> findSaves() {
        File savesDir = new File(SAVE_DIR);
        if (!savesDir.exists()) {
            savesDir.mkdirs();
            return new ArrayList<>();
        }
        
        File[] matchingFiles = savesDir.listFiles((dir, name) -> name.endsWith(".ptvsav"));
        
        if (matchingFiles == null || matchingFiles.length == 0) {
            return new ArrayList<>();
        }
        
        return Arrays.stream(matchingFiles)
                    .map(File::getName)
                    .collect(Collectors.toList());
    }

    private void startKanto(boolean newgame, String saveName) {
        String savePath = "";
        if (newgame) {
            savePath = saveName;
        } else {
            savePath = saveName;
        }
        try {
            ArrayList<Area> gameMap = Region.createRegion(KANTO_MAP, JAR_MODE);
            Game game = GameInflater.inflateRegion(gameMap, savePath, newgame, JAR_MODE);
            if (newgame) {
                game.startNew();
            } else {
                game.start();
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
            gameFrame.getGamePrinter().printFileNotFound();
            System.exit(0);
        } catch (BadNameException e) {
            gameFrame.getGamePrinter().printUnknownException();
            System.err.println("Bad name: " + e.getBadName());
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new GameStarter().startGame();
    }

}
