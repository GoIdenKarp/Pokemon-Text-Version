package exceptions;

public class BadNameException extends Exception {

    private String badName;

    public BadNameException(String badName) {
        this.badName = badName;
    }

    public String getBadName() {
        return badName;
    }

}
