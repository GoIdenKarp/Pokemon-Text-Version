package events;

public class MovementFlag {

    private String areaOne;
    private String areaTwo;

    public MovementFlag(String areaOne, String areaTwo) {
        this.areaOne = areaOne;
        this.areaTwo = areaTwo;
    }

    public String getAreaOne() {
        return areaOne;
    }

    public String getAreaTwo() {
        return areaTwo;
    }
}
