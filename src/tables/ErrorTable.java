package tables;

import java.util.LinkedList;
import java.util.List;

public class ErrorTable {
    private static ErrorTable instance;

    private List<String> errors;

    private ErrorTable() {
        errors = new LinkedList<>();
    }

    public static ErrorTable getInstance() {
        if (instance == null)
            instance = new ErrorTable();

        return instance;
    }

    public void addError(String lexeme) {
        errors.add(lexeme);
    }

    @Override
    public String toString() {
        StringBuilder repr = new StringBuilder("«Tabla de errores»\n");

        errors.stream()
            .forEach(error -> repr.append(" ⋄ " + error + "\n"));

        return repr.toString();
    }
}
