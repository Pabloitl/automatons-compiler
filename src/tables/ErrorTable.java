package tables;

import utils.List;

public class ErrorTable {
    private static ErrorTable instance;

    private List<String> errors;

    private ErrorTable() {
        errors = new List<>();
    }

    public static ErrorTable getInstance() {
        if (instance == null)
            instance = new ErrorTable();

        return instance;
    }

    public void addError(int line, String lexeme) {
        errors.add("Línea " + line + " → " + lexeme);
    }

    @Override
    public String toString() {
        StringBuilder repr = new StringBuilder("«Tabla de errores»\n");

        errors
            .forEach(error -> repr.append(" ⋄ " + error + "\n"));

        return repr.toString();
    }
}
