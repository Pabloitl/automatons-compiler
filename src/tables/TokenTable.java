package tables;

import utils.List;

import utils.Token;

public class TokenTable {
    private static TokenTable instance;

    private List<Token> tokens;

    private TokenTable() {
        tokens = new List<>();
    }

    public static TokenTable getInstance() {
        if (instance == null)
            instance = new TokenTable();

        return instance;
    }

    public void addToken(Token token) {
        tokens.add(token);
    }

    @Override
    public String toString() {
        StringBuilder repr = new StringBuilder("«Tabla de Tokens»\n");

        repr.append(String.format("%12s\t%30s\t%22s\t%3s\n", "Lexema", "Tipo-lexema", "Tipo", "Valor"));

        tokens.forEach(token -> repr.append(
                    String.format("%12s", token.getLexeme())+ "\t"
                    + String.format("%30s", token.getClassification()) + "\t"
                    + String.format("%22s", token.getKind()) + "\t"
                    + String.format("%3s", token.getAttribute()) + "\n"));;

        return repr.toString();
    }
}
