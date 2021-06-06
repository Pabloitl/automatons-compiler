package analyzers;

public class Token {
    private int attr;

    public Token(int attr) {
        this.attr = attr;
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
        }
        return "None";
    }

    @Override
    public String toString() {
        StringBuilder repr = new StringBuilder("Token: ");

        repr.append("Atributo → (" + attr + ")\t");
        repr.append("Clasificación → (" + getClassification() + ") ");

        return repr.toString();
    }
}
