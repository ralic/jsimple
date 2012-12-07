package jsimple.util;

/**
 * @author Bret Johnson
 * @since 11/25/12 4:00 PM
 */
public class CharIterator {
    private String str;
    private int index = 0;
    private int length;

    public CharIterator(String string) {
        this.str = string;
        this.length = string.length();
    }

    public boolean atEnd() {
        return index >= length;
    }

    /**
     * @return current character or -1 if at end of str
     */
    public int curr() {
        if (index >= length)
            return -1;
        else return str.charAt(index);
    }

    /**
     * Return the current character (same as curr()) then advance to the next one.
     *
     * @return current character or -1 if at end of str
     */
    public int currAndAdvance() {
        int c = curr();
        advance();
        return c;
    }

    /**
     * Advance the iterator to the next character.  If already at the end of the str, this method is a no-op.
     */
    public void advance() {
        if (index < length)
            ++index;
    }

    /**
     * Search for the the next occurrence of the specified substring & advance the iterator just past it.  If that
     * substring isn't found, an exception is thrown.
     *
     * @param substr substring to search for
     */
    public void skipAheadPast(String substr) {
        int substringIndex = str.indexOf(substr, index);
        if (substringIndex == -1)
            throw new RuntimeException("'" + substr + "' not found in string '" + str + "'");

        index = substringIndex + substr.length();
    }

    /**
     * Verify that the current character is c, throwing an exception if it isn't, then advance to the next character.
     *
     * @param c character to verify that occurs at the current iterator position
     */
    public void checkAndAdvancePast(char c) {
        if (atEnd())
            throw new RuntimeException("Already at end of string");

        if (str.charAt(index) != c)
            throw new RuntimeException("Character '" + c + "' not found at this position in the string: " +
                    str.substring(index));

        ++index;
    }

    /**
     * @return true if the current character is a whitespace character.
     */
    public boolean isWhitespace() {
        int c = curr();
        return c == ' ' || c == '\t' || c == '\r' || c == '\n';
    }

    /**
     * Advance past any whitespace characters in the str, leaving the iterator at the first non-whitespace character
     * following.
     */
    public void advancePastWhitespace() {
        while (isWhitespace())
            advance();
    }
}