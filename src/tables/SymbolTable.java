package tables;

import utils.List;

public class SymbolTable {
    private static SymbolTable instance;

    private List<String> symbols;

    private SymbolTable() {
        symbols = new List<>();
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

        symbols
            .forEach(symbol -> repr.append(" ⋄ " + symbol + "\n"));

        return repr.toString();
    }
}
