package util;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;

public class JsonBase64Util {
    private static final Base64.Encoder ENCODER = Base64.getEncoder();
    private static final Base64.Decoder DECODER = Base64.getDecoder();

    /**
     * Reads a JSON file and converts it to a Base64 encoded string
     * @param jsonFilePath Path to the JSON file to encode
     * @param outputFilePath Path where to save the Base64 encoded content
     * @throws IOException If there are issues reading or writing files
     * @throws ParseException If the JSON file is invalid
     */
    public static void encodeJsonToBase64File(String jsonFilePath, String outputFilePath) throws IOException, ParseException {
        // Read the JSON file
        String jsonContent = new String(Files.readAllBytes(Paths.get(jsonFilePath)), StandardCharsets.UTF_8);
        
        // Parse and re-stringify to remove unnecessary whitespace
        JSONParser parser = new JSONParser();
        JSONObject jsonObject = (JSONObject) parser.parse(jsonContent);
        String minifiedJson = jsonObject.toJSONString();
        
        // Encode to Base64
        String base64Encoded = ENCODER.encodeToString(minifiedJson.getBytes(StandardCharsets.UTF_8));
        
        // Write to output file
        Files.write(Paths.get(outputFilePath), base64Encoded.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * Decodes a Base64 encoded file back to a JSON object
     * @param base64FilePath Path to the Base64 encoded file
     * @return Decoded JSONObject
     * @throws IOException If there are issues reading the file
     * @throws ParseException If the decoded content is not valid JSON
     */
    public static JSONObject decodeBase64FileToJson(String base64FilePath) throws IOException, ParseException {
        // Read the Base64 file
        String base64Content = new String(Files.readAllBytes(Paths.get(base64FilePath)), StandardCharsets.UTF_8).trim();
        
        // Decode from Base64
        byte[] decodedBytes = DECODER.decode(base64Content);
        String jsonContent = new String(decodedBytes, StandardCharsets.UTF_8);
        
        // Parse JSON
        JSONParser parser = new JSONParser();
        return (JSONObject) parser.parse(jsonContent);
    }

    /**
     * Decodes a Base64 encoded InputStream back to a JSON object
     * @param inputStream InputStream containing Base64 encoded content
     * @return Decoded JSONObject
     * @throws IOException If there are issues reading the stream
     * @throws ParseException If the decoded content is not valid JSON
     */
    public static JSONObject decodeBase64StreamToJson(InputStream inputStream) throws IOException, ParseException {
        // Read the Base64 content from the stream
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        int nRead;
        byte[] data = new byte[1024];
        while ((nRead = inputStream.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, nRead);
        }
        buffer.flush();
        String base64Content = new String(buffer.toByteArray(), StandardCharsets.UTF_8).trim();
        
        // Decode from Base64
        byte[] decodedBytes = DECODER.decode(base64Content);
        String jsonContent = new String(decodedBytes, StandardCharsets.UTF_8);
        
        // Parse JSON
        JSONParser parser = new JSONParser();
        return (JSONObject) parser.parse(jsonContent);
    }

    /**
     * Utility method to convert a JSON file to a pretty-printed string
     * @param jsonFilePath Path to the JSON file
     * @return Pretty-printed JSON string
     * @throws IOException If there are issues reading the file
     * @throws ParseException If the file contains invalid JSON
     */
    public static String prettyPrintJson(String jsonFilePath) throws IOException, ParseException {
        String jsonContent = new String(Files.readAllBytes(Paths.get(jsonFilePath)), StandardCharsets.UTF_8);
        JSONParser parser = new JSONParser();
        JSONObject jsonObject = (JSONObject) parser.parse(jsonContent);
        return jsonObject.toJSONString();
    }

    /**
     * Test method to verify encoding/decoding process
     * @param args Command line arguments (not used)
     */
    public static void main(String[] args) {
        try {
            String inputDir = "src/main/resources/json-data";
            String outputDir = "src/main/resources/data";
            
            // Create output directory if it doesn't exist
            Files.createDirectories(Paths.get(outputDir));
            
            // Process Kanto.json -> Kanto.region
            String kantoJsonPath = Paths.get(inputDir, "Kanto.json").toString();
            String kantoRegionPath = Paths.get(outputDir, "Kanto.region").toString();
            
            System.out.println("\nProcessing Kanto.json -> Kanto.region");
            System.out.println("Encoding JSON to Base64...");
            encodeJsonToBase64File(kantoJsonPath, kantoRegionPath);
            System.out.println("Encoded successfully!");
            
            // Verify the encoding
            System.out.println("Verifying encoding...");
            JSONObject decodedKanto = decodeBase64FileToJson(kantoRegionPath);
            System.out.println("Verification successful!");
            
            // Process Kanto_NewGame.json -> Kanto.newgame
            String newGameJsonPath = Paths.get(inputDir, "Kanto_NewGame.json").toString();
            String newGamePath = Paths.get(outputDir, "Kanto.newgame").toString();
            
            System.out.println("\nProcessing Kanto_NewGame.json -> Kanto.newgame");
            System.out.println("Encoding JSON to Base64...");
            encodeJsonToBase64File(newGameJsonPath, newGamePath);
            System.out.println("Encoded successfully!");
            
            // Verify the encoding
            System.out.println("Verifying encoding...");
            JSONObject decodedNewGame = decodeBase64FileToJson(newGamePath);
            System.out.println("Verification successful!");
            
            System.out.println("\nAll files processed successfully!");
        } catch (Exception e) {
            System.err.println("Error during encoding/decoding process:");
            e.printStackTrace();
        }
    }
} 