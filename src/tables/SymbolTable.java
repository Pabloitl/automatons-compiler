package tables;

import java.util.LinkedList;
import java.util.List;

public class SymbolTable {
    private static SymbolTable instance;

    private List<String> symbols;

    private SymbolTable() {
        symbols = new LinkedList<>();
    }

    public static SymbolTable getInstance() {
        if (instance == null)
            instance = new SymbolTable();

        return instance;
    }

    public void addIdentifier(String lexeme) {
        if (symbols.contains(lexeme))
            return;

        symbols.add(lexeme);
    }

    @Override
    public String toString() {
        StringBuilder repr = new StringBuilder("«Tabla de símbolos»\n");

        symbols.stream()
            .forEach(symbol -> repr.append(" ⋄ " + symbol + "\n"));

        return repr.toString();
    }
}
