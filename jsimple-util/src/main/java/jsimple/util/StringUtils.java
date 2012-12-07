package jsimple.util;

/**
 * @author Bret Johnson
 * @since 10/23/12 12:23 AM
 */
public class StringUtils {

    /**
     * Convert a byte array to a hexadecimal string.  Leading zeros are of course preserved.  Hex letters are upper
     * case.
     *
     * @param bytes byte array
     * @return hexadecimal string
     */
    public static String toHexStringFromBytes(byte[] bytes) {
        char[] hexArray = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        char[] hexChars = new char[bytes.length * 2];
        int v;
        for (int j = 0; j < bytes.length; j++) {
            v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];  // Formerly v/16
            hexChars[j * 2 + 1] = hexArray[v & 0xF];   // Formerly v%16
        }
        return new String(hexChars);
    }

    /**
     * Convert a hexadecimal string to a byte array.  An exception is thrown if the string contains any non-hex
     * characters.  A-F and a-f (both upper & lower case) are allowed for input.
     *
     * @param hexString hex string, containing only 0-9, A-F, or a-f
     * @return byte array
     */
    public static byte[] toBytesFromHexString(String hexString) {
        int len = hexString.length();
        byte[] bytes = new byte[len / 2];
        for (int i = 0; i < len; i += 2)
            bytes[i / 2] = (byte) ((toByteFromHexCharacter(hexString.charAt(i)) << 4)
                    + toByteFromHexCharacter(hexString.charAt(i + 1)));

        return bytes;
    }

    /**
     * Convert a hex character to it's equivalent byte.  Similar to standard Java's Character.digit.  If character isn't
     * a valid hex character, an exception is thrown.
     *
     * @param hexCharacter hex character (0-9, A-F, or a-f)
     * @return number equivalent (0-15)
     */
    public static byte toByteFromHexCharacter(char hexCharacter) {
        if ('0' <= hexCharacter && hexCharacter <= '9')
            return (byte) (hexCharacter - '0');
        else if ('A' <= hexCharacter && hexCharacter <= 'F')
            return (byte) (hexCharacter - 'A' + 10);
        else if ('a' <= hexCharacter && hexCharacter <= 'f')
            return (byte) (hexCharacter - 'a' + 10);
        else throw new RuntimeException("Invalid hex character: '" + hexCharacter + "'");
    }

    /**
     * Convert a byte array, assumed to be a Latin1 (aka 8859-1 aka Windows 1252) encoded string, to a regular Java
     * string.  Latin1 is the first 256 characters of Unicode, so this conversion is just a straight pass through,
     * turning bytes in chars.  Note that ASCII is a subset of Latin1.
     *
     * @param bytes byte array
     * @return resulting string
     */
    public static String toStringFromLatin1Bytes(byte[] bytes) {
        int length = bytes.length;
        char[] chars = new char[length];
        for (int i = 0; i < length; i++)
            chars[i] = (char) bytes[i];
        return new String(chars);
    }

    /**
     * Convert a string, assumed to contain only Latin1 (aka 8859-1 aka Windows 1252) characters, to a byte array.  The
     * byte array is encoded one byte per character--just a straight pass through from the Unicode, dropping the high
     * byte (which should be zero) of each character.  If any non-Latin1 characters are in the string an exception is
     * thrown.  This method is an efficient way to only ASCII text as a byte array, since ASCII is a subset of Latin1.
     *
     * @param string input string, which should contain only Latin1 characters
     * @return resulting byte array
     */
    public static byte[] toLatin1BytesFromString(String string) {
        int length = string.length();
        byte[] bytes = new byte[length];
        for (int i = 0; i < length; i++) {
            char c = string.charAt(i);
            if (c >= 256)
                throw new RuntimeException("Character '" + c + "' at index + " + i + " isn't a Latin1 character");
            bytes[i] = (byte) c;
        }
        return bytes;
    }
}