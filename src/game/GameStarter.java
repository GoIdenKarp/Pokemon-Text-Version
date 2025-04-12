package game;

import areas.Area;
import exceptions.BadNameException;
import ui.GameFrame;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;

public class GameStarter {

    private final static boolean DEBUG_MODE = true;
    private final static boolean JAR_MODE = false;
    private final static String KANTO_MAP = "Kanto.region";
    private final static String KANTO_NEW_GAME = "data/kanto.newgame";

    private final static String KANTO = "Kanto";
    private final static String BULBASAUR = "Bulbasaur";
    private final static String CHARMANDER = "Charmander";
    private final static String SQUIRTLE = "Squirtle";
    private final static ArrayList<String> gameOptions = new ArrayList<>(Arrays.asList(KANTO));
    private final static ArrayList<String> kantoStarterOptions = new ArrayList<>(Arrays.asList(BULBASAUR, CHARMANDER, SQUIRTLE));

    private GameFrame gameFrame;


    public GameStarter() {
        gameFrame = new GameFrame(DEBUG_MODE);

    }

    public void startGame() {
        String gameChoice = gameFrame.getInputHelper().getInputFromOptions(gameOptions, "Game Selection",
                "Please select which region you would like to play:");
        if (gameChoice.equals(KANTO)) {
            startKanto(true);
        }
    }

    private void startKanto(boolean newgame) {
        try {
            ArrayList<Area> gameMap = Region.createRegion(KANTO_MAP, JAR_MODE);
            Game game = GameInflater.inflateRegion(gameMap, KANTO_NEW_GAME, newgame, JAR_MODE, gameFrame);
            if (newgame) {
                game.startNew();
            }
            //TODO: what if it's not a new game?
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
            gameFrame.getGamePrinter().printFileNotFound();
            System.exit(0);
        } catch (BadNameException e) {
            gameFrame.getGamePrinter().printUnknownException();
            e.printStackTrace();
            System.err.println("Bad name: " + e.getBadName());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new GameStarter().startGame();
    }

}
