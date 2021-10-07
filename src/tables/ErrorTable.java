package tables;

import java.util.ArrayList;

public class ErrorTable {
    private static ErrorTable instance;

    private ArrayList<String> errors;

    private ErrorTable() {
        errors = new ArrayList<>();
    }

    public static ErrorTable getInstance() {
        if (instance == null)
            instance = new ErrorTable();

        return instance;
    }

    public void addError(int line, String msg) {
        errors.add("Línea(" + line + ") → " + msg);
    }

    public boolean isEmpty() {
        return errors.isEmpty();
    }

    @Override
    public String toString() {
        StringBuilder repr = new StringBuilder("«Tabla de errores»\n");

        errors
            .forEach(error -> repr.append(" ⋄ " + error + "\n"));

        return repr.toString();
    }
}
