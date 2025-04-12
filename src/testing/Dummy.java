package testing;

import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.HashMap;

public class Dummy {

    public static void main(String[] args) {

        StringBuilder builder = new StringBuilder("Hello,\n");
        builder.append(" world!");
        String original = "Hello, world!";

        original += "\nNow is the time";
        original += "\nFor all good men";
        original += "\nTo come to the aid of the party";
        String encoded = Base64.getEncoder().encodeToString(original.getBytes());
        String decoded = new String(Base64.getDecoder().decode(encoded));

        System.out.println(original);
        System.out.println(encoded);
        System.out.println(decoded);


    }




}
