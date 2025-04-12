package items;

public abstract class Item {


    public abstract String getName();

    public abstract String getDescription();

    public abstract int getCost();

    public abstract String getEncodedName();


    public String toString() {
        return getName();
    }


}
