package items;

import exceptions.BadNameException;

public class ItemMapper {

    /**
     * Parses a string as the name of an Item to be used in-game. This method is for reading in maps from files.
     * @param toMap The String representing the Item
     * @return The Item named in toMap
     * @throws exceptions.BadNameException If I put a bad String that isn't the name of an item anywhere
     */
    public static Item map(String toMap) throws BadNameException {
        System.out.println("Mapping " + toMap);
        switch(toMap) {
            case "POKEBALL":
                return new PokéBall();
            case "POTION":
                return new Potion();
            case "SUPOTION":
                return new SuperPotion();
            case "ANTIDOTE":
                return new Antidote();
            case "PARALYZHL":
                return new ParalyzeHeal();
            case "OAKPARCEL":
                return new OaksParcel();
            case "None":
                return null;
            default:
                throw new BadNameException(toMap);

        }
    }
}
