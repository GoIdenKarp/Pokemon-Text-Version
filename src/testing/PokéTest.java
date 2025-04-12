package testing;

import battle.BattleRedux;
import enums.Owner;
import enums.Species;
import enums.Weather;
import game.GameStarter;
import game.Player;
import moves.*;
import pokémon.*;
import trainer.PartySlot;
import trainer.Trainer;
import ui.GameFrame;

import java.util.ArrayList;
import java.util.Arrays;

public class PokéTest {

	public static void main(String[] args) {
        //battleTest();
       new GameStarter().startGame();

    }

    public static void battleTest() {
        GameFrame frame = new GameFrame(false);
        Player ari = new Player("Ari", "Evil Ari");
        PokémonFactory factory = new PokémonFactory(frame.getInputHelper(), frame.getGamePrinter());
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
        new BattleRedux(ari, testTrainer, Weather.NONE, frame).battle();
    }

}

