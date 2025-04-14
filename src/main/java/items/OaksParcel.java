package items;

public class OaksParcel extends KeyItem {


    private final static String NAME = "Oak's Parcel";
    private final static String ENCODED_NAME = "OAKPARCEL";
    private final static String DESCRIPTION = "A parcel to be delivered to Professor Oak from Viridian City's Pokémon Mart.";
    private final static int COST = 0;


    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public String getDescription() {
        return DESCRIPTION;
    }

    @Override
    public int getCost() {
        return COST;
    }

    @Override
    public String getEncodedName() {
        return ENCODED_NAME;
    }
}
