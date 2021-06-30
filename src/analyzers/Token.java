package analyzers;

import tables.KeywordTable;

public class Token {
    private int attr;
    private String lexeme;

    public Token(int attr) {
        this.attr = attr;
    }

    public Token(String lexeme, int attr) {
        this.attr = attr;
        this.lexeme = lexeme;
    }

    public String getClassification() {
        switch (attr - attr % 100) {
            case 0: case 100: case 200:
                return "Caracteres simples";
            case 300:
                return "Identificadores";
            case 400:
                return "Números enteros (naturales)";
            case 500:
                return "Números de punto flotante (sin signo)";
            case 600:
                return "Palabras reservadas";
            case 900:
                return "Error Léxico";
        }
        return "None";
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
