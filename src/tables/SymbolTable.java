package tables;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import utils.Symbol;
import utils.Token;

public class SymbolTable {
    private static SymbolTable instance;

    private ArrayList<Symbol> symbols;

    private SymbolTable() {
        symbols = new ArrayList<>();
    }

    public static SymbolTable getInstance() {
        if (instance == null)
            instance = new SymbolTable();

        return instance;
    }

    public void addIdentifier(Token token) {
        if (this.contains(token))
            return;

        symbols.add(new Symbol(token));
    }

    public boolean contains(Token token) {
        return symbols.stream()
            .map(Symbol::getLexeme)
            .anyMatch(l -> l.equals(token.getLexeme()));
    }

    public void addLinetoToken(int lineNumber, String identifier) {
        symbols.stream()
            .filter(symbol -> symbol.getLexeme().equals(identifier))
            .findFirst()
            .orElseThrow()
            .seenInLine(lineNumber);
    }

    public Symbol search(String lexeme) {
        return symbols.stream()
            .filter(s -> s.getLexeme().equals(lexeme))
            .findFirst().orElseThrow();
    }

    public Stream<Symbol> stream() {
        return symbols.stream();
    }

    @Override
    public String toString() {
        StringBuilder repr = new StringBuilder(String.format("%10s\t%26s\t%14s\t%10s\t%12s\t%10s\n",
                    "Lexeme", "Token", "Tipo", "Valor", "Repeticiones", "Línea"));

        symbols
            .forEach(symbol -> repr.append(symbol + "\n"));

        return repr.toString();
    }
}
