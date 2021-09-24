package utils;

import tables.KeywordTable;

public class Token {
    private int attr;
    private String lexeme;
    private String kind;

    public Token(int attr) {
        this.attr = attr;
    }

    public Token(String lexeme, int attr) {
        this(attr);
        this.lexeme = lexeme;
    }

    public Token(String lexeme, int attr, String kind) {
        this(lexeme, attr);
        this.kind = kind;
    }

    public String getClassification() {
        switch (attr - attr % 100) {
            case 0: case 100: case 200:
                return "Caracteres simples";
            case 300:
                return "Identificadores";
            case 400:
                return "Números enteros";
            case 500:
                return "Números de punto flotante";
            case 600:
                return "Palabras reservadas";
            case 900:
                return "Error Léxico";
        }
        return "None";
    }

    public String getKind() {
        if (kind != null) return kind;

        switch (lexeme) {

        }

        switch (attr) {
            case ';':
                return "Punto y coma";
            case '=':
                return "Igual";
            case '+':
                return "Más";
            case '/':
                return "Diagonal";
            case '(':
                return "Parentesis izquierdo";
            case ')':
                return "Parentesis derecho";
            case ',':
                return "Coma";
            case '-':
                return "Hyphen";
            case 601:
            case 602:
                return "Tipo de dato";
            case 600:
            case 603:
            case 604:
                return "Función";
            case 605:
                return "Inicial";
            case 606:
                return "Final";
        }

        switch (attr - attr % 100) {
            case 400:
                return "Número entero";
            case 500:
                return "Número real";
        }

        return "Sin tipo";
    }

    public int getAttribute() {
        return this.attr;
    }

    public String getLexeme() {
        return this.lexeme;
    }

    public String toTerminal() {
        switch (attr - attr % 100) {
            case 0: case 100: case 200:
                return ((char) attr) + "";
            case 300:
                return "id";
            case 400:
                return "intliteral";
            case 500:
                return "realliteral";
            case 600:
                return KeywordTable.getInstance().getLexeme(attr);
            case 900:
                return "error";
            case 1000:
                return "EOF";
        }
        return "None";
    }

    @Override
    public String toString() {
        StringBuilder repr = new StringBuilder("Token: ");

        repr.append("Atributo → (" + attr + ")\t");
        repr.append("Lexema → (" + lexeme + ") ");
        repr.append("Clasificación → (" + getClassification() + ")");

        return repr.toString();
    }
}
