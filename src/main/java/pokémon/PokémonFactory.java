package pokémon;

import enums.Owner;
import enums.Species;
import ui.GameFrame;

/**
 * Created by AriG on 6/10/17.
 */
public class PokémonFactory {

    private GameFrame.InputHelper inputHelper;
    private GameFrame.GamePrinter gamePrinter;


    public PokémonFactory(GameFrame.InputHelper inputHelper, GameFrame.GamePrinter gamePrinter) {
        this.inputHelper = inputHelper;
        this.gamePrinter = gamePrinter;
    }

    public Pokémon makePokémon(Species species, int level, Owner owner) {
        switch (species) {
            case BULBASAUR:
                return new Bulbasaur(level, owner, inputHelper, gamePrinter);
            case IVYSAUR:
                return new Ivysaur(level, owner, inputHelper, gamePrinter);
            case VENUSAUR:
                return new Venusaur(level, owner, inputHelper, gamePrinter);
            case CHARMANDER:
                return new Charmander(level, owner, inputHelper, gamePrinter);
            case CHARMELEON:
                return new Charmeleon(level, owner, inputHelper, gamePrinter);
            case CHARIZARD:
                return new Charizard(level, owner, inputHelper, gamePrinter);
            case SQUIRTLE:
                return new Squirtle(level, owner, inputHelper, gamePrinter);
            case WARTORTLE:
                return new Wartortle(level, owner, inputHelper, gamePrinter);
            case BLASTOISE:
                return new Blastoise(level, owner, inputHelper, gamePrinter);
            case CATERPIE:
                return new Caterpie(level, owner, inputHelper, gamePrinter);
            case METAPOD:
                return new Metapod(level, owner, inputHelper, gamePrinter);
            case BUTTERFREE:
                return new Butterfree(level, owner, inputHelper, gamePrinter);
            case WEEDLE:
                return new Weedle(level, owner, inputHelper, gamePrinter);
            case KAKUNA:
                return new Kakuna(level, owner, inputHelper, gamePrinter);
            case BEEDRILL:
                return new Beedrill(level, owner, inputHelper, gamePrinter);
            case PIDGEY:
                return new Pidgey(level, owner, inputHelper, gamePrinter);
            case PIDGEOTTO:
                return new Pidgeotto(level, owner, inputHelper, gamePrinter);
            case PIDGEOT:
                return new Pidgeot(level, owner, inputHelper, gamePrinter);
            case RATTATA:
                return new Rattata(level, owner, inputHelper, gamePrinter);
            case RATICATE:
                return new Raticate(level, owner, inputHelper, gamePrinter);
            case SPEAROW:
                return new Spearow(level, owner, inputHelper, gamePrinter);
            case FEAROW:
                return new Fearow(level, owner, inputHelper, gamePrinter);
            case EKANS:
                return new Ekans(level, owner, inputHelper, gamePrinter);
            case ARBOK:
                return new Arbok(level, owner, inputHelper, gamePrinter);
            case PIKACHU:
                return new Pikachu(level, owner, inputHelper, gamePrinter);
            case RAICHU:
                return new Raichu(level, owner, inputHelper, gamePrinter);
            case SANDSHREW:
                return new Sandshrew(level, owner, inputHelper, gamePrinter);
            case SANDSLASH:
                return new Sandslash(level, owner, inputHelper, gamePrinter);
            case NIDORANF:
                return new NidoranF(level, owner, inputHelper, gamePrinter);
            case NIDORINA:
                return new Nidorina(level, owner, inputHelper, gamePrinter);
            case NIDOQUEEN:
                return new Nidoqueen(level, owner, inputHelper, gamePrinter);
            case NIDORANM:
                return new NidoranM(level, owner, inputHelper, gamePrinter);
            case NIDORINO:
                return new Nidorino(level, owner, inputHelper, gamePrinter);
            case NIDOKING:
                return new Nidoking(level, owner, inputHelper, gamePrinter);
            case VULPIX:
                return new Vulpix(level, owner, inputHelper, gamePrinter);
            case NINETALES:
                return new Ninetales(level, owner, inputHelper, gamePrinter);
            case MANKEY:
                return new Mankey(level, owner, inputHelper, gamePrinter);
            case PRIMEAPE:
                return new Primeape(level, owner, inputHelper, gamePrinter);
            default:
                return new Pikachu(level, owner, inputHelper, gamePrinter);
        }
    }
}
