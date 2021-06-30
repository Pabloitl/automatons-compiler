package tables;

import java.util.Arrays;

public class KeywordTable {
    private static KeywordTable instance;

    private final int OFFSET = 600;
    private String keywords[];

    private KeywordTable() {
        keywords = new String[]{
            "Programa",
            "Real",
            "Entero",
            "Leer",
            "Escribir",
            "Inicio",
            "Fin"
        };
    }

    public static KeywordTable getInstance() {
        if (instance == null)
            instance = new KeywordTable();

        return instance;
    }

    public int getAttr(String lexeme) {
        int index = -1;

        for (int i = 0; i < keywords.length; ++i)
            if (keywords[i].equals(lexeme)) index = i;

        return index + OFFSET;
    }

    public String getLexeme(int attr) {
        if (attr < OFFSET || attr >= keywords.length + OFFSET)
            return "";

        return keywords[attr - OFFSET];
    }

    @Override
    public String toString() {
        StringBuilder repr = new StringBuilder("«Tabla de palabras reservadas»\n");

        Arrays.stream(keywords)
            .forEach(keyword -> repr.append(" ⋄ " + keyword + " → " + getAttr(keyword) + "\n"));

        return repr.toString();
    }
}
