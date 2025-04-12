package testing;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.Base64;
import java.util.Scanner;

public class Testing {

    private static final Base64.Encoder ENCODER = Base64.getEncoder();
    private static final Base64.Decoder DECODER = Base64.getDecoder();
    private static final JSONParser PARSER = new JSONParser();

    public static void testRegionParsing() throws IOException, ParseException {

        String regionData = new Scanner(new File("src/data/Kanto.json")).useDelimiter("\\Z").next();
        System.out.println(regionData);
        String encoded = ENCODER.encodeToString(regionData.getBytes());
        FileWriter writer = new FileWriter("Kanto.region");
        writer.write(encoded);
        writer.close();
        Scanner scanner = new Scanner(new File("Kanto.region"));
        String codedRegion = scanner.next();
        String decoded = new String(DECODER.decode(codedRegion));
        JSONParser parser = new JSONParser();
        JSONObject testObj = (JSONObject) parser.parse(decoded);
        System.out.println(encoded);
        System.out.println(testObj);
    }

    public static void testSaveParsing() throws IOException, ParseException {

        String saveData = new Scanner(new File("src/data/Kanto_NewGame.json")).useDelimiter("\\Z").next();
        System.out.println(saveData);
        JSONObject saveObj = (JSONObject) PARSER.parse(saveData);
        JSONObject test = (JSONObject) saveObj.get("areas");
        System.out.println(saveObj);
        System.out.println(test);
//        String encoded = ENCODER.encodeToString(regionData.getBytes());
//        FileWriter writer = new FileWriter("Kanto.region");
//        writer.write(encoded);
//        writer.close();
//        Scanner scanner = new Scanner(new File("Kanto.region"));
//        String codedRegion = scanner.next();
//        String decoded = new String(DECODER.decode(codedRegion));
//        JSONParser parser = new JSONParser();
//        JSONObject testObj = (JSONObject) parser.parse(decoded);
//
//
//        System.out.println(encoded);
//        System.out.println(testObj);
    }


    public static void main(String[] args) throws CloneNotSupportedException, IOException, ParseException {
        //testRegionParsing();
        testSaveParsing();
    }
}
