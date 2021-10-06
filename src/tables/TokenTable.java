package tables;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

import utils.Token;

public class TokenTable {
    private static TokenTable instance;

    private ArrayList<Token> tokens;

    private TokenTable() {
        tokens = new ArrayList<>();
    }

    public static TokenTable getInstance() {
        if (instance == null)
            instance = new TokenTable();

        return instance;
    }

    public void addToken(Token token) {
        tokens.add(token);
    }

    public Stream<Token> stream() {
        return tokens.stream();
    }

    public List<Token> getAsList() {
        return List.copyOf(tokens);
    }

    @Override
    public String toString() {
        StringBuilder repr = new StringBuilder("«Tabla de Tokens»\n");

        repr.append(String.format("%12s\t%30s\t%22s\t%3s\n", "Lexema", "Tipo-lexema", "Tipo", "Valor"));

        tokens.forEach(token -> repr.append(
                    String.format("%12s", token.getLexeme())+ "\t"
                    + String.format("%30s", token.getClassification()) + "\t"
                    + String.format("%22s", token.getKind()) + "\t"
                    + String.format("%3s", token.getAttribute()) + "\t"
                    + String.format("%3s", token.getLine()) + "\n"));

        return repr.toString();
    }
}
