package game;

import areas.Area;
import battle.BattleRedux;
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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class GameStarter {

    private final static boolean DEBUG_MODE = true;
    private final static boolean JAR_MODE = false;
    private final static String KANTO_MAP = "Kanto.region";
    private final static String KANTO_NEW_GAME = "data/Kanto.newgame";

    private final static String KANTO = "Kanto";
    private final static ArrayList<String> REGION_OPTIONS = new ArrayList<>(Arrays.asList(KANTO));

    private final static String BATTLE_MODE = "Quick Battle";
    private final static String ADVENTURE_MODE = "Adventure Mode";
    private final static ArrayList<String> GAME_MODE_OPTIONS = new ArrayList<String>(Arrays.asList(ADVENTURE_MODE, BATTLE_MODE));


    private GameFrame gameFrame;


    public GameStarter() {
        gameFrame = new GameFrame(DEBUG_MODE);

    }

    public void startGame() {

        String gameModeChoice = gameFrame.getInputHelper().getInputFromOptions(GAME_MODE_OPTIONS, "Game Mode Selection",
                "Please select which game mode you would like to play:");

        if (gameModeChoice.equals(BATTLE_MODE)) {
            startBattleMode();
        } else if (gameModeChoice.equals(ADVENTURE_MODE)) {
            startAdventureMode();
        }
    }

    private void startBattleMode() {
        Player ari = new Player("Ari", "Evil Ari");
        PokémonFactory factory = new PokémonFactory(gameFrame.getInputHelper(), gameFrame.getGamePrinter());
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
        new BattleRedux(ari, testTrainer, Weather.NONE, gameFrame).battle();
    }

    private void startAdventureMode() {
        List<String> saves = findSaves();
        if (saves.isEmpty()) {
            String gameChoice = gameFrame.getInputHelper().getInputFromOptions(REGION_OPTIONS, "Game Selection",
                    "Please select which region you would like to play:");
            if (gameChoice.equals(KANTO)) {
                startKanto(true, KANTO_NEW_GAME);
            }
        } else {
            String saveChoice = gameFrame.getInputHelper().getInputFromOptions(saves, "Game Selection",
                    "Please select which save you would like to load:");
            startKanto(false, saveChoice);
        }
    }

    private List<String> findSaves() {
        File f = new File("./bin");
        File[] matchingFiles = f.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.endsWith(".ptvsav");
            }
        });
        return Arrays.asList(matchingFiles).stream().map(File::getName).collect(Collectors.toList());
    }

    private void startKanto(boolean newgame, String saveName) {
        try {
            ArrayList<Area> gameMap = Region.createRegion(KANTO_MAP, JAR_MODE);
            Game game = GameInflater.inflateRegion(gameMap, saveName, newgame, JAR_MODE, gameFrame);
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
