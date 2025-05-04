package util;

/**
 * Utility class for string manipulation operations.
 */
public class StringUtils {
    
    /**
     * Formats an enum value to be properly capitalized and spaced.
     * Example: "FIRE" becomes "Fire
     * 
     * @param value The enum value to format
     * @return The formatted string, or the original value if null or empty
     */
    public static String formatEnumValue(String value) {
        if (value == null || value.isEmpty()) {
            return value;
        }
        return value.substring(0, 1).toUpperCase() + 
               value.substring(1).toLowerCase().replace("_", " ");
    }
} 