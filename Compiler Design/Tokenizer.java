import java.util.ArrayList;
import java.util.List;

public class Tokenizer {
    public static List<Token> tokenize(String line) {
        List<Token> tokens = new ArrayList<>();
        int i = 0;

        while (i < line.length()) {
            char c = line.charAt(i);

            // Skip spaces
            if (Character.isWhitespace(c)) {
                i++;
                continue;
            }

            // Handle numbers
            if (Character.isDigit(c)) {
                StringBuilder num = new StringBuilder();
                while (i < line.length() && Character.isDigit(line.charAt(i))) {
                    num.append(line.charAt(i));
                    i++;
                }
                tokens.add(new Token(Token.Type.NUMBER, num.toString()));
            }

            // Handle words (keywords or identifiers)
            else if (Character.isLetter(c)) {
                StringBuilder word = new StringBuilder();
                while (i < line.length() && Character.isLetterOrDigit(line.charAt(i))) {
                    word.append(line.charAt(i));
                    i++;
                }
                String value = word.toString();
                if (isKeyword(value)) {
                    tokens.add(new Token(Token.Type.KEYWORD, value));
                } else {
                    tokens.add(new Token(Token.Type.IDENTIFIER, value));
                }
            }

            // Handle symbols like >, <, =
            else if ("<>=!".indexOf(c) >= 0) {
                String symbol = "" + c;
                i++;
                if (i < line.length() && line.charAt(i) == '=') {
                    symbol += "=";
                    i++;
                }
                tokens.add(new Token(Token.Type.SYMBOL, symbol));
            }

            else {
                throw new RuntimeException("Oops Surabhi, unknown character found: " + c);
            }
        }

        return tokens;
    }

    private static boolean isKeyword(String word) {
        return word.equals("SET") || word.equals("ADD") || word.equals("SUB") || 
            word.equals("MUL") || word.equals("DIV") || word.equals("PRINT") ||
            word.equals("IF") || word.equals("THEN") || word.equals("WHILE") ||
            word.equals("DO") || word.equals("END") || word.equals("INPUT");
    }
}
