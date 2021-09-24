package utils;

import java.util.ArrayList;
import java.util.Arrays;

public class Symbol {
    String lexeme;
    String classification;
    String kind;
    int intvalue;
    float floatvalue;
    ArrayList<Integer> lines;

    public Symbol() {
        lines = new ArrayList<>();
    }

    public Symbol(Token token) {
        this();
        this.lexeme = token.getLexeme();
        this.classification = token.getClassification();
        this.kind = token.getKind();

        if (token.toTerminal().equals("id"))
            this.intvalue = token.getAttribute();
        else if (token.toTerminal().equals("realliteral"))
            this.floatvalue = Float.parseFloat(this.lexeme);
        else if (token.toTerminal().equals("intliteral"))
            this.intvalue = Integer.parseInt(this.lexeme);
        else
            throw new RuntimeException("Can't resolve value");
    }

    public void seenInLine(int lineNumber) {
        lines.add(lineNumber);
    }

    public String getLexeme() {
        return lexeme;
    }

    @Override
    public String toString() {
        StringBuffer repr = new StringBuffer();
        repr.append(String.format("%10s", this.lexeme) + "\t");
        repr.append(String.format("%26s", this.classification) + "\t");
        repr.append(String.format("%14s", this.kind) + "\t");

        if (this.kind.equals("Número real"))
            repr.append(String.format("%10s", this.floatvalue) + "\t");
        else
            repr.append(String.format("%10s", this.intvalue) + "\t");

        repr.append(String.format("%12s", this.lines.size()) + "\t");
        repr.append(String.format("%10s\t", Arrays.toString(this.lines.toArray(Integer[]::new))));

        return repr.toString();
    }
}
